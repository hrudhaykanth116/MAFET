package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository
import com.hrudhaykanth116.weather.data.repository.IWeatherForeCastRepository
import com.hrudhaykanth116.weather.domain.models.WeatherListItemUIState
import javax.inject.Inject

class GetForeCastUseCase @Inject constructor(
    private val geoCodeRepository: IGeoCodeRepository,
    private val weatherForeCastRepository: IWeatherForeCastRepository,
    private val parseForeCastDtoUseCase: ParseForeCastDtoUseCase
) {

    suspend operator fun invoke(
        location: String,
    ): DataResult<List<WeatherListItemUIState>> {

        when (val locationInfoDataResult = geoCodeRepository.getLocationInfo(location)) {
            is DataResult.Error -> {
                return DataResult.Error(UIText.Text("No information found on entered Location"))
            }
            is DataResult.Success -> {

                val locationInfo =
                    locationInfoDataResult.data.firstOrNull() ?: return DataResult.Error( UIText.Text("No information found on entered Location"))

                return when (

                    val foreCastResult = weatherForeCastRepository.getDailyWeatherForeCast(
                        locationInfo.lat?.toString().orEmpty(),
                        locationInfo.lon?.toString().orEmpty(),
                    )
                ) {
                    is DataResult.Error -> {
                        DataResult.Error(foreCastResult.uiMessage)
                    }
                    is DataResult.Success -> {
                        val foreCastList: List<WeatherListItemUIState> = parseForeCastDtoUseCase.invoke(foreCastResult.data)
                        DataResult.Success(foreCastList)
                    }
                }
            }
        }
    }
}