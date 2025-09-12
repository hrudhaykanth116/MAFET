package com.hrudhaykanth116.core.data.remote

import com.hrudhaykanth116.core.data.models.ApiError
import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.ui.components.ApiErrorScreen
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend inline fun <T> safeApiCallResponse(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline apiCall: suspend () -> Response<T>,
): ApiResultWrapper<T> = withContext(dispatcher) {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return@withContext ApiResultWrapper.Success(body)
            val raw = response.errorBody()
            return@withContext ApiResultWrapper.Error(
                ApiError.NullResponseError(
                    raw,
                    response.code()
                )
            )
        } else {
            ApiResultWrapper.Error(ApiError.ResponseError(response.errorBody(), response.code()))
        }
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        return@withContext when (e) {
            is HttpException -> {
                ApiResultWrapper.Error(ApiError.ResponseError(e.response()?.errorBody(), e.code()))
            }

            else -> ApiResultWrapper.Error(apiError = ApiError.ExceptionError(e as Exception))
        }
    }
}