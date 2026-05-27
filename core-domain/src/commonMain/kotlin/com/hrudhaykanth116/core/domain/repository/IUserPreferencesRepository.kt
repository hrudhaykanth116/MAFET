package com.hrudhaykanth116.core.domain.repository

import com.hrudhaykanth116.core.domain.models.UserLocation
import com.hrudhaykanth116.core.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {
    suspend fun saveLastLocation(location: UserLocation): DomainResult<Unit>
    fun observeLastLocation(): Flow<DomainResult<UserLocation?>>
    suspend fun getLastLocation(): DomainResult<UserLocation?>
    suspend fun clearLastLocation(): DomainResult<Unit>

    fun observeTheme(): Flow<String?>
    suspend fun setTheme(theme: String)
}
