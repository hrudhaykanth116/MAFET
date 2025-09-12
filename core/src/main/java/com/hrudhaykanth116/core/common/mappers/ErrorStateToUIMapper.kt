package com.hrudhaykanth116.core.common.mappers

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.ui.models.toErrorMessage
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.ErrorState
import javax.inject.Inject

fun ErrorState.mapToUIMessage(): UserMessage {
    return mapToUIText().toErrorMessage()
}

fun ErrorState.mapToUIText(): UIText = when (this) {
    ErrorState.NoNetwork -> "No Internet Connection. Please check your network settings."
    ErrorState.TimeOut -> "The request timed out. Please try again later."
    ErrorState.NotFound -> "The requested resource was not found."
    is ErrorState.Api -> "API Error: ${message ?: "Unknown error"}"
    is ErrorState.SomethingWentWrong -> "Something went wrong. Please try again."
    ErrorState.Unauthorized -> "Unauthorized access. Please check your credentials."
    is ErrorState.Unknown -> "Something went wrong"
    ErrorState.Validation -> "Validation failed"
    ErrorState.InvalidUser -> "Invalid user"
}.toUIText()