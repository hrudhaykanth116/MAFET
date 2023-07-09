package com.hrudhaykanth116.todo.ui.mappers

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.toUIText
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
                return UIState.Error(uiText = "Something went wrong".toUIText(), contentState?.toUIModel())
            }

            return UIState.Error(
                uiText = null,
                contentState?.toUIModel()
            )


        }
        is DomainState.LoadedDomainState -> {
            val userMessage = if(contentState.isSubmitted) UserMessage.Success("Task created/submitted successfully".toUIText()) else null

            return UIState.Loaded(contentState.toUIModel(), userMessage)
        }
        is DomainState.LoadingDomainState -> {
            return UIState.Loading(contentState?.toUIModel())
        }
    }


}