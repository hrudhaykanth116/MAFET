package com.hrudhaykanth116.core.domain.usecases

import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.result.DomainResult

class ClearSavedUserLocationUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {
    suspend operator fun invoke(): DomainResult<Unit> {
        return userPreferencesRepository.clearLastLocation()
    }
}
