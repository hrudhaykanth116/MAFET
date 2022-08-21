package com.hrudhaykanth116.mafet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hrudhaykanth116.mafet.auth.ui.screens.LoginActivity
import com.hrudhaykanth116.mafet.databinding.ActivityMainBinding
import com.hrudhaykanth116.mafet.todo.TodoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.todoScreenBtn.setOnClickListener{
            TodoActivity.start(this)
        }

    }
}