package com.hrudhaykanth116.core.data

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.common.mappers.toRepoResult
import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    @IoDispatcher private val dispatcher: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO,
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