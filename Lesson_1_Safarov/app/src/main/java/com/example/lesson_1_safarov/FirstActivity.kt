package com.example.lesson_1_safarov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1_safarov.databinding.ActivityFirstBinding
import java.util.TreeSet

class FirstActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, FirstActivity::class.java)
    }

    private lateinit var binding: ActivityFirstBinding
    private var names: TreeSet<String> = TreeSet<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.saveNameButton.setOnClickListener {
            names.add(binding.nameEdittext.text.toString())
            binding.nameEdittext.text.clear()
        }
        binding.showNamesButton.setOnClickListener {
            binding.namesTextview.text = names.joinToString(separator = "\n") { it }
        }
    }
}