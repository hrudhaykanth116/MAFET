package com.hrudhaykanth116.tv.data.datasources.remote.retrofit

import com.hrudhaykanth116.tv.data.models.constants.MoviesDbConstants
import com.hrudhaykanth116.tv.data.datasources.remote.models.MovieVideosResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.PopularMoviesResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDataPagedResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.data.datasources.remote.models.genres.GetTvGenresResponse
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


public interface RetroApis {


    /****************** Movies ***************************/

    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("page") pageId: Int,
        @Query("api_key", encoded = true) apiKey: String = MoviesDbConstants.API_KEY,
    ): Response<PopularMoviesResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
    ): Call<MovieVideosResponse>

    /****************** Movies ***************************/


    /****************** TV ***************************/

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
    ): Response<TvShowDataPagedResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
    ): Response<TvShowDataPagedResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayShows(
        @Query("page") pageId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
    ): Response<TvShowDataPagedResponse>

    @GET("trending/tv/{time_window}")
    suspend fun trendingTv(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<TvShowDataPagedResponse>

    @GET("search/tv")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
        @Query("include_adult") includeAdult: String = "false",
    ): Response<TvShowSearchResults>

    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int,
        @Query("with_genres") genres: String?,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<TvShowDataPagedResponse>

    @GET("tv/{tvShowId}")
    suspend fun getTvShowDetails(
        @Path("tvShowId") tvShowId: Int,
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
    ): Response<TvShowDetails>

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query("api_key") apiKey: String = MoviesDbConstants.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<GetTvGenresResponse>


    /****************** TV ***************************/

}