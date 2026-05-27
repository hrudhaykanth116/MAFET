package com.hrudhaykanth116.settings.domain.use_cases

import com.hrudhaykanth116.settings.domain.model.AppTheme
import com.hrudhaykanth116.settings.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(
    private val repository: ISettingsRepository
) {
    operator fun invoke(): Flow<AppTheme> = repository.getTheme()
}
