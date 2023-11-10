package com.example.lesson_4_safarov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_4_safarov.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}