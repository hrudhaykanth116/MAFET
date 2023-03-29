package com.hrudhaykanth116.mafet.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hrudhaykanth116.mafet.todo.ui.components.TodoList
import com.hrudhaykanth116.mafet.todo.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
// Research about viewModel()
fun TodoListScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
) {

    // Property delegation helps us in remembering the value of state directly into the field instead
    // Remember savable saves the values over configuration changes.
    var test by rememberSaveable() {
        mutableStateOf(true)
    }

   // val list by todoViewModel.todoList.observeAsState(listOf())
    Box(modifier = Modifier.background(color = Color.Yellow)) {

        val coroutineScope = rememberCoroutineScope()

        // Check state hoisting.
        val listState = rememberLazyListState()

        TodoList(
            list = todoViewModel.todoList,
            listState = listState,
            modifier = Modifier.fillMaxSize(),
            onRemoveTask = { toDoTask ->  todoViewModel.removeTaskItem(toDoTask) }
        )

        // Show the button if the first visible item is past
        // the first item. We use a remembered derived` state to
        // minimize unnecessary compositions
        val shouldShowScrollToTopBtn by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }


        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {

            AnimatedVisibility(
                visible = true,
            ) {

                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Add Task"
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(onClick = {
                // navController.navigate(Screen.CreateTodoScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }

        }

    }
}