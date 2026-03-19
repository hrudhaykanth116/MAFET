package com.hrudhaykanth116.core.ui.mappers

import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toUIText


fun ErrorState.mapToUIMessage(): UserMessage.Error {
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