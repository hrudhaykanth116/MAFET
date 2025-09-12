package com.hrudhaykanth116.core.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
    data class ApiErrorResponse(
        val message: String? = null,
        val error: String? = null,
    )