package com.hrudhaykanth116.tv.ui.screens.all

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.tv.CategorisedTvShows
import com.hrudhaykanth116.tv.domaintemp.GetAllTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvHomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getAllTvShowsUseCase: GetAllTvShowsUseCase,
) : UIStateViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(
    initialState = UIState.Loading(),
    defaultState = TvHomeScreenUIState(),
    networkMonitor = networkMonitor

) {


    private val _uiState = MutableStateFlow(TvHomeScreenUIState())
    val uiStateTemp: StateFlow<TvHomeScreenUIState> = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    init {
        loadTvShows()
    }

    fun loadTvShows() {
        viewModelScope.launch {
            when (val result = getAllTvShowsUseCase()) {
                is DataResult.Success -> {
                    _uiState.value = result.data.toUiState()
                }
                is DataResult.Error -> {
                    // _error.emit(result.message ?: "Something went wrong")
                }
            }
        }
    }


    override fun processEvent(event: TvHomeScreenEvent) {
        when (event) {

            else -> {}
        }
    }

    override fun onRetry() {

    }

    fun CategorisedTvShows.toUiState(): TvHomeScreenUIState {
        return TvHomeScreenUIState(
            categories = listOf(
                TvShowCategoryUi(
                    title = "Popular",
                    shows = popular.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    title = "Top Rated",
                    shows = topRated.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    title = "Airing Today",
                    shows = airingToday.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    title = "Trending",
                    shows = trending.map { it.toUi() }
                )
            )
        )
    }

    fun TvShowData.toUi(): TvShowUi {
        return TvShowUi(
            id = id,
            name = name.orEmpty(),
            posterUrl = posterPath.orEmpty(),
            rating = voteAverage ?: 0.0
        )
    }



    companion object {
        private const val TAG = "TvHomeViewModel"
    }

}