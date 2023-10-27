package com.hrudhaykanth116.tv.domaintemp

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.models.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
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
    ): DataResult<List<SearchScreenItemUIState>?> = withContext(
        Dispatchers.Default
    ) {

        val tvShowResult: DataResult<TvShowSearchResults> = tvShowsRepository.searchTvShow(query)
        val myTvList = myTvListRepository.getMyTvList()


        when (tvShowResult) {
            is DataResult.Error -> {
                DataResult.Error()
            }

            is DataResult.Success -> {

                val list = tvShowResult.data.tvShowDataList?.filterNotNull()?.map { tvShowData ->
                    SearchScreenItemUIState(
                        id = tvShowData.id,
                        name = tvShowData.name?.toUIText() ?: "- -".toUIText(),
                        image = (BaseUrlConstants.IMAGES_BASE_URL + tvShowData.posterPath).toUrlImageHolder(),
                        isMyTvList = myTvList.any { tvShowData.id == it.id }
                    )
                }

                DataResult.Success(
                    list
                )
            }
        }


    }


}