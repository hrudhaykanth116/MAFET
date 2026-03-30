package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.domain.repository.IWeatherForeCastRepository

class GetForeCastUseCaseFromLatLongUseCase(
    private val geoCodeRepository: IGeoCodeRepository,
    private val weatherForeCastRepository: IWeatherForeCastRepository,
    private val parseDailyForeCastDtoUseCase: ParseDailyForeCastDtoUseCase,
    private val parseCurrentWeatherUseCase: ParseCurrentWeatherUseCase,
) {

    suspend operator fun invoke(
        location: String,
    ): DomainResult<Pair<TodayWeatherUIState, List<DailyWeatherUIState>>> {

        return when (val locationInfoDataResult = geoCodeRepository.getLocationInfo(location)) {
            is DomainResult.Error -> {
                locationInfoDataResult
            }

            is DomainResult.Success -> {
                val locationInfo = locationInfoDataResult.data.firstOrNull()
                    ?: return DomainResult.Error(DomainError.NotFound("Location not found"))

                when (
                    val foreCastResult = weatherForeCastRepository.getDailyWeatherForeCast(
                        locationInfo.lat?.toString().orEmpty(),
                        locationInfo.lon?.toString().orEmpty(),
                    )
                ) {
                    is DomainResult.Error -> {
                        foreCastResult
                    }

                    is DomainResult.Success -> {
                        val foreCastList: List<DailyWeatherUIState> =
                            parseDailyForeCastDtoUseCase.invoke(foreCastResult.data)
                        val currentWeatherUIState =
                            parseCurrentWeatherUseCase(foreCastResult.data)
                        DomainResult.Success(Pair(currentWeatherUIState, foreCastList))
                    }
                }
            }
        }
    }
}
