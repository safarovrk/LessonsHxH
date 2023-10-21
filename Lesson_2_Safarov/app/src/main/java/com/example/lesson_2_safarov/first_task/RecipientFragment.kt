package com.example.lesson_2_safarov.first_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson_2_safarov.databinding.FragmentRecipientBinding

class RecipientFragment : Fragment() {

    private lateinit var binding: FragmentRecipientBinding
    private val args: RecipientFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipientBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewData()
        setListeners()
    }

    private fun setViewData() {
        binding.messageEdittext.setText(args.message)
    }

    private fun setListeners() {
        binding.saveMessageButton.setOnClickListener {
            val resultMessage = binding.messageEdittext.text.toString()
            setFragmentResult(
                SenderFragment.REQUEST_KEY,
                bundleOf(SenderFragment.BUNDLE_MESSAGE_KEY to resultMessage)
            )
            findNavController().popBackStack()
        }
    }
}