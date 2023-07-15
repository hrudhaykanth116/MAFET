package com.hrudhaykanth116.todo.ui.models

sealed class TodoNavScreen(val route: String){
    object TodoListScreen: TodoNavScreen("todo_list_screen")
    object CreateOrUpdateTodoScreen: TodoNavScreen("create_todo_screen/{id}")
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
