package com.hrudhaykanth116.weather.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hrudhaykanth116.weather.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    companion object {

        fun start(context: Context) {
            context.startActivity(
                Intent(context.applicationContext, WeatherActivity::class.java)
            )
        }

    }


}