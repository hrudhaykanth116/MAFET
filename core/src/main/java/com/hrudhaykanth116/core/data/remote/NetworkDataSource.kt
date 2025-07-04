package com.hrudhaykanth116.core.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.network.OnlineTracker
import com.hrudhaykanth116.core.data.models.toUIText
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

abstract class NetworkDataSource {

    // TODO: P3 Switch dispatcher
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataResult<T> {

        if (OnlineTracker.isOnline) {

            // val result: Result<Response<T>> = kotlin.runCatching {
            //     call()
            // }
            // result.isFailure
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return DataResult.Success(body)
                }

                val apiError: DataResult.Error = parseError(response.errorBody(), response.code())

                // val url = response.raw().networkResponse?.request?.url.toString()

                Logger.e(TAG, "getResult: ${apiError.uiMessage}")
                return apiError
            } catch (e: Exception) {
                Logger.e(TAG, "getResult: ", e)
                return getDataResultError(e)
            }
        } else {
            Log.e(TAG, "getResult: No internet")
            return DataResult.Error(
                uiMessage = UIText.Text("No internet"),
                uiDescription = UIText.Text("Internet is not available."),
            )
        }
    }

    // TODO: Prepare ui message in UI layer
    private fun getDataResultError(e: Exception): DataResult.Error {
        return when (e) {
            is SocketTimeoutException -> {
                DataResult.Error(
                    uiMessage = "Socket time out".toUIText()
                )
            }
             is IOException -> {
                 DataResult.Error(
                     uiMessage = "Something went wrong".toUIText()
                 )
             }
            is TimeoutException -> {
                DataResult.Error(
                    uiMessage = "Time out".toUIText()
                )
            }
            else -> {
                DataResult.Error(
                    uiMessage = "Something went wrong".toUIText()
                )
            }
        }
    }

    fun parseError(errorBody: ResponseBody?, code: Int): DataResult.Error{

        // TODO: Use Moshi
        val gson = Gson()
        val type = object : TypeToken<ApiError>() {}.type
        val errorResponse: ApiError? = gson.fromJson(errorBody?.charStream(), type)

        return DataResult.Error(
            uiMessage = errorResponse?.message?.toUIText() ?: "Something went wrong".toUIText(),
            uiDescription = errorResponse?.description?.let { UIText.Text(it) },
            code = errorResponse?.code
        )
    }

    data class ApiError(
        var message: String? = null,
        var description: String? = null,
        var code: String? = null
    )

    companion object{
        private const val TAG = "NetworkDataSource"
    }


}