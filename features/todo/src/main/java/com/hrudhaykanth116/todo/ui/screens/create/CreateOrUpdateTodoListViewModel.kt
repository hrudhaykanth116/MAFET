package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toSuccessMessage
import com.hrudhaykanth116.todo.R
import com.hrudhaykanth116.todo.domain.model.TaskCategory
import com.hrudhaykanth116.todo.domain.model.TodoModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTaskUseCase
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import kotlinx.coroutines.launch

class CreateOrUpdateTodoListViewModel(
    private val createTodoTaskUseCase: CreateTodoTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val networkMonitor: NetworkMonitor,
    private val dateTimeUtils: DateTimeUtils,
    private val uniqueIdGenerator: UniqueIdGenerator,
    private val todoId: String?,
) : UIStateViewModel<CreateOrUpdateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    initialState = UIState.Loading(CreateOrUpdateTodoUIState()),
    defaultState = CreateOrUpdateTodoUIState(),
    networkMonitor = networkMonitor
) {

    init {
        initializeData()
    }

    override fun initializeData() {
        initData(todoId)
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
                        category = TextFieldValue(category.key),
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
                    // Validate only title (description is optional)
                    if (currentContentState.todoUIModel.title.text.isBlank()) {
                        setState {
                            UIState.Idle(
                                currentContentState.copy(
                                    titleError = "Title is required"
                                )
                            )
                        }
                        return@launch
                    }

                    setState {
                        UIState.Loading(contentState)
                    }

                    try {
                        val todoModel = with(currentContentState) {
                            TodoModel(
                                id = todoId ?: uniqueIdGenerator.getUniqueId(), // new id if new note.
                                title = todoUIModel.title.text,
                                description = todoUIModel.description.text,
                                category = TaskCategory.fromKey(todoUIModel.category.text),
                                priority = todoUIModel.priority,
                                targetTime = if (todoUIModel.targetTime.text.isNotBlank()) {
                                    dateTimeUtils.getMillisFromDateTime(todoUIModel.targetTime.text)
                                } else {
                                    null
                                }
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
                                        userMessage = R.string.todo_error_generic.toErrorMessage()
                                    )
                                }
                            }

                            is RepoResultWrapper.Success -> {
                                setState {
                                    UIState.Idle(
                                        currentContentState.copy(
                                            isSubmitted = true,
                                        ),
                                        userMessage = R.string.todo_success_saved.toSuccessMessage()
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                        setState {
                            UIState.Idle(
                                currentContentState.copy(
                                    isSubmitted = false,
                                ),
                                userMessage = R.string.todo_error_generic.toErrorMessage()
                            )
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
                            todoUIModel = currentContentState.todoUIModel.copy(title = event.textFieldValue),
                            titleError = null
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

            CreateTodoEvent.OnCategoryFieldClicked -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            showCategoryDropdown = true
                        ),
                    )
                }
            }

            CreateTodoEvent.OnCategoryDismissRequest -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            showCategoryDropdown = false
                        ),
                    )
                }
            }

            is CreateTodoEvent.CategorySelected -> {
                setState {
                    UIState.Idle(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(
                                category = TextFieldValue(event.category.key)
                            ),
                            showCategoryDropdown = false
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