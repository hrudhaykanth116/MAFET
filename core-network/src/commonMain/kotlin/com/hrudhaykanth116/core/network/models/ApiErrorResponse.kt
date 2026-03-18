package com.hrudhaykanth116.core.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
    data class ApiErrorResponse(
        val message: String? = null,
        val error: String? = null,
    )