package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.models.TvShowPagedResult
import com.hrudhaykanth116.tv.domain.models.TvShowSearchResult

interface ITvShowsRepository {
    suspend fun getTvShowDetails(tvShowId: Int): DomainResult<TvShowDetail>
    suspend fun searchTvShow(query: String): DomainResult<TvShowSearchResult>
    suspend fun getTvGenres(): DomainResult<GetTvGenresResponse>
    suspend fun getTvImages(tvId: Int): DomainResult<GetTvImagesResponse>
    suspend fun getTvShowVideos(tvId: Int): DomainResult<GetTvVideosResponse>
    suspend fun getTvShowsSimilar(tvId: Int, pageId: Int): DomainResult<TvShowPagedResult>
    suspend fun getTvReviews(tvId: Int, pageId: Int): DomainResult<GetTvReviewsResponse>
    suspend fun getTvCredits(tvId: Int): DomainResult<GetTvCreditsResponse>
}
