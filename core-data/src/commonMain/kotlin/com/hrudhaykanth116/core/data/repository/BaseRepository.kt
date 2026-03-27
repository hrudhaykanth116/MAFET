package com.hrudhaykanth116.core.data.repository

import com.hrudhaykanth116.core.common.di.getIODispatcher
import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.network.models.ApiError
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    private val dispatcher: CoroutineDispatcher = getIODispatcher(),
) {

    suspend fun <T> getResult(
        getData: suspend () -> ApiResultWrapper<T>,
    ): RepoResultWrapper<T> = withContext(dispatcher) {
        getData().toRepoResult()
    }

    suspend fun <T> getLocalResult(
        getData: suspend () -> T,
    ): T = withContext(dispatcher) {
        getData()
    }

}


fun <T> ApiResultWrapper<T>.toRepoResult(): RepoResultWrapper<T> {
    return when (this) {
        is ApiResultWrapper.Success -> RepoResultWrapper.Success(data)

        is ApiResultWrapper.Error -> RepoResultWrapper.Error(
            when (val error = apiError) {
                is ApiError.NoInternetError -> ErrorState.NoNetwork

                is ApiError.TimeOutError -> ErrorState.TimeOut

                is ApiError.ExceptionError -> ErrorState.SomethingWentWrong

                is ApiError.SomethingWentWrong -> ErrorState.SomethingWentWrong

                is ApiError.InvalidUser -> ErrorState.InvalidUser
            }
        )
    }
}
