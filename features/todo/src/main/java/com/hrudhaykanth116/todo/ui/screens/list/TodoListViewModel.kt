package com.hrudhaykanth116.todo.ui.screens.list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.DeleteTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.ObserveTasksUseCase
import com.hrudhaykanth116.todo.ui.mappers.toState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListScreenEvent
import com.hrudhaykanth116.todo.ui.models.todolist.TodoListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : UIStateViewModel<TodoListUIState, TodoListScreenEvent, CreateTodoEffect>(
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

    private fun removeTaskItem(id: String) {
        viewModelScope.launch {
            deleteTaskUseCase(id)
        }
    }

    fun getTodoList() {
        viewModelScope.launch {
            observeTasksUseCase()
        }
    }

    override fun processEvent(event: TodoListScreenEvent) {
        when (event) {
            is TodoListScreenEvent.RemoveTask -> removeTaskItem(event.id)
            is TodoListScreenEvent.TodoTaskTitleChanged -> onTodoTaskTitleChanged(event.textFieldValue)
            is TodoListScreenEvent.CreateTodoTask -> createTodoTask(event.taskTitle)
        }
    }

    private fun createTodoTask(taskTitle: String) {
        viewModelScope.launch {
            val result: DomainState<CreateOrUpdateTodoDomainModel> = createTodoTaskUseCase(
                CreateOrUpdateTodoDomainModel(title = taskTitle)
            )
            when (result) {
                is DomainState.ErrorDomainState -> {

                }
                is DomainState.LoadedDomainState -> {
                    onTodoTaskTitleChanged(TextFieldValue())
                }
                is DomainState.LoadingDomainState -> {

                }
            }
        }
    }

    private fun onTodoTaskTitleChanged(textFieldValue: TextFieldValue) {
        setState {
            val newContentState = contentState ?: TodoListUIState()
            UIState.Loaded(
                newContentState.copy(
                    todoTitle = textFieldValue
                )
            )
        }
    }

    // private fun loadData() {
    //     _todoList = DummyTodoList.todoList.toMutableStateList()
    // }

    // fun setListItemExpanded(toDoTask: ToDoTask, isExpanded: Boolean){
    //     _todoList
    // }

}