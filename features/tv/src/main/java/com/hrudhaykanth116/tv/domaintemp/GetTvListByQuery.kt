package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTvListByQuery @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
) {

    suspend operator fun invoke() {

        val tvShowResult: DataResult<TvShowSearchResults> = tvShowsRepository.searchTvShow("k")


    }


}