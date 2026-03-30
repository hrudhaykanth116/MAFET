package com.hrudhaykanth116.core.data.mappers

import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.network.models.ApiError
import com.hrudhaykanth116.core.network.models.ApiResultWrapper

fun <T> ApiResultWrapper<T>.toDomainResult(): DomainResult<T> {
    return when (this) {
        is ApiResultWrapper.Success -> DomainResult.Success(data)
        is ApiResultWrapper.Error -> DomainResult.Error(apiError.toDomainError())
    }
}

fun <T> RepoResultWrapper<T>.toDomainResult(): DomainResult<T> {
    return when (this) {
        is RepoResultWrapper.Success -> DomainResult.Success(data)
        is RepoResultWrapper.Error -> DomainResult.Error(errorState.toDomainError())
    }
}

fun ApiError.toDomainError(): DomainError {
    return when (this) {
        is ApiError.NoInternetError -> DomainError.NoNetwork
        is ApiError.TimeOutError -> DomainError.Timeout
        is ApiError.InvalidUser -> DomainError.Authentication("Invalid user credentials")
        is ApiError.SomethingWentWrong -> DomainError.Unknown(message = "Something went wrong")
        is ApiError.ExceptionError -> DomainError.Unknown(
            throwable = exception,
            message = exception.message
        )
    }
}

fun ErrorState.toDomainError(): DomainError {
    return when (this) {
        is ErrorState.NoNetwork -> DomainError.NoNetwork
        is ErrorState.TimeOut -> DomainError.Timeout
        is ErrorState.Validation -> DomainError.Validation("Validation error")
        is ErrorState.InvalidUser -> DomainError.Authentication("Invalid user")
        is ErrorState.Unauthorized -> DomainError.Unauthorized()
        is ErrorState.NotFound -> DomainError.NotFound()
        is ErrorState.SomethingWentWrong -> DomainError.Unknown(message = "Something went wrong")
        is ErrorState.Api -> DomainError.ServerError(message ?: description)
        is ErrorState.Unknown -> DomainError.Unknown(throwable = throwable)
    }
}
