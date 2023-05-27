package com.hrudhaykanth116.todo.ui.screens.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.domain.use_cases.GetTodoTasksUseCase
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
    private val getTodoTasksUseCase: GetTodoTasksUseCase,
) : UIStateViewModel<CreateOrUpdateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.Loading(CreateOrUpdateTodoUIState())
) {


    init {
        val noteId: String? = savedStateHandle["id"]

        initData(noteId)

    }

    private fun initData(noteId: String?) {

        if (noteId == null) {
            setState {
                UIState.Loaded(
                    getOrCreateContentState()
                )
            }
        } else {
            viewModelScope.launch {
                val todoModel = getTodoTasksUseCase(noteId)?.getOrNull(1)

                val todoUIModel = todoModel?.toUIModel()

                setState {
                    UIState.Loaded(
                        getOrCreateContentState(todoUIModel)
                    )
                }

            }
        }
    }


    override fun processEvent(event: CreateTodoEvent) {
        val currentContentState = getOrCreateContentState()
        when (event) {

            CreateTodoEvent.Create -> {
                viewModelScope.launch {
                    setState {
                        UIState.Loading(contentState)
                    }
                    val newState = createTodoTaskUseCase(
                        currentContentState.toDomainModel()
                    )
                    setState {
                        newState.toUIState()
                    }
                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                setState {
                    UIState.Loaded(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(description = event.textFieldValue)
                        )
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                setState {
                    UIState.Loaded(
                        currentContentState.copy(
                            todoUIModel = currentContentState.todoUIModel.copy(title = event.textFieldValue)
                        )
                    )
                }
            }
        }
    }

    private fun getOrCreateContentState(
        todoUIModel: TodoUIModel? = null,
    ): CreateOrUpdateTodoUIState {

        val contentStateResult = contentState ?: CreateOrUpdateTodoUIState()

        todoUIModel?.let {
            contentStateResult.copy(
                todoUIModel = todoUIModel
            )
        }

        return contentStateResult
    }


}