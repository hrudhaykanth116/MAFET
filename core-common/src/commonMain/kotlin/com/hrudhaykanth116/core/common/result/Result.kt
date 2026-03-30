package com.hrudhaykanth116.core.common.result

/**
 * A generic result wrapper for operations that can succeed or fail.
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception ?: Exception(message)
        is Loading -> throw IllegalStateException("Cannot get value from Loading state")
    }
}

/**
 * Returns the data if Success, otherwise returns the provided default value.
 */
fun <T> Result<T>.getOrElse(default: T): T = when (this) {
    is Result.Success -> data
    else -> default
}
