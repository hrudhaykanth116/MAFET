package com.hrudhaykanth116.mafet.todo.ui.data_models

sealed class Screen(val route: String){
    object TodoListScreen: Screen("todo_list_screen")
    object CreateTodoScreen: Screen("create_todo_screen")
}
