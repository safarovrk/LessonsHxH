package com.example.lesson_4_safarov

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lesson_4_safarov.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setListeners() {
        binding.button.setOnClickListener {
            viewModel.onButtonClicked(binding.edittext.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.stringValue.observe(viewLifecycleOwner) {
            binding.textview.text = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}