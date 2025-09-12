package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import javax.inject.Inject

class GetForeCastFromLatLongUseCase @Inject constructor(
    private val weatherForeCastRepository: IWeatherForeCastRepository,
    private val parseDailyForeCastDtoUseCase: ParseDailyForeCastDtoUseCase,
    private val parseCurrentWeatherUseCase: ParseCurrentWeatherUseCase,
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): RepoResultWrapper<Pair<TodayWeatherUIState, List<DailyWeatherUIState>>> {

        return when (
            val foreCastResult: RepoResultWrapper<WeatherForeCastResponse> =
                weatherForeCastRepository.getDailyWeatherForeCast(
                    latitude.toString(),
                    longitude.toString()
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