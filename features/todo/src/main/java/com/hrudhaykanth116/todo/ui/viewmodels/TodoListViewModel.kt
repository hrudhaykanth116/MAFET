package com.hrudhaykanth116.todo.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTodoTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.toState
import com.hrudhaykanth116.todo.ui.mappers.toUIModel
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import com.hrudhaykanth116.todo.ui.screens.list.TodoListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodoTasksUseCase: GetTodoTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
) : UIStateViewModel<TodoListUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.Loaded(TodoListUIState())
) {


    // private val _todoList: SnapshotStateList<ToDoTaskUIState> = DummyTodoList.todoList.toMutableStateList()
    // val todoList: List<ToDoTaskUIState>
    //     get() = _todoList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val getTasksResult: List<TodoModel> = getTodoTasksUseCase() ?: listOf()
            setState {
                val newContentState = contentState ?: TodoListUIState()
                UIState.Loaded(
                    newContentState.copy(
                        list = getTasksResult.map { it.toState() }
                    )
                )
            }
        }
    }

    fun removeTaskItem(toDoTaskUIState: ToDoTaskUIState) {
        // _todoList.remove(toDoTaskUIState)
    }

    fun getTodoList() {
        viewModelScope.launch {
            getTodoTasksUseCase()
        }
    }

    override fun processEvent(event: CreateTodoEvent) {
        when (event) {
            CreateTodoEvent.Create -> TODO()
            is CreateTodoEvent.DescriptionChanged -> TODO()
            is CreateTodoEvent.TitleChanged -> TODO()
        }
    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}