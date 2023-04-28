package com.hrudhaykanth116.todo.ui.models

import com.hrudhaykanth116.core.utils.nav.NavRoute

sealed class TodoNavScreen(val route: String){
    object TodoListScreen: TodoNavScreen("todo_list_screen")
    object CreateTodoScreen: TodoNavScreen("create_todo_screen")
    object TodoDetailsScreen: TodoNavScreen("todo_details_screen")
}

// object TodoDetailsScreenRoute: NavRoute<TodoDetailsArgs>(
//     "todo_details_screen"
// ) {
//
//     override fun putArgs(args: TodoDetailsArgs) {
//
//     }
//
//     override fun getArgs(): TodoDetailsArgs {
//
//     }
// }
//
// data class TodoDetailsArgs(
//     val id: String,
// )
