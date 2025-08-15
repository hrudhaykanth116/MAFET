package com.hrudhaykanth116.todo.ui.screens.create

import android.R.attr.category
import android.R.attr.description
import android.R.attr.priority
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
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
    private val networkMonitor: NetworkMonitor,
    private val dateTimeUtils: DateTimeUtils,
) : UIStateViewModel<CreateOrUpdateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    initialState = UIState.Loading(CreateOrUpdateTodoUIState()),
    defaultState = CreateOrUpdateTodoUIState(),
    networkMonitor = networkMonitor
) {

    private val noteId: String? = savedStateHandle["id"]

    init {
        initData(noteId)

    }

    private fun initData(noteId: String?) {

        viewModelScope.launch {
            val todoModel: TodoModel? = getTaskUseCase(noteId)

            val todoUIModel = if (todoModel == null) {
                TodoUIModel()
            } else {
                with(todoModel) {

                    val dateTime = targetTime?.let { dateTimeUtils.getFormattedDateTime(it) } ?: ""

                    TodoUIModel(
                        id = id,
                        title = TextFieldValue(title),
                        description = TextFieldValue(description),
                        category = TextFieldValue(category),
                        priority = priority,
                        targetTime = TextFieldValue(dateTime),
                    )
                }
            }

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

                    val domainModel: CreateOrUpdateTodoDomainModel = with(currentContentState) {
                        CreateOrUpdateTodoDomainModel(
                            id = noteId,
                            isInEditMode = isInEditMode,
                            title = todoUIModel.title.text,
                            description = todoUIModel.description.text,
                            category = todoUIModel.category.text,
                            priority = todoUIModel.priority,
                            isSubmitted = isSubmitted,
                            targetTime = dateTimeUtils.getMillisFromDateTime(todoUIModel.targetTime.text)
                        )
                    }

                    val newState: DomainState<CreateOrUpdateTodoDomainModel> = createTodoTaskUseCase(
                        domainModel
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

            CreateTodoEvent.OnTargetFieldClicked -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            showTargetTimePicker = true
                        ),
                    )
                }
            }

            is CreateTodoEvent.OnTargetTimeChanged -> {

                val formatedDateTime = dateTimeUtils.getFormattedDateTime(event.timeMillis)

                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(
                                targetTime = TextFieldValue(
                                    text = formatedDateTime
                                )
                            )
                        ),
                    )
                }
            }

            CreateTodoEvent.OnTargetTimeDateTimePickerCloseRequest -> {
                setState {
                    uiState.copyUIState(
                        currentContentState.copy(
                            showTargetTimePicker = false
                        ),
                    )
                }
            }
        }
    }

    override fun onRetry() {
        initData(noteId)
    }

    private fun getOrCreateContentState(

    ): CreateOrUpdateTodoUIState {

        return currentContentState

    }


}