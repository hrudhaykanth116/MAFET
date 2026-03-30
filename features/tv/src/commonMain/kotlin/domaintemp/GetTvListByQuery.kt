package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
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

        val tvShowResult: DomainResult<TvShowSearchResults> =
            tvShowsRepository.searchTvShow(query)
        val myTvList = myTvListRepository.getMyTvList()


        when (tvShowResult) {
            is DomainResult.Error -> {
                tvShowResult
            }

            is DomainResult.Success -> {

                val list = tvShowResult.data.tvShowDataList?.filterNotNull()?.map { tvShowData ->
                    SearchScreenItemUIState(
                        id = tvShowData.id,
                        name = tvShowData.name?.toUIText() ?: "- -".toUIText(),
                        image = (BaseUrlConstants.IMAGES_BASE_URL + tvShowData.posterPath).toUrlImageHolder(),
                        isMyTvList = myTvList.any { tvShowData.id == it.id }
                    )
                }

                DomainResult.Success(
                    list
                )
            }
        }


    }


}