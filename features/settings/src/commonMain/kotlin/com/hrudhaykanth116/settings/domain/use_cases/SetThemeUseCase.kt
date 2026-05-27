package com.hrudhaykanth116.settings.domain.use_cases

import com.hrudhaykanth116.settings.domain.model.AppTheme
import com.hrudhaykanth116.settings.domain.repository.ISettingsRepository

class SetThemeUseCase(
    private val repository: ISettingsRepository
) {
    suspend operator fun invoke(theme: AppTheme) = repository.setTheme(theme)
}
