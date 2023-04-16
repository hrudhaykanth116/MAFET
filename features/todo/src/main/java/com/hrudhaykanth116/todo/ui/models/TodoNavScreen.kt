package com.hrudhaykanth116.todo.ui.models

sealed class TodoNavScreen(val route: String){
    object TodoListScreen: TodoNavScreen("todo_list_screen")
    object CreateTodoScreen: TodoNavScreen("create_todo_screen")
}
