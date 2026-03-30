package com.hrudhaykanth116.tv.domain.usecases

import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domain.models.TvShowSearchResult
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTvListByQuery(
    private val tvShowsRepository: TvShowsRepository,
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(
        query: String,
    ): DomainResult<List<SearchScreenItemUIState>?> = withContext(
        Dispatchers.Default
    ) {

        val tvShowResult: DomainResult<TvShowSearchResult> =
            tvShowsRepository.searchTvShow(query)
        val myTvList = myTvListRepository.getMyTvList()


        when (tvShowResult) {
            is DomainResult.Error -> {
                tvShowResult
            }

            is DomainResult.Success -> {

                val list = tvShowResult.data.tvShows.map { tvShow ->
                    SearchScreenItemUIState(
                        id = tvShow.id,
                        name = tvShow.name.toUIText(),
                        image = (BaseUrlConstants.IMAGES_BASE_URL + tvShow.posterPath).toUrlImageHolder(),
                        isMyTvList = myTvList.any { tvShow.id == it.id }
                    )
                }

                DomainResult.Success(
                    list
                )
            }
        }


    }


}