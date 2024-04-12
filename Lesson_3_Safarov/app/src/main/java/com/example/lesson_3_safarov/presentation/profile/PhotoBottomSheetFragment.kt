package com.example.lesson_3_safarov.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.lesson_3_safarov.databinding.FragmentPhotoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val PHOTO_KEY = "photo_key"
        const val BUNDLE_PHOTO_KEY = "bundle_photo_key"
        const val MAKE_PHOTO_INTENT_ID = 0
        const val CHOOSE_PHOTO_INTENT_ID = 1
    }

    private var _binding: FragmentPhotoBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.makePhoto.setOnClickListener {
            setFragmentResult(
                PHOTO_KEY,
                bundleOf(BUNDLE_PHOTO_KEY to MAKE_PHOTO_INTENT_ID)
            )
            findNavController().popBackStack()
        }
        binding.chooseFromGallery.setOnClickListener {
            setFragmentResult(
                PHOTO_KEY,
                bundleOf(BUNDLE_PHOTO_KEY to CHOOSE_PHOTO_INTENT_ID)
            )
            findNavController().popBackStack()
        }
    }
}