package com.example.lesson_2_safarov.first_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_2_safarov.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, MessageActivity::class.java)
    }

    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}