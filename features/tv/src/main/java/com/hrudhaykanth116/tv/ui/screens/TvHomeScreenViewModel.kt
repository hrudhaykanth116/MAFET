package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.data.models.models.TvShowData
import com.hrudhaykanth116.tv.data.models.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEffect
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvHomeScreenViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
) : UDFViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(TvHomeScreenUIState()) {

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            val tvShowResult: DataResult<TvShowSearchResults> = tvShowsRepository.searchTvShow("k")
            tvShowResult.process(
                onSuccess = { tvShowSearchResults ->
                    val tvShows: List<TvShowData?>? = tvShowSearchResults.tvShowDataList
                    setState {
                        copy(
                            tvShows = tvShows
                        )
                    }

                },
                onError = { error ->
                    setState {
                        copy(
                            userMessage = UserMessage.Error(error.uiMessage ?: "Something went wrong".toUIText())
                        )
                    }
                }
            )
        }
    }

    override fun processEvent(event: TvHomeScreenEvent) {

    }


}