package com.hrudhaykanth116.tv.data.datasources.remote.ktor

import com.hrudhaykanth116.tv.BuildConfig
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvCreditsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvImagesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvReviewsResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.GetTvVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.MovieVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.PopularMoviesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Ktor-based TMDB API service using HttpClient from core-network module
 *
 * Api documentation: https://developer.themoviedb.org/reference/intro/getting-started
 */
class TmdbApiServiceKtor(
    private val httpClient: HttpClient
) {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private val API_KEY = BuildConfig.TMDB_API_KEY
    }

    // ****************** Movies **************************

    suspend fun getPopularMoviesList(
        pageId: Int,
        apiKey: String = API_KEY
    ): Result<PopularMoviesResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}movie/popular") {
                parameter("page", pageId)
                parameter("api_key", apiKey)
            }.body<PopularMoviesResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String = API_KEY
    ): Result<MovieVideosResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}movie/$movieId/videos") {
                parameter("api_key", apiKey)
            }.body<MovieVideosResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ****************** TV Shows **************************

    suspend fun getPopularTvShows(
        pageId: Int,
        apiKey: String = API_KEY
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/popular") {
                parameter("page", pageId)
                parameter("api_key", apiKey)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTopRatedTvShows(
        pageId: Int,
        apiKey: String = API_KEY
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/top_rated") {
                parameter("page", pageId)
                parameter("api_key", apiKey)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAiringTodayShows(
        pageId: Int,
        apiKey: String = API_KEY
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/airing_today") {
                parameter("page", pageId)
                parameter("api_key", apiKey)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTrendingTv(
        timeWindow: String,
        apiKey: String = API_KEY,
        language: String = "en-US"
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}trending/tv/$timeWindow") {
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchTv(
        query: String,
        apiKey: String = API_KEY,
        language: String = "en-US",
        page: String = "1",
        includeAdult: String = "false"
    ): Result<TvShowSearchResults> {
        return try {
            val response = httpClient.get("${BASE_URL}search/tv") {
                parameter("query", query)
                parameter("api_key", apiKey)
                parameter("language", language)
                parameter("page", page)
                parameter("include_adult", includeAdult)
            }.body<TvShowSearchResults>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun discoverTv(
        page: Int,
        genres: String?,
        sortBy: String = "popularity.desc",
        apiKey: String = API_KEY,
        language: String = "en-US"
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}discover/tv") {
                parameter("page", page)
                if (genres != null) parameter("with_genres", genres)
                parameter("sort_by", sortBy)
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvShowDetails(
        tvShowId: Int,
        apiKey: String = API_KEY
    ): Result<TvShowDetails> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId") {
                parameter("api_key", apiKey)
            }.body<TvShowDetails>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvGenres(
        apiKey: String = API_KEY,
        language: String = "en-US"
    ): Result<GetTvGenresResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}genre/tv/list") {
                parameter("api_key", apiKey)
                parameter("language", language)
            }.body<GetTvGenresResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ****************** TV Show Details **************************

    suspend fun getTvShowImages(
        tvShowId: Int,
        apiKey: String = API_KEY
    ): Result<GetTvImagesResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId/images") {
                parameter("api_key", apiKey)
            }.body<GetTvImagesResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvShowVideos(
        tvShowId: Int,
        apiKey: String = API_KEY
    ): Result<GetTvVideosResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId/videos") {
                parameter("api_key", apiKey)
            }.body<GetTvVideosResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvShowsSimilar(
        tvShowId: Int,
        page: Int,
        apiKey: String = API_KEY
    ): Result<TvShowDataPagedResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId/similar") {
                parameter("page", page)
                parameter("api_key", apiKey)
            }.body<TvShowDataPagedResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvReviews(
        tvShowId: Int,
        page: Int,
        apiKey: String = API_KEY
    ): Result<GetTvReviewsResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId/reviews") {
                parameter("page", page)
                parameter("api_key", apiKey)
            }.body<GetTvReviewsResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTvCredits(
        tvShowId: Int,
        apiKey: String = API_KEY
    ): Result<GetTvCreditsResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}tv/$tvShowId/aggregate_credits") {
                parameter("api_key", apiKey)
            }.body<GetTvCreditsResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
