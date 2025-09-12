package com.hrudhaykanth116.tv.data.repositories.tv

import com.hrudhaykanth116.core.common.di.IoDispatcher
import com.hrudhaykanth116.core.data.BaseRepository
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseRepository(dispatcher), ITvShowsRepository {

    override suspend fun getTvShowDetails(tvShowId: Int): RepoResultWrapper<TvShowDetails> =
        getResult {
            tvShowsRemoteDataSource.fetchTvShowDetails(tvShowId)
        }

    override suspend fun searchTvShow(query: String): RepoResultWrapper<TvShowSearchResults> =
        getResult {
            tvShowsRemoteDataSource.searchTvShow(query)
        }

    override suspend fun getTvGenres(): RepoResultWrapper<GetTvGenresResponse> = getResult {
        tvShowsRemoteDataSource.getTvGenres()
    }

    override suspend fun getTvImages(tvId: Int): RepoResultWrapper<GetTvImagesResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvImages(tvId)
        }

    override suspend fun getTvShowVideos(tvId: Int): RepoResultWrapper<GetTvVideosResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvShowVideos(tvId)
        }

    override suspend fun getTvShowsSimilar(
        tvId: Int,
        pageId: Int,
    ): RepoResultWrapper<TvShowDataPagedResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvShowsSimilar(tvId, pageId)
        }

    override suspend fun getTvReviews(
        tvId: Int,
        pageId: Int,
    ): RepoResultWrapper<GetTvReviewsResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvReviews(tvId, pageId)
        }

    override suspend fun getTvCredits(tvId: Int): RepoResultWrapper<GetTvCreditsResponse> =
        getResult {
            tvShowsRemoteDataSource.getTvCredits(tvId)
        }

}