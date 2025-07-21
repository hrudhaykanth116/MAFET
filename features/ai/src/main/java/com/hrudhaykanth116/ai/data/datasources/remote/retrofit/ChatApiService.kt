package com.hrudhaykanth116.ai.data.datasources.remote.retrofit

import retrofit2.Response

interface ChatApiService {

    suspend fun query(): Response<String>

}