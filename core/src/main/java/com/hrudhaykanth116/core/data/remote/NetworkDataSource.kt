package com.hrudhaykanth116.core.data.remote

import android.util.Log
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.network.OnlineTracker
import com.hrudhaykanth116.core.data.models.ErrorConstants
import com.hrudhaykanth116.core.data.models.toUIText
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

open class NetworkDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataResult<T> {

        if (OnlineTracker.isOnline) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return DataResult.Success(body)
                }

                val apiError: DataResult.Error = parseError(response.errorBody(), response.code())
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
                code = ErrorConstants.NO_INTERNET
            )
        }
    }

    private fun getDataResultError(e: Exception): DataResult.Error {
        return when (e) {
            is SocketTimeoutException -> DataResult.Error(uiMessage = "Socket time out".toUIText())
            is IOException -> DataResult.Error(uiMessage = "Something went wrong".toUIText())
            is TimeoutException -> DataResult.Error(uiMessage = "Time out".toUIText())
            else -> DataResult.Error(uiMessage = "Something went wrong".toUIText())
        }
    }

    private fun parseError(errorBody: ResponseBody?, code: Int): DataResult.Error {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(ApiErrorResponse::class.java)
        val parsed = try {
            errorBody?.charStream()?.let { adapter.fromJson(it.readText()) }
        } catch (e: Exception) { null }

        val message = parsed?.message?.takeIf { it.isNotBlank() } ?: "Something went wrong"
        return DataResult.Error(
            uiMessage = message.toUIText(),
            uiDescription = message.toUIText(),
            code = ErrorConstants.UNKNOWN_ERROR_CODE // TODO: parse code correctly
        )
    }

    sealed interface ApiError {
        data class ResponseCodeError(val error: String) : ApiError
        data class NetworkError(val io: IOException) : ApiError
        object NoInternetError : ApiError
        data class TimeOutError(val error: SocketTimeoutException) : ApiError
        object NullResponseError : ApiError
        data class ExceptionError(val exception: java.lang.Exception) : ApiError
        data class CustomError(val message: UIText) : ApiError
        object SomethingWentWrong : ApiError
    }


    @JsonClass(generateAdapter = true)
    data class ApiErrorResponse(
        val message: String? = null,
        val error: String? = null,
    )

    companion object {
        private const val TAG = "NetworkDataSource"
    }
}