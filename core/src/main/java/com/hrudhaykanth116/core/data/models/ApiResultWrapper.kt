package com.hrudhaykanth116.core.data.models

sealed class ApiResultWrapper<out T> {
    data class Success<out T>(val data: T) : ApiResultWrapper<T>()
    data class Error(val apiError: ApiError) : ApiResultWrapper<Nothing>()
}