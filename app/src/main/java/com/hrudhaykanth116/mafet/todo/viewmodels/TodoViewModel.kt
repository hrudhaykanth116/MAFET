package com.hrudhaykanth116.mafet.todo.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hrudhaykanth116.mafet.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.mafet.todo.data.models.ToDoTask

class TodoViewModel: ViewModel() {


    private val _todoList: SnapshotStateList<ToDoTask> = DummyTodoList.todoList.toMutableStateList()
    val todoList: List<ToDoTask>
        get() = _todoList

    init {
        // loadData()
    }

    fun removeTaskItem(toDoTask: ToDoTask){
        _todoList.remove(toDoTask)
    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}