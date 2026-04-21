package com.hrudhaykanth116.tv.ui.screens.details

import com.hrudhaykanth116.core.ui.models.ImageHolder

data class TvDetailsScreenUIState(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropImage: ImageHolder?,
    val dateRange: String,
    val rating: String,
    val genres: List<String>,
    val networks: List<NetworkUIState>,
    val isBookmarked: Boolean = false,
    val aboutTabState: AboutTabUIState? = null,
    val moreLikeThisTabState: MoreLikeThisTabUIState? = null,
    val mediaTabState: MediaTabUIState? = null,
    val reviewsTabState: ReviewsTabUIState? = null,
    val fullscreenImageUrl: String? = null,
    val isDownloadingImage: Boolean = false,
)

data class NetworkUIState(
    val name: String,
    val logo: ImageHolder?,
)

// --- About Tab ---

data class AboutTabUIState(
    val cast: List<CastUIState>,
    val creators: List<CreatorUIState>,
    val seasons: List<SeasonUIState>,
    val productionCompanies: List<ProductionCompanyUIState>,
    val languages: List<String>,
    val status: String,
    val type: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
)

data class CastUIState(
    val id: Int,
    val name: String,
    val character: String,
    val profileImage: ImageHolder?,
)

data class CreatorUIState(
    val id: Int,
    val name: String,
    val profileImage: ImageHolder?,
)

data class SeasonUIState(
    val id: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String,
    val posterImage: ImageHolder?,
    val seasonNumber: Int,
)

data class ProductionCompanyUIState(
    val name: String,
    val logo: ImageHolder?,
)

// --- More Like This Tab ---

data class MoreLikeThisTabUIState(
    val similarShows: List<SimilarShowUIState>,
)

data class SimilarShowUIState(
    val id: Int,
    val name: String,
    val rating: String,
    val posterImage: ImageHolder?,
)

// --- Media Tab ---

data class MediaTabUIState(
    val images: List<MediaImageUIState>,
    val videos: List<MediaVideoUIState>,
)

data class MediaImageUIState(
    val image: ImageHolder,
    val originalUrl: String,
)

data class MediaVideoUIState(
    val key: String,
    val name: String,
    val thumbnail: ImageHolder,
    val site: String,
)

data class ReviewsTabUIState(
    val reviews: List<ReviewUIState>,
)

data class ReviewUIState(
    val id: String,
    val author: String,
    val avatar: ImageHolder?,
    val content: String,
    val rating: String,
    val createdAt: String,
)
