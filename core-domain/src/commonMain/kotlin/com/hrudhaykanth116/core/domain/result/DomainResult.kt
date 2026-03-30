package com.hrudhaykanth116.core.domain.result

sealed interface DomainResult<out T> {

    data class Success<T>(val data: T) : DomainResult<T>

    data class Error(val error: DomainError) : DomainResult<Nothing>

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun <R> map(transform: (T) -> R): DomainResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
        }
    }

    fun onSuccess(action: (T) -> Unit): DomainResult<T> {
        if (this is Success) action(data)
        return this
    }

    fun onError(action: (DomainError) -> Unit): DomainResult<T> {
        if (this is Error) action(error)
        return this
    }
}

fun <T> DomainResult<T>.getOrElse(default: T): T = when (this) {
    is DomainResult.Success -> data
    else -> default
}

fun <T> DomainResult<T>.getOrThrow(): T = when (this) {
    is DomainResult.Success -> data
    is DomainResult.Error -> throw error.toException()
}
