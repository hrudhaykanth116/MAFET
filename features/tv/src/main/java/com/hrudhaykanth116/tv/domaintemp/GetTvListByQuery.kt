package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTvListByQuery @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    private val myTvListRepository: MyTvListRepository,
) {

    suspend operator fun invoke(
        query: String,
    ): RepoResultWrapper<List<SearchScreenItemUIState>?> = withContext(
        Dispatchers.Default
    ) {

        val tvShowResult: RepoResultWrapper<TvShowSearchResults> =
            tvShowsRepository.searchTvShow(query)
        val myTvList = myTvListRepository.getMyTvList()


        when (tvShowResult) {
            is RepoResultWrapper.Error -> {
                tvShowResult
            }

            is RepoResultWrapper.Success -> {

                val list = tvShowResult.data.tvShowDataList?.filterNotNull()?.map { tvShowData ->
                    SearchScreenItemUIState(
                        id = tvShowData.id,
                        name = tvShowData.name?.toUIText() ?: "- -".toUIText(),
                        image = (BaseUrlConstants.IMAGES_BASE_URL + tvShowData.posterPath).toUrlImageHolder(),
                        isMyTvList = myTvList.any { tvShowData.id == it.id }
                    )
                }

                RepoResultWrapper.Success(
                    list
                )
            }
        }


    }


}