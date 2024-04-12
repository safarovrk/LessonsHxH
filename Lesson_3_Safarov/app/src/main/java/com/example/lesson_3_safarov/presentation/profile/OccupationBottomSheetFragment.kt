package com.example.lesson_3_safarov.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_safarov.databinding.FragmentOccupationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OccupationBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val OCCUPATION_KEY = "occupation_key"
        const val BUNDLE_OCCUPATION_KEY = "bundle_occupation_key"
    }

    private var _binding: FragmentOccupationBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOccupationBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.developer.setOnClickListener {
            setFragmentResult(
                OCCUPATION_KEY,
                bundleOf(BUNDLE_OCCUPATION_KEY to binding.developer.text)
            )
            findNavController().popBackStack()
        }
        binding.tester.setOnClickListener {
            setFragmentResult(
                OCCUPATION_KEY,
                bundleOf(BUNDLE_OCCUPATION_KEY to binding.tester.text)
            )
            findNavController().popBackStack()
        }
        binding.manager.setOnClickListener {
            setFragmentResult(
                OCCUPATION_KEY,
                bundleOf(BUNDLE_OCCUPATION_KEY to binding.manager.text)
            )
            findNavController().popBackStack()
        }
        binding.other.setOnClickListener {
            setFragmentResult(
                OCCUPATION_KEY,
                bundleOf(BUNDLE_OCCUPATION_KEY to binding.other.text)
            )
            findNavController().popBackStack()
        }
    }
}