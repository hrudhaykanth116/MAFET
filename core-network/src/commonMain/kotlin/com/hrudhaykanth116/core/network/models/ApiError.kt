package com.hrudhaykanth116.core.network.models

import okhttp3.ResponseBody
import java.io.IOException
import java.net.SocketTimeoutException

sealed interface ApiError {
    object NoInternetError : ApiError
    object TimeOutError : ApiError
    data class ExceptionError(val exception: java.lang.Exception) : ApiError
    object SomethingWentWrong : ApiError
    object InvalidUser : ApiError
}