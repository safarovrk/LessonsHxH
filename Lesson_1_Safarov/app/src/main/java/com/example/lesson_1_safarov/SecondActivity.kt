package com.example.lesson_1_safarov

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1_safarov.databinding.ActivitySecondBinding
import kotlin.random.Random
import kotlin.random.nextULong

class SecondActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, SecondActivity::class.java)
        const val NAME_STUDENT_INDEX = 0
        const val SURNAME_STUDENT_INDEX = 1
        const val GRADE_STUDENT_INDEX = 2
        const val BIRTHDAY_STUDENT_INDEX = 3
    }

    private lateinit var binding: ActivitySecondBinding
    private val students: HashMap<ULong, Student> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.studentEdittext.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {

                // Простейшая валидация исключительно чтобы ничего не ломалось
                if (binding.studentEdittext.text.isEmpty()) return@setOnKeyListener false
                val rawStudent = binding.studentEdittext.text.toString().split(" ")
                if (rawStudent.size != 4) return@setOnKeyListener false

                val currentStudent = Student(
                    Random.nextULong(),
                    rawStudent[NAME_STUDENT_INDEX],
                    rawStudent[SURNAME_STUDENT_INDEX],
                    rawStudent[GRADE_STUDENT_INDEX],
                    rawStudent[BIRTHDAY_STUDENT_INDEX]
                )
                students[currentStudent.id] = currentStudent
                binding.studentEdittext.text.clear()
                return@setOnKeyListener true
            }
            false
        }
        binding.showStudentButton.setOnClickListener {
            binding.studentsTextview.text = ""
            for (student in students) binding.studentsTextview.append("\n ${student.value}")
        }
    }
}