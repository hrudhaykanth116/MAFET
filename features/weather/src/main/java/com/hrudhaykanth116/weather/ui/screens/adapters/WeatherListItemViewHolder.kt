package com.hrudhaykanth116.weather.ui.screens.adapters

// class WeatherListItemViewHolder(
//     private val listItemWeatherBinding: ListItemWeatherBinding
// ) : RecyclerView.ViewHolder(listItemWeatherBinding.root) {
//
//     fun bind(
//         weatherElementUIState: WeatherElementUIState,
//         eventListener: (ForeCastListAdapter.ItemEvent) -> Unit
//     ) {
//         // listItemWeatherBinding.day.text = weatherListItemUIState.day
//         listItemWeatherBinding.weather.text = weatherElementUIState.weather
//         listItemWeatherBinding.minTemp.text = "Min temp: ${weatherElementUIState.minTemp}℃"
//         listItemWeatherBinding.maxTemp.text = "Max temp: ${weatherElementUIState.maxTemp}℃"
//
//
//         listItemWeatherBinding.root.setOnClickListener {
//             eventListener(ForeCastListAdapter.ItemEvent.Clicked(weatherElementUIState))
//         }
//     }
//
// }