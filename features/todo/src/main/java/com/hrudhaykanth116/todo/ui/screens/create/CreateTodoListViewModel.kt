package com.hrudhaykanth116.todo.ui.screens.create

import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEffect
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTodoListViewModel @Inject constructor(

): UDFViewModel<CreateTodoUIState, CreateTodoEvent, CreateTodoEffect>(
    CreateTodoUIState()
) {

    override fun processEvent(event: CreateTodoEvent) {

    }


}