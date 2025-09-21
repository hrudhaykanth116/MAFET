package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.toErrorMessage
import com.hrudhaykanth116.core.common.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
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
    private val uniqueIdGenerator: UniqueIdGenerator,

    ) : UIStateViewModel<CreateOrUpdateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    initialState = UIState.Loading(CreateOrUpdateTodoUIState()),
    defaultState = CreateOrUpdateTodoUIState(),
    networkMonitor = networkMonitor
) {

    private val noteId: String? = savedStateHandle["id"]

    override fun initializeData() {
        initData(noteId)
    }

    private fun initData(noteId: String?) {

        viewModelScope.launch {
            val todoModel: TodoModel? = noteId?.let {
                val getTaskResult = getTaskUseCase(it)
                when (getTaskResult) {
                    is RepoResultWrapper.Error -> {
                        null
                    }

                    is RepoResultWrapper.Success -> {
                        getTaskResult.data
                    }
                }
            }

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

                    val todoModel = with(currentContentState) {
                        TodoModel(
                            id = noteId ?: uniqueIdGenerator.getUniqueId(), // new id if new note.
                            title = todoUIModel.title.text,
                            description = todoUIModel.description.text,
                            category = todoUIModel.category.text,
                            priority = todoUIModel.priority,
                            targetTime = dateTimeUtils.getMillisFromDateTime(todoUIModel.targetTime.text)
                        )
                    }

                    val createTodoResult: RepoResultWrapper<Unit> = createTodoTaskUseCase(
                        todoModel = todoModel
                    )

                    when (createTodoResult) {
                        is RepoResultWrapper.Error -> {
                            setState {
                                UIState.Idle(
                                    currentContentState.copy(
                                        isSubmitted = true,
                                    ),
                                    userMessage = "Something went wrong. Please try again.".toErrorMessage()
                                )
                            }
                        }

                        is RepoResultWrapper.Success -> {
                            setState {
                                UIState.Idle(
                                    currentContentState.copy(
                                        isSubmitted = true,
                                    ),
                                    userMessage = "Todo saved successfully".toSuccessMessage()
                                )
                            }

                        }
                    }

                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(description = event.textFieldValue)
                        ),
                    )
                }
            }

            is CreateTodoEvent.CategoryChanged -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(category = event.textFieldValue)
                        ),
                    )
                }
            }

            is CreateTodoEvent.PriorityChanged -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(priority = event.priority)
                        ),
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(title = event.textFieldValue)
                        ),
                    )
                }
            }

            CreateTodoEvent.UserMessageShown -> {
                setState {
                    UIState.Idle(
                        currentContentState,
                        userMessage = null
                    )
                }
            }

            CreateTodoEvent.OnTargetFieldClicked -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            showTargetTimePicker = true
                        ),
                    )
                }
            }

            is CreateTodoEvent.OnTargetTimeChanged -> {

                val formatedDateTime = dateTimeUtils.getFormattedDateTime(event.timeMillis)

                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(
                                targetTime = TextFieldValue(
                                    text = formatedDateTime ?: ""
                                )
                            )
                        ),
                    )
                }
            }

            CreateTodoEvent.OnTargetTimeDateTimePickerCloseRequest -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            showTargetTimePicker = false
                        ),
                    )
                }
            }
        }
    }

    private fun getOrCreateContentState(

    ): CreateOrUpdateTodoUIState {

        return contentStateOrDefault

    }


}