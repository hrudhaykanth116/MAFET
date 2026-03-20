package com.hrudhaykanth116.core.network

import android.util.Log
import com.hrudhaykanth116.core.network.models.ApiError
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> safeApiCallResponse(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline apiCall: suspend () -> Result<T>,
): ApiResultWrapper<T> = withContext(dispatcher) {
    try {
        val response = apiCall()
        if (response.isSuccess) {
            val body = response.getOrThrow()
            if (body != null) return@withContext ApiResultWrapper.Success(body)
            Log.e("safeApiCallResponse", "Response body is null")
            // val raw = response.errorBody()
            // return@withContext ApiResultWrapper.Error(
            //     ApiError.NullResponseError(
            //         raw,
            //         response.code()
            //     )
            // )
        } else {
            val exception = response.exceptionOrNull()
            Log.e("safeApiCallResponse", "API call failed", exception)
            return@withContext ApiResultWrapper.Error(apiError = ApiError.ExceptionError(exception as? Exception ?: Exception("Unknown error")))
            // ApiResultWrapper.Error(ApiError.ResponseError(response.errorBody(), response.code()))
        }
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        Log.e("safeApiCallResponse", "Exception during API call", e)
        return@withContext when (e) {
            // is HttpException -> {
            //     ApiResultWrapper.Error(ApiError.ResponseError(e.response()?.errorBody(), e.code()))
            // }

            else -> ApiResultWrapper.Error(apiError = ApiError.ExceptionError(e as Exception))
        }
    }
    return@withContext ApiResultWrapper.Error(ApiError.SomethingWentWrong)
}