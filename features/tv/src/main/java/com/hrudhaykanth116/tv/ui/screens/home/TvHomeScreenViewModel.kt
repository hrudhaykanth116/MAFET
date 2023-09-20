package com.hrudhaykanth116.tv.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.search.TvShowSearchResults
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.GetMyTvListUseCase
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEffect
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvHomeScreenViewModel @Inject constructor(
    private val gwtMyTvListUseCase: GetMyTvListUseCase,
) : UDFViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(TvHomeScreenUIState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val tvShowResult = gwtMyTvListUseCase()
            tvShowResult.process(
                onSuccess = { tvShowSearchResults ->
                    val tvShows: List<MyTvDomainModel> = tvShowSearchResults
                    setState {
                        copy(
                            tvShows = tvShows.toUIState()
                        )
                    }

                },
                onError = { error ->
                    setState {
                        copy(
                            userMessage = UserMessage.Error(
                                error.uiMessage ?: "Something went wrong".toUIText()
                            )
                        )
                    }
                }
            )
        }
    }

    override fun processEvent(event: TvHomeScreenEvent) {

        when (event) {
            TvHomeScreenEvent.AddNew -> onAddNewEvent()
            is TvHomeScreenEvent.Delete -> onAddNewEvent()
            is TvHomeScreenEvent.MyTvListItemClicked -> onAddNewEvent()
        }

    }

    private fun onAddNewEvent() {
        setState {
            copy(
                shouldNavigateToSearchScreen = true
            )
        }
    }


}