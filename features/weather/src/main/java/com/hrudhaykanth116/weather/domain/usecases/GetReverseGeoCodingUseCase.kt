package com.hrudhaykanth116.weather.domain.usecases

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.weather.data.models.OWMReverseGeocodingResponseItem
import com.hrudhaykanth116.weather.data.repository.IGeoCodeRepository

class GetReverseGeoCodingUseCase(
    private val geoCodeRepository: IGeoCodeRepository,

    ) {

    suspend operator fun invoke(
        latitude: String,
        longitude: String,
    ): RepoResultWrapper<List<OWMReverseGeocodingResponseItem>> {

        return geoCodeRepository.getReverseGeoCoding(latitude, longitude)

    }

}