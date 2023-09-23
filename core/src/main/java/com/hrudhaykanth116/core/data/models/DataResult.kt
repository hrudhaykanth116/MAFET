package com.hrudhaykanth116.core.data.models

import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.domain.models.ErrorState

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(
        // Use this to show as error message on UI.
        val uiMessage: UIText = "Something went wrong".toUIText(),
        // Use this to show as error description on UI.
        val uiDescription: UIText? = null,
        // Use this to log error or for complete error description.
        val fullDescription: String? = null,
        val domainMessage: ErrorState? = null,
        // Use this to perform any error type checks.
        val code: String? = ErrorConstants.UNKNOWN_ERROR_CODE,
    ) : DataResult<Nothing>() // Unit does not working here

    // fun <R> process(
    //     onSuccess: ((T) -> R)? = null,
    //     onError: ((Error) -> R)? = null,
    // ): R? {
    //     return process(onSuccess, onError)
    // }

    fun <R> process(
        onSuccess: ((T) -> R),
        onError: ((Error) -> R), // TODO: Make this nullable and optional
    ): R {
        return when (this) {
            is Error -> onError.invoke(this)
            is Success -> onSuccess.invoke(this.data)
        }
    }

}