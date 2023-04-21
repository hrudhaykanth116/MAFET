package com.hrudhaykanth116.todo.ui.screens.create

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoUIState
import com.hrudhaykanth116.todo.domain.use_cases.CreateTodoTaskUseCase
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTodoListViewModel @Inject constructor(
    private val createTodoTaskUseCase: CreateTodoTaskUseCase
) : UIStateViewModel<CreateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.LoadedUIState(
        CreateTodoUIState()
    )
) {

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

    fun getOrCreateContentState(): CreateTodoUIState {
        return contentState ?: CreateTodoUIState()
    }


}