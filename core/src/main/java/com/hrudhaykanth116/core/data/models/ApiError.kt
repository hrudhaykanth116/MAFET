package com.hrudhaykanth116.core.data.models

import okhttp3.ResponseBody
import java.io.IOException
import java.net.SocketTimeoutException

sealed interface ApiError {
    data class ResponseError(val error: ResponseBody?, val code: Int) : ApiError
    object NoInternetError : ApiError
    object TimeOutError : ApiError
    data class NullResponseError(val error: ResponseBody?, val code: Int) : ApiError
    data class ExceptionError(val exception: java.lang.Exception) : ApiError
    object SomethingWentWrong : ApiError
    object InvalidUser : ApiError
}