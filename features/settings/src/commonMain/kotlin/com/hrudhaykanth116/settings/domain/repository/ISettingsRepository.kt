package com.hrudhaykanth116.settings.domain.repository

import com.hrudhaykanth116.settings.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    fun getTheme(): Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)
}
