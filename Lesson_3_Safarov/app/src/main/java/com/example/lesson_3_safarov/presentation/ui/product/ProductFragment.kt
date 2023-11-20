package com.example.lesson_3_safarov.presentation.ui.product

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.databinding.FragmentProductBinding
import com.example.lesson_3_safarov.domain.product.Product
import com.example.lesson_3_safarov.presentation.exception.getError
import com.example.lesson_3_safarov.presentation.ui.product.size.SizeBottomSheetFragment
import com.example.lesson_3_safarov.presentation.utils.setTextAppearanceCompat
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ProductFragment : Fragment() {

    companion object {
        private const val VIEW_FLIPPER_LOADING = 0
        private const val VIEW_FLIPPER_ERROR = 1
        private const val VIEW_FLIPPER_PRODUCT = 2
    }

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val args: ProductFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: ImageAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        ProductViewModel::class,
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
        _binding = FragmentProductBinding.inflate(inflater)
        binding.materialToolbar.title = args.title
        viewModel.onLoadData(args.id)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.black_20pc)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imagesRecyclerView.adapter = adapter
        // Отключает анимацию "мигания" при обновлении элементов
        binding.imagesRecyclerView.itemAnimator = null
        setListeners()
        setObservers()
        setStateObserver()
    }

    private fun setObservers() {
        viewModel.currentSelectedSize.observe(viewLifecycleOwner) {
            binding.sizeText.setText(it)
        }
    }

    private fun setListeners() {
        binding.errorRefreshButton.setOnClickListener {
            viewModel.onLoadData(args.id)
        }
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.sizeText.setOnClickListener {
            findNavController().navigate(
                ProductFragmentDirections.actionProductFragmentToSizeBottomSheetFragment(
                    (viewModel.productState.value as ResponseStates.Success<Product>)
                        .data.sizes.toTypedArray()
                )
            )
        }
        setFragmentResultListener(SizeBottomSheetFragment.SIZE_KEY) { _, bundle ->
            val result = bundle.getString(SizeBottomSheetFragment.BUNDLE_SIZE_KEY)
            viewModel.currentSelectedSize.value = result
        }
    }

    private fun setStateObserver() {
        viewModel.productState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseStates.Success -> {
                    fillUi(state.data)
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_PRODUCT
                }

                is ResponseStates.Failure -> {
                    binding.unexpectedErrorDescription.text = state.e.getError()
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_ERROR
                }

                is ResponseStates.Loading -> {
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_LOADING
                }
            }
        }
    }

    private fun fillUi(product: Product) {
        val cornerRadius = resources.getDimension(R.dimen.corner_radius_secondary).toInt()

        // Загрузка основного изображения
        Glide.with(binding.productFullImage)
            .load(product.images[0])
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(cornerRadius)
                )
            )
            .placeholder(R.drawable.placeholder_insets)
            .into(binding.productFullImage)

        // Загрузка и добавление в recyclerView всех доступных изображений
        val list = mutableListOf<ImageItem>()
        product.images.forEach { imageUlr ->
            list.add(
                ImageItem(
                    imageUlr,
                    itemClickListener = {
                        Glide.with(binding.productFullImage)
                            .load(it)
                            .transform(
                                MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(cornerRadius)
                                )
                            )
                            .placeholder(R.drawable.placeholder_insets)
                            .into(binding.productFullImage)
                    },
                    isFocused = false
                )
            )
        }

        // Реализация дополнительного условия, добавление недостающих элементов
        while (list.size < 3) {
            list.add(
                ImageItem(
                    "",
                    itemClickListener = {
                        Glide.with(binding.productFullImage)
                            .load(it)
                            .transform(
                                MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(cornerRadius)
                                )
                            )
                            .placeholder(R.drawable.placeholder_insets)
                            .into(binding.productFullImage)
                    },
                    isFocused = false
                )
            )
        }
        list[0].isFocused = true
        adapter.submitList(list)

        binding.priceText.text = product.price
        binding.titleText.text = product.title
        binding.badgeText.text = product.badge[0].value
        binding.badgeText.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(product.badge[0].color))
        binding.descriptionText.text = product.description
        binding.countryText.text = product.department

        // Создание маркированного списка деталей
        product.details.forEach {
            val spanString = SpannableString(it)
            spanString.setSpan(
                BulletSpan(resources.getDimensionPixelSize(R.dimen.normal_50)),
                0,
                it.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val textView = TextView(requireContext())
            textView.text = spanString

            requireContext().setTextAppearanceCompat(
                textView,
                R.style.TextAppearance_Secondary_Details
            )

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(
                resources.getDimensionPixelSize(R.dimen.normal_50),
                resources.getDimensionPixelSize(R.dimen.normal_25),
                0,
                0)

            textView.layoutParams = layoutParams

            binding.detailsContainer.addView(textView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}