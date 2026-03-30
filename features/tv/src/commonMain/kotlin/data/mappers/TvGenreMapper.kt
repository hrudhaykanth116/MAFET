package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.Genre
import com.hrudhaykanth116.tv.domain.models.TvGenre

fun Genre.toDomain(): TvGenre {
    return TvGenre(
        id = id ?: 0,
        name = name.orEmpty()
    )
}

fun List<Genre?>.toDomainGenres(): List<TvGenre> {
    return filterNotNull().map { it.toDomain() }
}
