package com.hrudhaykanth116.composeapp.data

import kotlinx.coroutines.flow.Flow

expect class RemoteConfigDataSource(fetchIntervalSeconds: Long) {
    suspend fun fetchAndActivate(): Boolean
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun configUpdates(): Flow<Unit>
}
