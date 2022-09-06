package com.hrudhaykanth116.mafet.common.data.data_models

sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val value: T) : DataResult<T>()
    data class Error(
        // Use this to show as error message on UI.
        val message: String? = null,
        // Use this to log error or for complete error description.
        val description: String? = null,
        // Use this to perform any error type checks.
        val code: String? = ErrorConstants.UNKNOWN_ERROR_CODE,
    ) : DataResult<Nothing>()
}