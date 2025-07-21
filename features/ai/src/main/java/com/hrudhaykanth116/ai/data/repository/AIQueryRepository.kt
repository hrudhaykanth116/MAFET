package com.hrudhaykanth116.ai.data.repository

import com.hrudhaykanth116.ai.data.datasources.remote.QueryRemoteDataSource
import javax.inject.Inject

class AIQueryRepository @Inject constructor(
    private val queryRemoteDataSource: QueryRemoteDataSource,
) {



}