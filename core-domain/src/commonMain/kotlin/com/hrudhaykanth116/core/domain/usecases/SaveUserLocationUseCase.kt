package com.hrudhaykanth116.core.domain.usecases

import com.hrudhaykanth116.core.domain.models.UserLocation
import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.result.DomainResult

class SaveUserLocationUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    suspend operator fun invoke(location: UserLocation): DomainResult<Unit> {
        return userPreferencesRepository.saveLastLocation(location)
    }
}
