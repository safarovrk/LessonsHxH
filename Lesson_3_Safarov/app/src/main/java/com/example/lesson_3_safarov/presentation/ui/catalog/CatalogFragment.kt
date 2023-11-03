package com.example.lesson_3_safarov.presentation.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.FragmentCatalogBinding
import com.example.lesson_3_safarov.presentation.utils.SnackbarEvents
import com.example.lesson_3_safarov.presentation.utils.showStylizedSnackbar
import com.google.android.material.snackbar.Snackbar

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}