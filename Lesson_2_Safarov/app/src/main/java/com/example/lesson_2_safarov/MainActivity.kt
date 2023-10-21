package com.example.lesson_2_safarov

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_2_safarov.databinding.ActivityMainBinding
import com.example.lesson_2_safarov.first_task.MessageActivity
import com.example.lesson_2_safarov.second_task.BottomNavigationActivity
import com.example.lesson_2_safarov.third_task.LifecycleTestActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.firstTaskButton.setOnClickListener {
            startActivity(MessageActivity.createIntent(this))
        }
        binding.secondTaskButton.setOnClickListener {
            startActivity(BottomNavigationActivity.createIntent(this))
        }
        binding.thirdTaskButton.setOnClickListener {
            startActivity(LifecycleTestActivity.createIntent(this))
        }
    }
}