package com.hrudhaykanth116.composeapp.home.dashboard

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardJournalUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTodoUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardTvUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.domain.GetDashboardWeatherUseCase
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenEffect
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenEvent
import com.hrudhaykanth116.composeapp.home.dashboard.models.DashboardScreenState
import com.hrudhaykanth116.composeapp.home.dashboard.models.TodoSummary
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardTodoUseCase: GetDashboardTodoUseCase,
    private val getDashboardWeatherUseCase: GetDashboardWeatherUseCase,
    private val getDashboardTvUseCase: GetDashboardTvUseCase,
    private val getDashboardJournalUseCase: GetDashboardJournalUseCase,
    networkMonitor: NetworkMonitor,
) : UIStateViewModel<DashboardScreenState, DashboardScreenEvent, DashboardScreenEffect>(
    initialState = UIState.Loading(DashboardScreenState()),
    defaultState = DashboardScreenState(),
    networkMonitor = networkMonitor,
) {

    override fun initializeData() {
        Logger.d(TAG, "initializeData")
        loadDashboardData()
    }

    override fun processEvent(event: DashboardScreenEvent) {
        when (event) {
            DashboardScreenEvent.Refresh -> loadDashboardData()
        }
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            setLoadingState(currentContentState)

            val todoDeferred = async { getDashboardTodoUseCase() }
            val weatherDeferred = async { getDashboardWeatherUseCase() }
            val tvDeferred = async { getDashboardTvUseCase() }
            val journalDeferred = async { getDashboardJournalUseCase() }

            val todo = todoDeferred.await()
            val weather = weatherDeferred.await()
            val tv = tvDeferred.await()
            val journal = journalDeferred.await()

            setState {
                UIState.Idle(
                    DashboardScreenState(
                        todoSummary = todo,
                        weatherSummary = weather,
                        tvSummary = tv,
                        journalSummary = journal,
                    )
                )
            }
        }
    }

    companion object{
        private const val TAG = "DashboardViewModel"
    }
}
