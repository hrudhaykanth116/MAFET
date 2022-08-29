package com.hrudhaykanth116.mafet.common.remote

data class NetworkResource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val error: Error? = null
) {

    val isSuccessful = status == Status.SUCCESS
    val isError = status == Status.ERROR
    val isLoading = status == Status.LOADING

    companion object {
        fun <T> success(data: T): NetworkResource<T> {
            return NetworkResource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, data: T? = null, error: Error?): NetworkResource<T> {
            return NetworkResource(Status.ERROR, data, message, error)
        }
        fun <T> loading(data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.LOADING, data, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}