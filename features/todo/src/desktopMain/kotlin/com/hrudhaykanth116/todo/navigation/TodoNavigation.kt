// package com.hrudhaykanth116.todo.navigation
//
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
// import com.hrudhaykanth116.todo.ui.screens.create.CreateOrUpdateTodoScreen
// import com.hrudhaykanth116.todo.ui.screens.list.TodoListScreen
//
// @Composable
// fun TodoNavigation(
//     onBackClicked: () -> Unit = {},
// ) {
//     var currentScreen by remember { mutableStateOf<TodoScreen>(TodoScreen.List) }
//     var selectedTodoId by remember { mutableStateOf<String?>(null) }
//
//     when (val screen = currentScreen) {
//         is TodoScreen.List -> {
//             TodoListScreen(
//                 navigateToCreateScreen = {
//                     selectedTodoId = null
//                     currentScreen = TodoScreen.Create
//                 },
//                 onItemClicked = {
//                     selectedTodoId = it.id
//                     currentScreen = TodoScreen.Create
//                 },
//                 onBackClicked = onBackClicked
//             )
//         }
//         is TodoScreen.Create -> {
//             CreateOrUpdateTodoScreen(
//                 isInEditMode = true,
//                 noteId = selectedTodoId,
//                 onCreated = {
//                     currentScreen = TodoScreen.List
//                 },
//                 onBackClicked = {
//                     currentScreen = TodoScreen.List
//                 }
//             )
//         }
//     }
// }
//
// private sealed class TodoScreen {
//     data object List : TodoScreen()
//     data object Create : TodoScreen()
// }
