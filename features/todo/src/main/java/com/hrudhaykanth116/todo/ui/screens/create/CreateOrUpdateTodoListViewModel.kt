package com.hrudhaykanth116.todo.ui.screens.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.ui.mappers.toDomainModel
import com.hrudhaykanth116.todo.ui.mappers.toUIModel
import com.hrudhaykanth116.todo.ui.mappers.toUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrUpdateTodoListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
) : UIStateViewModel<CreateOrUpdateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.Loading(CreateOrUpdateTodoUIState())
) {

    private val noteId: String? = savedStateHandle["id"]

    init {
        initData(noteId)

    }

    private fun initData(noteId: String?) {

        viewModelScope.launch {
            val todoModel: TodoModel? = getTaskUseCase(noteId)

            val todoUIModel: TodoUIModel = todoModel.toUIModel()

            setState {
                UIState.Idle(
                    getOrCreateContentState().copy(
                        todoUIModel = todoUIModel
                    )
                )
            }

        }
    }


    override fun processEvent(event: CreateTodoEvent) {
        val currentContentState = getOrCreateContentState()
        when (event) {

            CreateTodoEvent.Submit -> {
                viewModelScope.launch {
                    setState {
                        UIState.Loading(contentState)
                    }
                    val newState = createTodoTaskUseCase(
                        currentContentState.toDomainModel(noteId)
                    )
                    setState {
                        newState.toUIState()
                    }
                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(description = event.textFieldValue)
                        ),
                        newUserMessage = uiState.userMessage
                    )
                }
            }

            is CreateTodoEvent.CategoryChanged -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(category = event.textFieldValue)
                        ),
                        newUserMessage = uiState.userMessage
                    )
                }
            }

            is CreateTodoEvent.PriorityChanged -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(priority = event.priority)
                        ),
                        newUserMessage = uiState.userMessage
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(title = event.textFieldValue)
                        ),
                        newUserMessage = uiState.userMessage
                    )
                }
            }

            CreateTodoEvent.UserMessageShown -> {
                setState {
                    uiState.copyUIState(
                        currentContentState,
                        newUserMessage = null
                    )
                }
            }
        }
    }

    private fun getOrCreateContentState(

    ): CreateOrUpdateTodoUIState {

        return contentState ?: CreateOrUpdateTodoUIState()

    }


}