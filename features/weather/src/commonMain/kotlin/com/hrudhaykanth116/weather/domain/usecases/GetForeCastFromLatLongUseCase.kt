package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.weather.domain.models.DailyWeatherUIState
import com.hrudhaykanth116.weather.domain.models.TodayWeatherUIState
import com.hrudhaykanth116.weather.domain.repository.IWeatherForeCastRepository

class GetForeCastFromLatLongUseCase(
    private val weatherForeCastRepository: IWeatherForeCastRepository,
    private val parseDailyForeCastDtoUseCase: ParseDailyForeCastDtoUseCase,
    private val parseCurrentWeatherUseCase: ParseCurrentWeatherUseCase,
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): DomainResult<Pair<TodayWeatherUIState, List<DailyWeatherUIState>>> {

        return when (
            val foreCastResult = weatherForeCastRepository.getDailyWeatherForeCast(
                latitude.toString(),
                longitude.toString()
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
