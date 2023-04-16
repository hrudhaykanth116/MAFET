package com.hrudhaykanth116.chatgpt.data.repository

import com.hrudhaykanth116.chatgpt.data.datasources.remote.QueryRemoteDataSource
import javax.inject.Inject

class AIQueryRepository @Inject constructor(
    private val queryRemoteDataSource: QueryRemoteDataSource,
) {



}