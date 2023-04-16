package com.hrudhaykanth116.chatgpt.data.datasources.remote.retrofit

import retrofit2.Response

interface ChatGptQueryApiService {

    suspend fun query(): Response<String>

}