package com.example.lesson_2_safarov.third_task

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_2_safarov.R
import com.example.lesson_2_safarov.databinding.ActivityLifecycleTestBinding

class LifecycleTestActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, LifecycleTestActivity::class.java)
    }

    private lateinit var binding: ActivityLifecycleTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifecycleTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("Host activity", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Host activity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Host activity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Host activity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Host activity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Host activity", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Host activity", "onDestroy")
    }
}