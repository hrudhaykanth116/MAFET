package com.hrudhaykanth116.tv.ui.mappers

import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.ui.screens.details.NetworkUIState
import com.hrudhaykanth116.tv.ui.screens.details.TvDetailsScreenUIState

fun TvShowDetail.toUIState(): TvDetailsScreenUIState {
    val formattedRating = ((voteAverage * 10).toInt() / 10.0).toString()
    val dateRange = "${firstAirDate.orEmpty()} - ${lastAirDate.orEmpty()}"

    return TvDetailsScreenUIState(
        id = id,
        title = name.ifEmpty { originalName },
        overview = overview,
        backdropImage = backdropPath?.let { (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder() },
        dateRange = dateRange,
        rating = "$formattedRating / 10",
        genres = genres.map { it.name },
        networks = networks.map { network ->
            NetworkUIState(
                name = network.name,
                logo = network.logoPath?.takeIf { it.isNotEmpty() }
                    ?.let { (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder() },
            )
        },
    )
}
