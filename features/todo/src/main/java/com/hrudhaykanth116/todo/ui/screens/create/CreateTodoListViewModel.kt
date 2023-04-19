package com.hrudhaykanth116.todo.ui.screens.create

import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoLoadedState
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoLoadingState
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTodoListViewModel @Inject constructor(

) : UDFViewModel<UIState<CreateTodoUIState>, CreateTodoEvent, CreateTodoEffect>(
    CreateTodoLoadedState(
        CreateTodoUIState()
    )
) {

    init {
    }

    override fun processEvent(event: CreateTodoEvent) {
        when (event) {

            CreateTodoEvent.Create -> {
                viewModelScope.launch {
                    delay(2000)
                    val newState = (state as? UIState.LoadedUIState?)?.data ?: CreateTodoUIState()
                    setState {
                        CreateTodoLoadedState(
                            newState.copy(
                                isSubmitted = true,
                            )
                        )
                    }
                }
            }

            is CreateTodoEvent.DescriptionChanged -> {
                val newState = (state as? UIState.LoadedUIState?)?.data ?: CreateTodoUIState()
                setState {
                    CreateTodoLoadedState(
                        newState.copy(
                            description = event.textFieldValue
                        )
                    )
                }
            }

            is CreateTodoEvent.TitleChanged -> {
                val newState = (state as? UIState.LoadedUIState?)?.data ?: CreateTodoUIState()
                setState {
                    CreateTodoLoadedState(
                        newState.copy(
                            title = event.textFieldValue
                        )
                    )
                }
            }
        }
    }


}