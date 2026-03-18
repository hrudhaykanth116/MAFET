package com.hrudhaykanth116.core.network

import android.util.Log
import com.hrudhaykanth116.core.network.models.ApiError
import com.hrudhaykanth116.core.network.models.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class NetworkDataSource constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    protected suspend fun <T> getResult(call: suspend () -> Result<T>): ApiResultWrapper<T> {

        // TODO: add Online check
        if (true) {
            return safeApiCallResponse(dispatcher = dispatcher, apiCall = call)
        } else {
            Log.e(TAG, "getResult: No internet")
            return ApiResultWrapper.Error(apiError = ApiError.NoInternetError)
        }
    }

    // private fun getDataResultError(e: Exception): DataResult.Error {
    //     return when (e) {
    //         is SocketTimeoutException -> DataResult.Error(uiMessage = "Socket time out".toUIText())
    //         is IOException -> DataResult.Error(uiMessage = "Something went wrong".toUIText())
    //         is TimeoutException -> DataResult.Error(uiMessage = "Time out".toUIText())
    //         else -> DataResult.Error(uiMessage = "Something went wrong".toUIText())
    //     }
    // }
    //
    // private fun parseError(errorBody: ResponseBody?, code: Int): ApiResultWrapper.Error {
    //     val moshi = Moshi.Builder().build()
    //     val adapter = moshi.adapter(ApiErrorResponse::class.java)
    //     val parsed = try {
    //         errorBody?.charStream()?.let { adapter.fromJson(it.readText()) }
    //     } catch (e: Exception) {
    //         null
    //     }
    //
    //     val message = parsed?.message?.takeIf { it.isNotBlank() } ?: "Something went wrong"
    //     return ApiResultWrapper.Error(
    //         apiError = ApiError.ResponseError(errorBody, code)
    //     )
    // }

    companion object {
        private const val TAG = "NetworkDataSource"
    }
}