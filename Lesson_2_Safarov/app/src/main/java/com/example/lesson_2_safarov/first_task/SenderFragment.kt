package com.example.lesson_2_safarov.first_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.lesson_2_safarov.databinding.FragmentSenderBinding
import androidx.navigation.fragment.findNavController

class SenderFragment : Fragment() {

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_MESSAGE_KEY = "message"
    }

    private lateinit var binding: FragmentSenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSenderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(BUNDLE_MESSAGE_KEY)
            binding.messageText.text = result
        }
        binding.nextFragmentButton.setOnClickListener {
            findNavController().navigate(
                SenderFragmentDirections.actionSenderFragmentToRecipientFragment(
                    binding.messageText.text.toString()
                )
            )
        }
    }
}