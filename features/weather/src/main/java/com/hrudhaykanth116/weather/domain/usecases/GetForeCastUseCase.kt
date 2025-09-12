package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import javax.inject.Inject

class GetForeCastUseCase @Inject constructor(
    private val geoCodeRepository: IGeoCodeRepository,
    private val weatherForeCastRepository: IWeatherForeCastRepository,
    private val parseDailyForeCastDtoUseCase: ParseDailyForeCastDtoUseCase,
    private val parseCurrentWeatherUseCase: ParseCurrentWeatherUseCase,
) {

    suspend operator fun invoke(
        location: String,
    ): RepoResultWrapper<Pair<TodayWeatherUIState, List<DailyWeatherUIState>>> {

        when (val locationInfoDataResult = geoCodeRepository.getLocationInfo(location)) {
            is RepoResultWrapper.Error -> {
                return locationInfoDataResult
            }

            is RepoResultWrapper.Success -> {

                val locationInfo =
                    locationInfoDataResult.data.firstOrNull()
                        ?: return RepoResultWrapper.Error(ErrorState.NotFound)

                return when (
                    val foreCastResult: RepoResultWrapper<WeatherForeCastResponse> =
                        weatherForeCastRepository.getDailyWeatherForeCast(
                            locationInfo.lat?.toString().orEmpty(),
                            locationInfo.lon?.toString().orEmpty(),
                        )
                ) {
                    is RepoResultWrapper.Error -> {
                        foreCastResult
                    }

                    is RepoResultWrapper.Success -> {
                        val foreCastList: List<DailyWeatherUIState> =
                            parseDailyForeCastDtoUseCase.invoke(foreCastResult.data)
                        val currentWeatherUIState =
                            parseCurrentWeatherUseCase(foreCastResult.data)
                        RepoResultWrapper.Success(Pair(currentWeatherUIState, foreCastList))
                    }
                }
            }
        }
    }
}