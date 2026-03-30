package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.domain.models.Creator
import com.hrudhaykanth116.tv.domain.models.Episode
import com.hrudhaykanth116.tv.domain.models.Network
import com.hrudhaykanth116.tv.domain.models.ProductionCompany
import com.hrudhaykanth116.tv.domain.models.Season
import com.hrudhaykanth116.tv.domain.models.TvShowDetail

fun TvShowDetails.toDomain(): TvShowDetail {
    return TvShowDetail(
        id = id,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        popularity = popularity ?: 0.0,
        originalLanguage = originalLanguage.orEmpty(),
        originalName = originalName.orEmpty(),
        originCountry = originCountry?.filterNotNull().orEmpty(),
        genres = genres.orEmpty().filterNotNull().map { it.toDomain() },
        createdBy = createdBy.orEmpty().filterNotNull().map { it.toDomain() },
        networks = networks.orEmpty().map { it.toDomain() },
        productionCompanies = productionCompanies.orEmpty().filterNotNull().map { it.toDomain() },
        seasons = seasons.orEmpty().filterNotNull().map { it.toDomain() },
        numberOfEpisodes = numberOfEpisodes ?: 0,
        numberOfSeasons = numberOfSeasons ?: 0,
        episodeRunTime = episodeRunTime?.filterNotNull().orEmpty(),
        lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
        status = status.orEmpty(),
        type = type.orEmpty(),
        homepage = homepage.orEmpty(),
        inProduction = inProduction ?: false,
        languages = languages?.filterNotNull().orEmpty()
    )
}

fun TvShowDetails.CreatedBy.toDomain(): Creator {
    return Creator(
        id = id ?: 0,
        name = name.orEmpty(),
        creditId = creditId.orEmpty(),
        gender = gender ?: 0,
        profilePath = profilePath
    )
}

fun TvShowDetails.LastEpisodeToAir.toDomain(): Episode {
    return Episode(
        id = id ?: 0,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        airDate = airDate,
        episodeNumber = episodeNumber ?: 0,
        seasonNumber = seasonNumber ?: 0,
        showId = showId ?: 0,
        stillPath = stillPath,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        productionCode = productionCode.orEmpty()
    )
}

fun TvShowDetails.Network.toDomain(): Network {
    return Network(
        id = id ?: 0,
        name = name.orEmpty(),
        logoPath = logoPath,
        originCountry = originCountry.orEmpty()
    )
}

fun TvShowDetails.ProductionCompany.toDomain(): ProductionCompany {
    return ProductionCompany(
        id = id ?: 0,
        name = name.orEmpty(),
        logoPath = logoPath,
        originCountry = originCountry.orEmpty()
    )
}

fun TvShowDetails.Season.toDomain(): Season {
    return Season(
        id = id ?: 0,
        name = name.orEmpty(),
        overview = overview.orEmpty(),
        airDate = airDate,
        episodeCount = episodeCount ?: 0,
        posterPath = posterPath,
        seasonNumber = seasonNumber ?: 0
    )
}
