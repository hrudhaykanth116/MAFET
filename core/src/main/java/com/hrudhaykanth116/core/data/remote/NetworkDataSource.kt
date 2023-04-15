package com.hrudhaykanth116.core.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.core.utils.network.OnlineTracker
import okhttp3.ResponseBody
import retrofit2.Response

abstract class NetworkDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataResult<T> {

        if (OnlineTracker.isOnline) {
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
                return DataResult.Error(
                    uiMessage = e.message?.let { UIText.Text(it) }
                )
            }
        } else {
            Log.e(TAG, "getResult: No internet")
            return DataResult.Error(
                uiMessage = UIText.Text("No internet"),
                uiDescription = UIText.Text("Internet is not available."),
            )
        }
    }

    fun parseError(errorBody: ResponseBody?, code: Int): DataResult.Error{

        // TODO: Use Moshi
        val gson = Gson()
        val type = object : TypeToken<ApiError>() {}.type
        val errorResponse: ApiError? = gson.fromJson(errorBody?.charStream(), type)

        return DataResult.Error(
            uiMessage = errorResponse?.message?.let { UIText.Text(it) },
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