package com.hrudhaykanth116.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.hrudhaykanth116.core.common.di.getIODispatcher
import com.hrudhaykanth116.core.data.local.datastore.PreferencesKeys
import com.hrudhaykanth116.core.domain.models.UserLocation
import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher = getIODispatcher()
) : IUserPreferencesRepository {

    override suspend fun saveLastLocation(location: UserLocation): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.LAST_LOCATION_LATITUDE] = location.latitude
                    preferences[PreferencesKeys.LAST_LOCATION_LONGITUDE] = location.longitude
                    location.address?.let {
                        preferences[PreferencesKeys.LAST_LOCATION_ADDRESS] = it
                    }
                    preferences[PreferencesKeys.LAST_LOCATION_TIMESTAMP] = location.timestamp
                }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(throwable = e))
            }
        }

    override fun observeLastLocation(): Flow<DomainResult<UserLocation?>> {
        return dataStore.data
            .catch { exception ->
                emit(emptyPreferences())
            }
            .map { preferences ->
                try {
                    val latitude = preferences[PreferencesKeys.LAST_LOCATION_LATITUDE]
                    val longitude = preferences[PreferencesKeys.LAST_LOCATION_LONGITUDE]
                    val address = preferences[PreferencesKeys.LAST_LOCATION_ADDRESS]
                    val timestamp = preferences[PreferencesKeys.LAST_LOCATION_TIMESTAMP]

                    if (latitude != null && longitude != null && timestamp != null) {
                        DomainResult.Success(
                            UserLocation(
                                latitude = latitude,
                                longitude = longitude,
                                address = address,
                                timestamp = timestamp
                            )
                        )
                    } else {
                        DomainResult.Success<UserLocation?>(null)
                    }
                } catch (exception: Exception) {
                    DomainResult.Error(DomainError.Unknown(throwable = exception))
                }
            }
            .flowOn(dispatcher)
    }

    override suspend fun getLastLocation(): DomainResult<UserLocation?> =
        withContext(dispatcher) {
            try {
                val preferences = dataStore.data.catch { exception ->
                    emit(emptyPreferences())
                }.map { prefs ->
                    val latitude = prefs[PreferencesKeys.LAST_LOCATION_LATITUDE]
                    val longitude = prefs[PreferencesKeys.LAST_LOCATION_LONGITUDE]
                    val address = prefs[PreferencesKeys.LAST_LOCATION_ADDRESS]
                    val timestamp = prefs[PreferencesKeys.LAST_LOCATION_TIMESTAMP]

                    if (latitude != null && longitude != null && timestamp != null) {
                        UserLocation(
                            latitude = latitude,
                            longitude = longitude,
                            address = address,
                            timestamp = timestamp
                        )
                    } else {
                        null
                    }
                }

                var location: UserLocation? = null
                preferences.collect { loc ->
                    location = loc
                }

                DomainResult.Success(location)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(throwable = e))
            }
        }

    override suspend fun clearLastLocation(): DomainResult<Unit> =
        withContext(dispatcher) {
            try {
                dataStore.edit { preferences ->
                    preferences.remove(PreferencesKeys.LAST_LOCATION_LATITUDE)
                    preferences.remove(PreferencesKeys.LAST_LOCATION_LONGITUDE)
                    preferences.remove(PreferencesKeys.LAST_LOCATION_ADDRESS)
                    preferences.remove(PreferencesKeys.LAST_LOCATION_TIMESTAMP)
                }
                DomainResult.Success(Unit)
            } catch (e: Exception) {
                DomainResult.Error(DomainError.Unknown(throwable = e))
            }
        }

    override fun observeTheme(): Flow<String?> {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { preferences -> preferences[PreferencesKeys.APP_THEME] }
    }

    override suspend fun setTheme(theme: String) {
        withContext(dispatcher) {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.APP_THEME] = theme
            }
        }
    }
}
