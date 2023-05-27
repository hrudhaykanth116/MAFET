package com.hrudhaykanth116.todo.ui.mappers

import com.hrudhaykanth116.core.domain.models.DomainState
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.todo.domain.model.create.CreateOrUpdateTodoDomainModel
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState

fun DomainState<CreateOrUpdateTodoDomainModel>.toUIState(): UIState<CreateOrUpdateTodoUIState> {

    when (this) {
        is DomainState.ErrorDomainState -> {

            // TODO: Handle this
            if (errorState == ErrorState.Unknown) {

            }

            contentState

            return UIState.Error(text = "Something went wrong", contentState?.toUIModel())
        }
        is DomainState.LoadedDomainState -> {
            return UIState.Loaded(contentState.toUIModel())
        }
        is DomainState.LoadingDomainState -> {
            return UIState.Loading(contentState?.toUIModel())
        }
    }


}