package com.hrudhaykanth116.todo.ui.screens.list

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.toState
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : UIStateViewModel<TodoListUIState, TodoListEvent, CreateTodoEffect>(
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
            observeTasksUseCase().collectLatest { todoDomainModelList ->
                setState {
                    val newContentState = contentState ?: TodoListUIState()
                    UIState.Loaded(
                        newContentState.copy(
                            list = todoDomainModelList.map { it.toState() }
                        )
                    )
                }
            }

        }
    }

    fun removeTaskItem(toDoTaskUIState: ToDoTaskUIState) {
        viewModelScope.launch {
            deleteTaskUseCase(toDoTaskUIState.data.id!!)
        }
    }

    fun getTodoList() {
        viewModelScope.launch {
            observeTasksUseCase()
        }
    }

    override fun processEvent(event: TodoListEvent) {

    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}