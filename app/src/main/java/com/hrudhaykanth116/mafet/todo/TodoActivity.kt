package com.hrudhaykanth116.mafet.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrudhaykanth116.mafet.databinding.ActivityTodoBinding
import com.hrudhaykanth116.mafet.todo.adapters.ToDoListAdapter
import com.hrudhaykanth116.mafet.todo.viewmodels.TodoViewModel

class TodoActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTodoBinding = ActivityTodoBinding.inflate(layoutInflater)
        activityTodoBinding.apply {
            viewmodel = todoViewModel
            tasksList.adapter = ToDoListAdapter()
        }
        setContentView(activityTodoBinding.root)

    }


    companion object{

        public fun start(context: Context){

            context.startActivity(
                Intent(context, TodoActivity::class.java)
            )

        }

    }

}