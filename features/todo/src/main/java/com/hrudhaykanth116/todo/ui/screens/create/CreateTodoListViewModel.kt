package com.hrudhaykanth116.todo.ui.screens.create

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTodoListViewModel @Inject constructor(

) : UIStateViewModel<CreateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    UIState.LoadedUIState(
        CreateTodoUIState()
    )
) {

    init {
    }

    override fun processEvent(event: CreateTodoEvent) {
        when (event) {

            CreateTodoEvent.Create -> {
                viewModelScope.launch {
                    setState {
                        UIState.LoadingUIState(contentState)
                    }
                    delay(2000)
                    val newState = contentState ?: CreateTodoUIState()
                    setState {
                        UIState.LoadedUIState(
                            contentState.copy(
                                isSubmitted = true
                            )
                        )
                    }
                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                val newState =
                    (state as? UIState.LoadedUIState?)?.contentState ?: CreateTodoUIState()
                setState {
                    UIState.LoadedUIState(
                        contentState.copy(
                            description = event.textFieldValue
                        )
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                val newState =
                    (state as? UIState.LoadedUIState?)?.contentState ?: CreateTodoUIState()
                setState {
                    UIState.LoadedUIState(
                        contentState.copy(
                            title = event.textFieldValue
                        )
                    )
                }
            }
        }
    }


}