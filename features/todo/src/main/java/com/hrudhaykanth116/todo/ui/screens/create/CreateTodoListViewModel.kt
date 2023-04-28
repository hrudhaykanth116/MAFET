package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.data.dummydata.DummyTodoList
import com.hrudhaykanth116.todo.domain.model.create.TodoUIState
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTodoListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createTodoTaskUseCase: CreateTodoTaskUseCase
) : UIStateViewModel<TodoUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.LoadingUIState()
) {


    init {
        val noteId: String? = savedStateHandle["id"]

        initData(noteId)

    }

    private fun initData(userId: String?) {

        if (userId == null) {
            setState {
                UIState.LoadedUIState(
                    getOrCreateContentState()
                )
            }
        } else {
            viewModelScope.launch {
                DummyTodoList.todoList.find {
                    it.data.id.toString() == userId
                }?.data?.let { todoUIModel ->
                    setState {
                        UIState.LoadedUIState(
                            getOrCreateContentState().copy(
                                title = TextFieldValue(text = todoUIModel.title),
                                description = TextFieldValue(text = todoUIModel.description.orEmpty())
                            )
                        )
                    }
                }
            }
        }
    }


    override fun processEvent(event: CreateTodoEvent) {
        when (event) {

            CreateTodoEvent.Create -> {
                viewModelScope.launch {
                    setState {
                        UIState.LoadingUIState(contentState)
                    }
                    val newState = createTodoTaskUseCase(
                        getOrCreateContentState()
                    )
                    setState {
                        newState
                    }
                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                setState {
                    UIState.LoadedUIState(
                        getOrCreateContentState().copy(
                            description = event.textFieldValue
                        )
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                setState {
                    UIState.LoadedUIState(
                        getOrCreateContentState().copy(
                            title = event.textFieldValue
                        )
                    )
                }
            }
        }
    }

    fun getOrCreateContentState(): TodoUIState {
        return contentState ?: TodoUIState()
    }


}