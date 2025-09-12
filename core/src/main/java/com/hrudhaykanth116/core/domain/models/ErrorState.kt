package com.hrudhaykanth116.core.domain.models

sealed class ErrorState {

    object Validation : ErrorState()
    object NoNetwork : ErrorState()
    data class Api(val message: String?, val description: String?) : ErrorState()
    object TimeOut : ErrorState()
    data class Unknown(val throwable: Throwable) : ErrorState()
    object SomethingWentWrong: ErrorState()
    object InvalidUser: ErrorState()
    object NotFound : ErrorState()
    object Unauthorized : ErrorState()
}