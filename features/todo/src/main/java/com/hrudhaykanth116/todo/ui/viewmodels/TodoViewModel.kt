package com.hrudhaykanth116.todo.ui.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTodoTasksUseCase
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoTasksUseCase: com.hrudhaykanth116.todo.domain.use_cases.GetTodoTasksUseCase,
    private val createTodoTaskUseCase: com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase,
): ViewModel() {


    private val _todoList: SnapshotStateList<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState> = com.hrudhaykanth116.todo.data.dummydata.DummyTodoList.todoList.toMutableStateList()
    val todoList: List<com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState>
        get() = _todoList

    init {
        // loadData()
    }

    fun removeTaskItem(toDoTaskUIState: com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState){
        _todoList.remove(toDoTaskUIState)
    }

    fun getTodoList(){
        viewModelScope.launch {
            getTodoTasksUseCase()
        }
    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}