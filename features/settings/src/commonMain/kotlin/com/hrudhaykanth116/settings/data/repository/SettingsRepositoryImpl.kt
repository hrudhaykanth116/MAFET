package com.hrudhaykanth116.settings.data.repository

import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.settings.domain.model.AppTheme
import com.hrudhaykanth116.settings.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val userPreferencesRepository: IUserPreferencesRepository
) : ISettingsRepository {

    override fun getTheme(): Flow<AppTheme> {
        return userPreferencesRepository.observeTheme().map { themeName ->
            try {
                if (themeName == null) AppTheme.SYSTEM
                else AppTheme.valueOf(themeName)
            } catch (e: Exception) {
                AppTheme.SYSTEM
            }
        }
    }

    override suspend fun setTheme(theme: AppTheme) {
        userPreferencesRepository.setTheme(theme.name)
    }
}
