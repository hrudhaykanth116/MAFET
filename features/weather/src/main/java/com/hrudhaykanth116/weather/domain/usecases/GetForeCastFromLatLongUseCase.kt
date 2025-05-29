package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.weather.data.models.WeatherForeCastResponse
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
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
    ): DataResult<Pair<TodayWeatherUIState, List<DailyWeatherUIState>>> {

        return when (
            val foreCastResult: DataResult<WeatherForeCastResponse> =
                weatherForeCastRepository.getDailyWeatherForeCast(
                    latitude.toString(),
                    longitude.toString()
                )
        ) {
            is DataResult.Error -> {
                DataResult.Error(foreCastResult.uiMessage)
            }

            is DataResult.Success -> {
                val foreCastList: List<DailyWeatherUIState> =
                    parseDailyForeCastDtoUseCase.invoke(foreCastResult.data)
                val currentWeatherUIState =
                    parseCurrentWeatherUseCase(foreCastResult.data)
                DataResult.Success(Pair(currentWeatherUIState, foreCastList))
            }
        }

    }
}