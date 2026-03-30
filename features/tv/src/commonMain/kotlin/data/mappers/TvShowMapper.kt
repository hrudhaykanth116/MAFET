package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.domain.models.TvShow

fun TvShowData.toDomain(): TvShow {
    return TvShow(
        id = id,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        firstAirDate = firstAirDate,
        popularity = popularity ?: 0.0,
        genreIds = genreIds.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalName = originalName.orEmpty(),
        originCountry = originCountry.orEmpty()
    )
}

fun List<TvShowData>.toDomainTvShows(): List<TvShow> {
    return map { it.toDomain() }
}
