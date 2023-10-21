package com.example.lesson_2_safarov.second_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.lesson_2_safarov.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, BottomNavigationActivity::class.java)
    }

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.firstFragmentButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, FirstFragment::class.java, bundleOf())
                .commit()
        }
        binding.secondFragmentButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, SecondFragment::class.java, bundleOf())
                .commit()
        }
        binding.thirdFragmentButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, ThirdFragment::class.java, bundleOf())
                .commit()
        }
    }
}