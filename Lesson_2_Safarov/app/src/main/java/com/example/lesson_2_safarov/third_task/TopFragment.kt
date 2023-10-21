package com.example.lesson_2_safarov.third_task

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson_2_safarov.databinding.FragmentTopBinding

class TopFragment : Fragment() {

    private lateinit var binding: FragmentTopBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("Top fragment", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Top fragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.i("Top fragment", "onCreateView")
        binding = FragmentTopBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Top fragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Top fragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Top fragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Bottom fragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Top fragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Top fragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Top fragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("Top fragment", "onDetach")
    }
}