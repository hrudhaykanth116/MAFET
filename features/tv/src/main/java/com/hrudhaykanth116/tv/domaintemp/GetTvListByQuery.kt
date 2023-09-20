package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTvListByQuery @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
) {

    suspend operator fun invoke(query: String): DataResult<List<SearchScreenItemUIState>?> {

        val tvShowResult: DataResult<TvShowSearchResults> = tvShowsRepository.searchTvShow(query)

        when (tvShowResult) {
            is DataResult.Error -> {
                return DataResult.Error()
            }

            is DataResult.Success -> {

                val list = tvShowResult.data.tvShowDataList?.filterNotNull()?.map {
                    SearchScreenItemUIState(
                        it.name?.toUIText() ?: "- -".toUIText(),
                        it.posterPath?.toUrlImageHolder()
                    )
                }

                return DataResult.Success(
                    list
                )
            }
        }


    }


}