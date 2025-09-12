package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults

interface ITvShowsRepository {
    suspend fun getTvShowDetails(tvShowId: Int): RepoResultWrapper<TvShowDetails>
    suspend fun searchTvShow(query: String): RepoResultWrapper<TvShowSearchResults>
    suspend fun getTvGenres(): RepoResultWrapper<GetTvGenresResponse>
    suspend fun getTvImages(tvId: Int): RepoResultWrapper<GetTvImagesResponse>
    suspend fun getTvShowVideos(tvId: Int): RepoResultWrapper<GetTvVideosResponse>
    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): RepoResultWrapper<TvShowDataPagedResponse>
    suspend fun getTvReviews(tvId: Int, pageId: Int): RepoResultWrapper<GetTvReviewsResponse>
    suspend fun getTvCredits(tvId: Int): RepoResultWrapper<GetTvCreditsResponse>
}
