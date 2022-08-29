package com.hrudhaykanth116.mafet.todo.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.mafet.todo.data.local.room.tables.TodoTask
import com.hrudhaykanth116.mafet.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.mafet.todo.domain.use_cases.GetTodoTasksUseCase
import com.hrudhaykanth116.mafet.todo.ui.data_models.ToDoTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoTasksUseCase: GetTodoTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
): ViewModel() {


    private val _todoList: SnapshotStateList<ToDoTaskUIState> = DummyTodoList.todoList.toMutableStateList()
    val todoList: List<ToDoTaskUIState>
        get() = _todoList

    init {
        // loadData()
    }

    fun removeTaskItem(toDoTaskUIState: ToDoTaskUIState){
        _todoList.remove(toDoTaskUIState)
    }

    fun getTodoList(){
        viewModelScope.launch {
            getTodoTasksUseCase()
        }
    }

    fun createTodoTask(todoTask: TodoTask){
        viewModelScope.launch {
            createTodoTaskUseCase(todoTask)
        }
    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}