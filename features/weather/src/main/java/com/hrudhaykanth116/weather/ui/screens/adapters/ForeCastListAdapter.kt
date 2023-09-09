package com.hrudhaykanth116.weather.ui.screens.adapters

// class ForeCastListAdapter(
//     private val eventListener: (ItemEvent) -> Unit
// ) : ListAdapter<WeatherElementUIState, WeatherListItemViewHolder>(
//     DIFF_CALL_BACK
// ) {
//
//     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListItemViewHolder {
//         return WeatherListItemViewHolder(
//             ListItemWeatherBinding.inflate(
//                 LayoutInflater.from(parent.context),
//                 parent,
//                 false
//             )
//         )
//     }
//
//     override fun onBindViewHolder(holder: WeatherListItemViewHolder, position: Int) {
//         holder.bind(getItem(position), eventListener)
//     }
//
//     companion object {
//
//         private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<WeatherElementUIState>() {
//             override fun areItemsTheSame(
//                 oldItem: WeatherElementUIState,
//                 newItem: WeatherElementUIState
//             ): Boolean {
//                 return oldItem.day == newItem.day
//             }
//
//             override fun areContentsTheSame(
//                 oldItem: WeatherElementUIState,
//                 newItem: WeatherElementUIState
//             ): Boolean {
//                 return oldItem == newItem
//             }
//         }
//
//     }
//
//     sealed interface ItemEvent{
//         data class Clicked(val weatherElementUIState: WeatherElementUIState): ItemEvent
//     }
//
// }