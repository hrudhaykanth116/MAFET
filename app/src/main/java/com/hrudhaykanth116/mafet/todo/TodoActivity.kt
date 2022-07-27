package com.hrudhaykanth116.mafet.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.mafet.databinding.ActivityTodoBinding
import com.hrudhaykanth116.mafet.todo.adapters.ToDoListAdapter
import com.hrudhaykanth116.mafet.todo.viewmodels.TodoViewModel

class TodoActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TodoListScreen()
            }
        }

    }


    companion object{

        public fun start(context: Context){

            context.startActivity(
                Intent(context, TodoActivity::class.java)
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun TodoListScreen(){
    Text(text = "Todo list screen")
}