package com.example.lesson_2_safarov.second_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
            supportFragmentManager.commit {
                replace<FirstFragment>(binding.fragmentContainer.id)
            }
        }
        binding.secondFragmentButton.setOnClickListener {
            supportFragmentManager.commit {
                replace<SecondFragment>(binding.fragmentContainer.id)
            }
        }
        binding.thirdFragmentButton.setOnClickListener {
            supportFragmentManager.commit {
                replace<ThirdFragment>(binding.fragmentContainer.id)
            }
        }
    }
}