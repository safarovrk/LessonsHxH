package com.example.lesson_5_safavor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_5_safavor.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRandomDataToChart()
    }

    private fun setRandomDataToChart() {
        val simpleDateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())

        val currentDate = Date()
        val dayInMillis = TimeUnit.DAYS.toMillis(1)

        val randomDatesList: ArrayList<Date> = arrayListOf()
        randomDatesList.add(currentDate)
        for (i in 0..7) {
            randomDatesList.add(
                Date(randomDatesList[i].time - (Random.nextLong(1, 30) * dayInMillis))
            )
        }
        randomDatesList.reverse()

        val data: ArrayList<Pair<String, Int>> = arrayListOf()

        randomDatesList.forEach {
            data.add(Pair(simpleDateFormat.format(it), Random.nextInt(-10, 110)))
        }

        binding.customView.setData(data)
    }
}