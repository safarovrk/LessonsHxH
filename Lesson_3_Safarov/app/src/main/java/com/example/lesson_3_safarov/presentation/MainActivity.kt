package com.example.lesson_3_safarov.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.lesson_3_safarov.databinding.ActivityMainBinding
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fitContentViewToInsets()
    }

    private fun fitContentViewToInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}