package com.hrudhaykanth116.core.domain.usecases

import com.hrudhaykanth116.core.domain.models.UserLocation
import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

class ObserveSavedUserLocationUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    operator fun invoke(): Flow<DomainResult<UserLocation?>> {
        return userPreferencesRepository.observeLastLocation()
    }
}
