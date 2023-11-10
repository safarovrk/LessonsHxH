package com.example.lesson_3_safarov.presentation.ui.signin

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.FragmentSignInBinding
import com.example.lesson_3_safarov.presentation.utils.SnackbarEvents
import com.example.lesson_3_safarov.presentation.utils.showStylizedSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.buttonContainer.setOnClickListener {
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

        if (binding.textLogin.text.isNullOrEmpty()) {
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
            lifecycleScope.launch(Dispatchers.Main) {
                binding.buttonText.visibility = View.INVISIBLE
                binding.circularProgressSignIn.visibility = View.VISIBLE

                // Смотрим на индикатор...
                delay(5000)

                binding.buttonText.visibility = View.VISIBLE
                binding.circularProgressSignIn.visibility = View.INVISIBLE

                // Заглушка для демонстрации Snackbar
                val snackbar = Snackbar.make(binding.root, getString(R.string.unknown_error), Snackbar.LENGTH_LONG)
                requireContext().showStylizedSnackbar(snackbar, SnackbarEvents.ErrorEvent)
            }
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