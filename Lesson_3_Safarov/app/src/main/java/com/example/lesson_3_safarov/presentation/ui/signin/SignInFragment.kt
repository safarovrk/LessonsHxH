package com.example.lesson_3_safarov.presentation.ui.signin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.databinding.FragmentSignInBinding
import com.example.lesson_3_safarov.presentation.utils.SnackbarEvents
import com.example.lesson_3_safarov.presentation.utils.showStylizedSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        SignInViewModel::class,
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
        _binding = FragmentSignInBinding.inflate(inflater)
        setListeners()
        setStateObserver()
        return binding.root
    }

    private fun setStateObserver() {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ResponseStates.Success -> {
                    binding.progressButton.isLoading = false
                    navigateToCatalog()
                }
                is ResponseStates.Failure -> {
                    binding.progressButton.isLoading = false
                    val snackbar = Snackbar.make(binding.root, state.e.message.toString(), Snackbar.LENGTH_LONG)
                    requireContext().showStylizedSnackbar(snackbar, SnackbarEvents.ErrorEvent)
                }
                is ResponseStates.Loading -> {
                    binding.progressButton.isLoading = true
                }
            }
        }
    }

    private fun setListeners() {
        binding.progressButton.setOnClickListener {
            signInClicked()
        }
        binding.textPassword.addTextChangedListener {
            binding.layoutPassword.error = ""
            binding.layoutPassword.isErrorEnabled = false
        }
        binding.textLogin.addTextChangedListener {
            binding.layoutLogin.error = ""
            binding.layoutLogin.isErrorEnabled = false
        }
        binding.textPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                signInClicked()
                true
            } else false
        }
    }

    private fun signInClicked() {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        binding.layoutLogin.clearFocus()
        binding.layoutPassword.clearFocus()

        var isFieldsValid = true

        if (binding.textLogin.text.isNullOrEmpty()
            || !Patterns.EMAIL_ADDRESS.matcher(binding.textLogin.text.toString()).matches()) {
            binding.layoutLogin.isErrorEnabled = true
            binding.layoutLogin.error = getString(R.string.input_empty_error)
            isFieldsValid = false
        } else {
            binding.layoutLogin.error = ""
            binding.layoutLogin.isErrorEnabled = false
        }

        if (binding.textPassword.text.isNullOrEmpty()) {
            binding.layoutPassword.isErrorEnabled = true
            binding.layoutPassword.error = getString(R.string.input_empty_error)
            isFieldsValid = false
        } else {
            binding.layoutPassword.error = ""
            binding.layoutPassword.isErrorEnabled = false
        }

        if (isFieldsValid) {
                viewModel.onSignIn(
                    binding.textLogin.text.toString(),
                    binding.textPassword.text.toString()
                )
        }
    }

    private fun navigateToCatalog() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToCatalogFragment()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}