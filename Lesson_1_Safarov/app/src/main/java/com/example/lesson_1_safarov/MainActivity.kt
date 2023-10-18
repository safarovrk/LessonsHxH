package com.example.lesson_1_safarov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1_safarov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.firstActivityButton.setOnClickListener {
            startActivity(FirstActivity.createIntent(this))
        }
        binding.secondActivityButton.setOnClickListener {
            startActivity(SecondActivity.createIntent(this))
        }
    }
}