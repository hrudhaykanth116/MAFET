package com.hrudhaykanth116.media.data.network.ktor

import com.hrudhaykanth116.media.BuildConfig
import com.hrudhaykanth116.media.data.models.CuratedPhotosResponse
import com.hrudhaykanth116.media.data.models.GetPopularVideosResponse
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.models.PhotoSearchResponse
import com.hrudhaykanth116.media.data.models.VideoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

/**
 * Ktor-based Pexels API service using HttpClient from core-network module
 *
 * Api documentation: https://www.pexels.com/api/documentation/
 */
class PexelsApiServiceKtor(
    private val httpClient: HttpClient
) {

    companion object {
        private const val BASE_URL = "https://api.pexels.com/"
    }

    /**
     * Search for photos
     * https://api.pexels.com/v1/search?query={query}&page={page}&per_page={per_page}
     */
    suspend fun searchPhotos(
        query: String,
        page: Int = 1,
        perPage: Int = 15,
        apiKey: String = BuildConfig.PEXELS_API_KEY
    ): Result<PhotoSearchResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}v1/search") {
                header("Authorization", apiKey)
                parameter("query", query)
                parameter("page", page)
                parameter("per_page", perPage)
            }.body<PhotoSearchResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get curated photos
     * https://api.pexels.com/v1/curated?page={page}&per_page={per_page}
     */
    suspend fun getCuratedPhotos(
        page: Int = 1,
        perPage: Int = 15,
        apiKey: String = BuildConfig.PEXELS_API_KEY
    ): Result<CuratedPhotosResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}v1/curated") {
                header("Authorization", apiKey)
                parameter("page", page)
                parameter("per_page", perPage)
            }.body<CuratedPhotosResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get photo by ID
     * https://api.pexels.com/v1/photos/{id}
     */
    suspend fun getPhotoById(
        id: Int,
        apiKey: String = BuildConfig.PEXELS_API_KEY
    ): Result<PhotoResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}v1/photos/$id") {
                header("Authorization", apiKey)
            }.body<PhotoResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get video by ID
     * https://api.pexels.com/videos/videos/{id}
     */
    suspend fun getVideoById(
        id: Int,
        apiKey: String = BuildConfig.PEXELS_API_KEY
    ): Result<VideoResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}videos/videos/$id") {
                header("Authorization", apiKey)
            }.body<VideoResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get popular videos
     * https://api.pexels.com/videos/popular?per_page={per_page}
     */
    suspend fun getPopularVideos(
        perPage: Int,
        apiKey: String = BuildConfig.PEXELS_API_KEY
    ): Result<GetPopularVideosResponse> {
        return try {
            val response = httpClient.get("${BASE_URL}videos/popular") {
                header("Authorization", apiKey)
                parameter("per_page", perPage)
            }.body<GetPopularVideosResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
