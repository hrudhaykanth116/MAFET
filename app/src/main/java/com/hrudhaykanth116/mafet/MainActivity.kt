package com.hrudhaykanth116.mafet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hrudhaykanth116.mafet.databinding.ActivityMainBinding
import com.hrudhaykanth116.mafet.todo.TodoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}