package com.example.lesson_2_safarov.third_task

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lesson_2_safarov.databinding.FragmentBottomBinding

class BottomFragment : Fragment() {

    private lateinit var binding: FragmentBottomBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("Bottom fragment", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Bottom fragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.i("Bottom fragment", "onCreateView")
        binding = FragmentBottomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Bottom fragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Bottom fragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Bottom fragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Bottom fragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Bottom fragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Bottom fragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Bottom fragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("Bottom fragment", "onDetach")
    }
}