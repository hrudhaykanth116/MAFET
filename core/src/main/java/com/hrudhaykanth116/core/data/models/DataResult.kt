package com.hrudhaykanth116.core.data.models

import com.hrudhaykanth116.core.domain.models.ErrorState

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(
        // Use this to show as error message on UI.
        val uiMessage: UIText? = null,
        // Use this to show as error description on UI.
        val uiDescription: UIText? = null,
        // Use this to log error or for complete error description.
        val fullDescription: String? = null,
        val domainMessage: ErrorState? = null,
        // Use this to perform any error type checks.
        val code: String? = ErrorConstants.UNKNOWN_ERROR_CODE,
    ) : DataResult<Nothing>() // Unit does not working here
}