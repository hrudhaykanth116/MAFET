package com.hrudhaykanth116.core.data.repository

import com.hrudhaykanth116.core.common.di.getIODispatcher
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.network.models.ApiError
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    private val dispatcher: CoroutineDispatcher = getIODispatcher(),
) {

    suspend fun <T> fetchResult(
        getData: suspend () -> ApiResultWrapper<T>,
    ): DomainResult<T> = withContext(dispatcher) {
        getData().toDomainResult()
    }

    suspend fun <T> getLocalResult(
        getData: suspend () -> T,
    ): T = withContext(dispatcher) {
        getData()
    }

}

fun <T> ApiResultWrapper<T>.toDomainResult(): DomainResult<T> {
    return when (this) {
        is ApiResultWrapper.Success -> DomainResult.Success(data)
        is ApiResultWrapper.Error -> DomainResult.Error(apiError.toDomainError())
    }
}

fun ApiError.toDomainError(): DomainError {
    return when (this) {
        is ApiError.NoInternetError -> DomainError.NoNetwork
        is ApiError.TimeOutError -> DomainError.Timeout
        is ApiError.ExceptionError -> DomainError.Unknown(throwable = exception)
        is ApiError.SomethingWentWrong -> DomainError.Unknown(message = "Something went wrong")
        is ApiError.InvalidUser -> DomainError.Authentication("Invalid user")
    }
}
