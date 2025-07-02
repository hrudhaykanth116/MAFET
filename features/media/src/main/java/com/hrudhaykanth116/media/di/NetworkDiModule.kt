package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.data.network.PexelsApisService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkDiModule {

    // TODO: Reuse dependencies from other modules.

    @Named("media_moshi")
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Named("media_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Named("media_okhttp")
    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            // .addInterceptor()
            // .addTimeOut()
            .build()


    @Named("media_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("media_okhttp") okHttpClient: OkHttpClient,
        @Named("media_baseurl") BASE_URL: String,
        @Named("media_moshi") moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideTvApisService(@Named("media_retrofit") retrofit: Retrofit): PexelsApisService =
        retrofit.create(PexelsApisService::class.java)

}