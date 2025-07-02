package com.hrudhaykanth116.media.data.repositories

import com.hrudhaykanth116.media.data.network.SearchImagesRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImagesRepository @Inject constructor(
    private val searchImagesRemoteDataSource: SearchImagesRemoteDataSource
) {

    fun search(){

    }

}