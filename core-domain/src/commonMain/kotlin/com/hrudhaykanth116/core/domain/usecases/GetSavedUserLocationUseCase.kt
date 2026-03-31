package com.hrudhaykanth116.core.domain.usecases

import com.hrudhaykanth116.core.domain.models.UserLocation
import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.result.DomainResult

class GetSavedUserLocationUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    suspend operator fun invoke(): DomainResult<UserLocation?> {
        return userPreferencesRepository.getLastLocation()
    }
}
