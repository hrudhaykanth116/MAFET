package com.hrudhaykanth116.mafet.common.data.models

object ErrorConstants {

    const val WRONG_PASSWORD: String = "E_WRONG_PASSWORD"
    const val USER_NOT_FOUND: String = "E_USER_NOT_FOUND"
    const val E_PASSWORD_RESET_REQUIRED: String = "E_PASSWORD_RESET_REQUIRED"
    const val BAD_REQUEST: String = "E_BADREQUEST"
    const val UNAUTHORIZED: String = "UNAUTHORIZED"
    const val UNKNOWN_ERROR_CODE: String = "UNKNOWN_ERROR_CODE"
    const val ACCESS_TOKEN_EXPIRED: String = "E_TOKEN_EXPIRED"
    const val REFRESH_TOKEN_EXPIRED: String = "E_UNAUTHORIZED"
    const val EENVELOPE: String = "EENVELOPE"
    const val E_USER_DEACTIVATED = "E_USER_DEACTIVATED"
    const val E_USER_INACTIVE = "E_USER_INACTIVE"
    const val E_PENDING_TOS_AGREEMENT = "E_PENDING_TOS_AGREEMENT"
    const val E_WRONG_CREDS = "E_INVALID_CREDENTIALS"
    const val E_TOS_AGREEMENT_REQUIRED = "E_TOS_AGREEMENT_REQUIRED"
    const val E_USER_NOT_LOGGED_IN = "E_USER_NOT_LOGGED_IN"

    // Api related errors
    const val E_API_DATA_NOT_FOUND = "ERROR_API_DATA_NOT_FOUND"

    // Chat client related errors
    const val E_CHAT_CLIENT = "ERROR CREATING CHAT CLIENT"

}