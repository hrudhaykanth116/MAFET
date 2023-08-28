package com.hrudhaykanth116.weather.ui.screens.weatherdetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hrudhaykanth116.core.udf.UDFFragment
import com.hrudhaykanth116.weather.R
import dagger.hilt.android.AndroidEntryPoint
import com.hrudhaykanth116.weather.databinding.FragmentWeatherDetailsBinding as Binding
import com.hrudhaykanth116.weather.domain.models.weatherdetails.WeatherDetailsScreenEffect as Effect
import com.hrudhaykanth116.weather.domain.models.weatherdetails.WeatherDetailsScreenEvent as Event
import com.hrudhaykanth116.weather.domain.models.weatherdetails.WeatherDetailsScreenUIState as State

@AndroidEntryPoint
class WeatherDetailsFragment : UDFFragment<State, Event, Effect, Binding>(
    R.layout.fragment_weather_details
) {

    override val viewModel: WeatherDetailsViewModel by viewModels()

    // TODO: Move data to viewModel savedstatehandle
    private val args: WeatherDetailsFragmentArgs by navArgs()


    override fun initViews() {
        val state = args.weatherData
        binding.description.text = state.day
        binding.humidity.text = "Humidity: ${state.humidity} %"
        binding.minTemperature.text = "Min temp: ${state.minTemp}℃"
        binding.maxTemperature.text = "Max temp: ${state.maxTemp}℃"
        binding.sunRise.text = "Sunrise: ${state.sunrise}"
        binding.sunSet.text = "Sunset: ${state.sunset}"
        // TODO: Implement these in better ui
        // binding.rain.text = "Rain: ${state.rain} mm/h"
        // binding.wind.text = "Wind: ${state.windSpeed} meters/sec"
        // binding.clouds.text = "Clouds: ${state.clouds} %"
        // binding.pressure.text = "Pressure: ${state.pressure} hPa"
    }

    override fun processNewEffect(effect: Effect) {

    }

    override fun processNewState(state: State) {

    }


}