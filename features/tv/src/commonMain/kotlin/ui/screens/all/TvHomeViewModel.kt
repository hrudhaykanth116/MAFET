package com.hrudhaykanth116.tv.ui.screens.all

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.tv.domain.models.TvCategory
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.datasources.remote.models.tv.CategorisedTvShows
import com.hrudhaykanth116.tv.domaintemp.GetAllTvShowsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TvHomeViewModel(
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
        initializeData()
    }

    override fun initializeData() {
        loadTvShows()
    }

    fun loadTvShows() {
        viewModelScope.launch {

            setState {
                UIState.Loading(currentContentState)
            }

            when (val result = getAllTvShowsUseCase()) {
                is RepoResultWrapper.Success -> {
                    setIdleState {
                        result.data.toUiState()
                    }
                }
                is RepoResultWrapper.Error -> {
                    setState {
                        UIState.Error(
                            result.errorState
                        )
                    }
                }
            }
        }
    }


    override fun processEvent(event: TvHomeScreenEvent) {
        when (event) {
            TvHomeScreenEvent.Temp -> {

            }
        }
    }

    fun CategorisedTvShows.toUiState(): TvHomeScreenUIState {
        return TvHomeScreenUIState(
            categories = listOf(
                TvShowCategoryUi(
                    category = TvCategory.POPULAR,
                    title = TvCategory.POPULAR.displayName,
                    shows = popular.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    category = TvCategory.TOP_RATED,
                    title = TvCategory.TOP_RATED.displayName,
                    shows = topRated.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    category = TvCategory.AIRING_TODAY,
                    title = TvCategory.AIRING_TODAY.displayName,
                    shows = airingToday.map { it.toUi() }
                ),
                TvShowCategoryUi(
                    category = TvCategory.TRENDING,
                    title = TvCategory.TRENDING.displayName,
                    shows = trending.map { it.toUi() }
                )
            )
        )
    }

    fun TvShowData.toUi(): TvShowUi {
        return TvShowUi(
            id = id,
            name = name.orEmpty(),
            posterImage = ImageHolder.Url("https://image.tmdb.org/t/p/w500${posterPath.orEmpty()}"),
            rating = voteAverage ?: 0.0
        )
    }



    companion object {
        private const val TAG = "TvHomeViewModel"
    }

}