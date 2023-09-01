package com.hrudhaykanth116.todo.ui.models.todolist

import androidx.compose.ui.text.input.TextFieldValue

sealed interface TodoListScreenEvent {

    data class RemoveTasks(val taskIdsToDelete: List<String>): TodoListScreenEvent

    data class TodoTaskTitleChanged(val textFieldValue: TextFieldValue): TodoListScreenEvent

    data class CreateTodoTask(val taskTitle: String): TodoListScreenEvent
    data class Search(val searchText: String): TodoListScreenEvent

    // Can have Filter(category, priority, due date today)
    data class FilterCategory(val filterCategory: String): TodoListScreenEvent
    data class MenuItemSelected(val menuItem: TodoListScreenMenuItem): TodoListScreenEvent

    object CategoryIconClicked: TodoListScreenEvent
    object CategoryListMenuDismiss: TodoListScreenEvent
    object SearchIconClicked: TodoListScreenEvent
    object MenuIconClicked: TodoListScreenEvent

}