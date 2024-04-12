package com.example.lesson_3_safarov.presentation.ui.order

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.data.responsemodel.formatPrice
import com.example.lesson_3_safarov.databinding.FragmentOrderBinding
import com.example.lesson_3_safarov.presentation.MapActivity
import com.example.lesson_3_safarov.presentation.MapActivity.Companion.RESULT_ADDRESS_KEY
import com.example.lesson_3_safarov.presentation.utils.SnackbarEvents
import com.example.lesson_3_safarov.presentation.utils.showStylizedSnackbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class OrderFragment : Fragment() {

    companion object {
        private val COUNTER_RANGE = IntRange(1, 10)
        private const val DATE_PATTERN = "d MMMM"
        private const val RESPONSE_DATE_PATTERN = "dd.MM.yyyy HH:mm"
    }

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private val args: OrderFragmentArgs by navArgs()
    private var startForResultLauncher: ActivityResultLauncher<Intent>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        OrderViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.status_bar)
        viewModel.onScreenLoad(args.product)

        startForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                binding.houseText.setText(result.data?.getStringExtra(RESULT_ADDRESS_KEY) ?: "")
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.decreaseCounter.setOnClickListener {
            val potentialCounter = viewModel.counterState.value?.minus(1)
            if (COUNTER_RANGE.contains(potentialCounter))
                viewModel.counterState.value = potentialCounter
        }
        binding.increaseCounter.setOnClickListener {
            val potentialCounter = viewModel.counterState.value?.plus(1)
            if (COUNTER_RANGE.contains(potentialCounter))
                viewModel.counterState.value = potentialCounter
        }
        binding.deliveryDateText.setOnClickListener {
            showDatePickerDialog()
        }
        binding.houseText.setOnClickListener {
            startForResultLauncher?.launch(MapActivity.createStartIntent(requireContext()))
        }
        binding.apartmentText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                binding.apartmentLayout.clearFocus()
                true
            } else false
        }
        binding.createOrderButton.findViewById<MaterialButton>(R.id.buttonLoadable)
            .setOnClickListener {
                if (isFieldsValid()) {
                    viewModel.currentDate.value?.let { date ->
                        viewModel.onOrderCreationClicked(
                            binding.houseText.text.toString(),
                            binding.apartmentText.text.toString(),
                            date,
                            args.product.id,
                            args.product.size,
                            binding.productCounter.text.toString().toInt()
                        )
                    }
                }
            }
        binding.houseText.addTextChangedListener {
            binding.houseLayout.error = ""
            binding.houseLayout.isErrorEnabled = false
        }
        binding.apartmentText.addTextChangedListener {
            binding.apartmentLayout.error = ""
            binding.apartmentLayout.isErrorEnabled = false
        }
        binding.deliveryDateText.addTextChangedListener {
            binding.deliveryDateLayout.error = ""
            binding.deliveryDateLayout.isErrorEnabled = false
        }
    }

    private fun setObservers() {
        viewModel.orderState.observe(viewLifecycleOwner) { product ->
            val cornerRadius = resources.getDimension(R.dimen.corner_radius_primary).toInt()
            Glide.with(binding.productImage)
                .load(product.preview)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(cornerRadius)
                    )
                )
                .placeholder(R.drawable.placeholder_insets)
                .into(binding.productImage)
            binding.productTitle.text = product.title
            binding.productCountry.text = product.department
            binding.createOrderButton.setText(
                requireContext().getString(R.string.buy_for_price, product.price)
            )
        }

        viewModel.counterState.observe(viewLifecycleOwner) { counter ->
            binding.productCounter.text = counter.toString()
            viewModel.orderState.value?.let { product ->
                binding.createOrderButton.setText(
                    requireContext().getString(
                        R.string.buy_for_price,
                        (counter * product.getIntPrice()).formatPrice()
                    )
                )
            }
            updateCounterView(counter)
        }

        viewModel.orderCreationResponseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseStates.Success -> {
                    binding.createOrderButton.setStateData()
                    val dateFormat: DateFormat = SimpleDateFormat(
                        RESPONSE_DATE_PATTERN, Locale.getDefault()
                    )
                    val snackbar = Snackbar.make(
                        binding.root,
                        getString(
                            R.string.order_creation_success,
                            state.data.number,
                            dateFormat.format(state.data.createdDelivery),
                        ),
                        Snackbar.LENGTH_LONG
                    )
                    requireContext().showStylizedSnackbar(snackbar, SnackbarEvents.InfoEvent)
                }

                is ResponseStates.Failure -> {
                    binding.createOrderButton.setStateData()
                    val snackbar = Snackbar.make(
                        binding.root,
                        getString(R.string.order_creation_error),
                        Snackbar.LENGTH_LONG
                    )
                    requireContext().showStylizedSnackbar(snackbar, SnackbarEvents.ErrorEvent)
                }

                is ResponseStates.Loading -> {
                    binding.createOrderButton.setStateLoading()
                }
            }
        }

        viewModel.currentDate.observe(viewLifecycleOwner) { date ->
            val dateFormat: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
            binding.deliveryDateText.setText(dateFormat.format(date))
        }
    }

    private fun updateCounterView(counter: Int) {
        when (counter) {
            COUNTER_RANGE.first -> {
                binding.decreaseCounter.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.counter_decrease_disable
                    )
            }

            COUNTER_RANGE.last -> {
                binding.increaseCounter.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.counter_increase_disable
                    )
            }

            COUNTER_RANGE.first + 1 -> {
                binding.decreaseCounter.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.counter_decrease_enable
                    )
            }

            COUNTER_RANGE.last - 1 -> {
                binding.increaseCounter.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.counter_increase_enable
                    )
            }
        }
    }

    private fun isFieldsValid(): Boolean {
        var isFieldsValid = true
        if (binding.houseText.text.isNullOrEmpty()) {
            binding.houseLayout.isErrorEnabled = true
            binding.houseLayout.error = getString(R.string.input_empty_error)
            isFieldsValid = false
        }
        if (binding.apartmentText.text.isNullOrEmpty()) {
            binding.apartmentLayout.isErrorEnabled = true
            binding.apartmentLayout.error = getString(R.string.input_empty_error)
            isFieldsValid = false
        }
        if (binding.deliveryDateText.text.isNullOrEmpty()) {
            binding.deliveryDateLayout.isErrorEnabled = true
            binding.deliveryDateLayout.error = getString(R.string.input_empty_error)
            isFieldsValid = false
        }
        return isFieldsValid
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
                viewModel.currentDate.value = calendar.time
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}