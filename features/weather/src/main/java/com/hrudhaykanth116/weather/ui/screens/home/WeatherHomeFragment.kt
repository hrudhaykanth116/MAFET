package com.hrudhaykanth116.weather.ui.screens.home

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.hrudhaykanth116.core.udf.UDFFragment
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.databinding.FragmentWeatherHomeScreenBinding as BINDING
import com.hrudhaykanth116.weather.ui.screens.adapters.ForeCastListAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEffect as EFFECT
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenEvent as EVENT
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenUIState as STATE


@AndroidEntryPoint
class WeatherHomeFragment :
    UDFFragment<STATE, EVENT, EFFECT, BINDING>(
        R.layout.fragment_weather_home_screen
    ) {

    override val viewModel: WeatherHomeScreenViewModel by viewModels()

    private val forecastListAdapter: ForeCastListAdapter by lazy {
        ForeCastListAdapter() {
            // TODO: Refactor
            when (it) {
                is ForeCastListAdapter.ItemEvent.Clicked -> {
                    val action =
                        WeatherHomeFragmentDirections.actionWeatherHomeScreenToWeatherDetailsFragment(
                            it.weatherListItemUIState
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun initViews() {
        binding.weatherList.adapter = forecastListAdapter

        binding.searchIcon.setOnClickListener {
            sendEvent(EVENT.Refresh)
        }
    }

    override fun processNewEffect(effect: EFFECT) {

    }

    override fun processNewState(state: STATE) {

        state.errorMessage?.let {
            Toast.makeText(requireContext(), it.getText(requireContext()), Toast.LENGTH_SHORT)
                .show()
            sendEvent(EVENT.UserMessageShown(it))
        }

        binding.progressBar.isVisible = state.isLoading

        forecastListAdapter.submitList(
            state.weatherListItemsUIState
        )

    }
}