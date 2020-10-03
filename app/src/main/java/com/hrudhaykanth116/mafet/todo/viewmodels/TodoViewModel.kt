package com.hrudhaykanth116.mafet.todo.viewmodels

import androidx.lifecycle.ViewModel
import com.hrudhaykanth116.mafet.todo.models.ToDoTask

class TodoViewModel: ViewModel() {

    public val items: List<ToDoTask> = listOf(
        ToDoTask("test1", true),
        ToDoTask("test1", true),
        ToDoTask("test1", false),
        ToDoTask("test1", true),
        ToDoTask("test1", false),
        ToDoTask("test1", true),
        ToDoTask("test1", false),
        ToDoTask("test1", false),
        ToDoTask("test1", true)
    )

}