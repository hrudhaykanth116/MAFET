package com.hrudhaykanth116.core.domain.result

sealed interface DomainError {

    data object NoNetwork : DomainError

    data object Timeout : DomainError

    data class Validation(val message: String) : DomainError

    data class Authentication(val message: String? = null) : DomainError

    data class NotFound(val message: String? = null) : DomainError

    data class Unauthorized(val message: String? = null) : DomainError

    data class ServerError(val message: String? = null) : DomainError

    data class Unknown(val throwable: Throwable? = null, val message: String? = null) : DomainError

    fun toMessage(): String = when (this) {
        is NoNetwork -> "No internet connection. Please check your network."
        is Timeout -> "Request timed out. Please try again."
        is Validation -> message
        is Authentication -> message ?: "Authentication failed. Please login again."
        is NotFound -> message ?: "Requested resource not found."
        is Unauthorized -> message ?: "You don't have permission to access this resource."
        is ServerError -> message ?: "Server error occurred. Please try again later."
        is Unknown -> message ?: throwable?.message ?: "An unexpected error occurred."
    }

    fun toException(): Exception = when (this) {
        is NoNetwork -> NetworkException(toMessage())
        is Timeout -> TimeoutException(toMessage())
        is Validation -> ValidationException(message)
        is Authentication -> AuthenticationException(message ?: toMessage())
        is NotFound -> NotFoundException(message ?: toMessage())
        is Unauthorized -> UnauthorizedException(message ?: toMessage())
        is ServerError -> ServerException(message ?: toMessage())
        is Unknown -> throwable as? Exception ?: Exception(toMessage())
    }
}

class NetworkException(message: String) : Exception(message)
class TimeoutException(message: String) : Exception(message)
class ValidationException(message: String) : Exception(message)
class AuthenticationException(message: String) : Exception(message)
class NotFoundException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)
class ServerException(message: String) : Exception(message)
