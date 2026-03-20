package com.hrudhaykanth116.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val message: String? = null,
    val error: String? = null,
)