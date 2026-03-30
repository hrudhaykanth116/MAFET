package com.hrudhaykanth116.core.network.models

sealed interface ApiError {
    object NoInternetError : ApiError
    object TimeOutError : ApiError
    data class ExceptionError(val exception: Throwable) : ApiError
    object SomethingWentWrong : ApiError
    object InvalidUser : ApiError
}