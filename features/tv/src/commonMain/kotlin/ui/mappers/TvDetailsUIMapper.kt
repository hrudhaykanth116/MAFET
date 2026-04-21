package com.hrudhaykanth116.tv.ui.mappers

import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult
import com.hrudhaykanth116.tv.ui.screens.details.AboutTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.CastUIState
import com.hrudhaykanth116.tv.ui.screens.details.CreatorUIState
import com.hrudhaykanth116.tv.ui.screens.details.MediaImageUIState
import com.hrudhaykanth116.tv.ui.screens.details.MediaTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.MediaVideoUIState
import com.hrudhaykanth116.tv.ui.screens.details.MoreLikeThisTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.NetworkUIState
import com.hrudhaykanth116.tv.ui.screens.details.ProductionCompanyUIState
import com.hrudhaykanth116.tv.ui.screens.details.ReviewUIState
import com.hrudhaykanth116.tv.ui.screens.details.ReviewsTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.SeasonUIState
import com.hrudhaykanth116.tv.ui.screens.details.SimilarShowUIState
import com.hrudhaykanth116.tv.ui.screens.details.TvDetailsScreenUIState

// --- Header mapping ---

fun TvShowDetail.toUIState(isBookmarked: Boolean = false): TvDetailsScreenUIState {
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
        networks = networks.map { it.toNetworkUIState() },
        isBookmarked = isBookmarked,
        aboutTabState = toAboutTabUIState(cast = emptyList()),
    )
}

private fun com.hrudhaykanth116.tv.domain.models.Network.toNetworkUIState() = NetworkUIState(
    name = name,
    logo = logoPath?.takeIf { it.isNotEmpty() }
        ?.let { (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder() },
)

// --- About Tab mapping ---

fun TvShowDetail.toAboutTabUIState(cast: List<CastUIState>): AboutTabUIState {
    return AboutTabUIState(
        cast = cast,
        creators = createdBy.map { creator ->
            CreatorUIState(
                id = creator.id,
                name = creator.name,
                profileImage = creator.profilePath?.let {
                    (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder()
                },
            )
        },
        seasons = seasons.map { season ->
            SeasonUIState(
                id = season.id,
                name = season.name,
                episodeCount = season.episodeCount,
                airDate = season.airDate.orEmpty(),
                posterImage = season.posterPath?.let {
                    (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder()
                },
                seasonNumber = season.seasonNumber,
            )
        },
        productionCompanies = productionCompanies.map { company ->
            ProductionCompanyUIState(
                name = company.name,
                logo = company.logoPath?.takeIf { it.isNotEmpty() }
                    ?.let { (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder() },
            )
        },
        languages = languages,
        status = status,
        type = type,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
    )
}

fun GetTvCreditsResponse.toCastUIStates(): List<CastUIState> {
    return cast.orEmpty()
        .filterNotNull()
        .sortedBy { it.order ?: Int.MAX_VALUE }
        .take(20)
        .map { castMember ->
            CastUIState(
                id = castMember.id ?: 0,
                name = castMember.name.orEmpty(),
                character = castMember.character.orEmpty(),
                profileImage = castMember.profile_path?.let {
                    (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder()
                },
            )
        }
}

// --- More Like This Tab mapping ---

fun TvShowPagedResult.toMoreLikeThisTabUIState(): MoreLikeThisTabUIState {
    return MoreLikeThisTabUIState(
        similarShows = tvShows.map { show ->
            val formattedRating = ((show.voteAverage * 10).toInt() / 10.0).toString()
            SimilarShowUIState(
                id = show.id,
                name = show.name,
                rating = "$formattedRating / 10",
                posterImage = show.posterPath?.let {
                    (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder()
                },
            )
        }
    )
}

// --- Media Tab mapping ---

fun toMediaTabUIState(
    images: GetTvImagesResponse,
    videos: GetTvVideosResponse,
): MediaTabUIState {
    val imageStates = images.posters
        .filterNotNull()
        .filter { (it.aspect_ratio ?: 1.0) < 1.0 }
        .mapNotNull { imageObj ->
            imageObj.file_path?.let { path ->
                MediaImageUIState(
                    image = (BaseUrlConstants.IMAGES_THUMBNAIL_BASE_URL + path).toUrlImageHolder(),
                    originalUrl = BaseUrlConstants.IMAGES_BASE_URL + path,
                )
            }
        }

    val videoStates = videos.results.orEmpty()
        .filterNotNull()
        .filter { it.site.equals("YouTube", ignoreCase = true) && !it.key.isNullOrEmpty() }
        .map { video ->
            MediaVideoUIState(
                key = video.key!!,
                name = video.name.orEmpty(),
                thumbnail = "https://img.youtube.com/vi/${video.key}/hqdefault.jpg".toUrlImageHolder(),
                site = video.site.orEmpty(),
            )
        }

    return MediaTabUIState(
        images = imageStates,
        videos = videoStates,
    )
}

// --- Reviews / Discussions Tab mapping ---

fun GetTvReviewsResponse.toReviewsTabUIState(): ReviewsTabUIState {
    val reviews = reviewDetails.mapNotNull { review ->
        val id = review.id ?: return@mapNotNull null
        val content = review.content?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
        val author = review.author?.takeIf { it.isNotBlank() }
            ?: review.author_details?.username
            ?: review.author_details?.name
            ?: "Anonymous"

        val avatarPath = review.author_details?.avatar_path
        val avatar = avatarPath?.let {
            if (it.startsWith("/http")) it.drop(1).toUrlImageHolder()
            else (BaseUrlConstants.IMAGES_BASE_URL + it).toUrlImageHolder()
        }

        val ratingValue = review.author_details?.rating
        val rating = if (ratingValue != null && ratingValue > 0) {
            val rounded = ((ratingValue * 10).toInt() / 10.0)
            "$rounded / 10"
        } else ""

        val createdAt = review.created_at?.take(10).orEmpty()

        ReviewUIState(
            id = id,
            author = author,
            avatar = avatar,
            content = content,
            rating = rating,
            createdAt = createdAt,
        )
    }
    return ReviewsTabUIState(reviews = reviews)
}
