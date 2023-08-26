package com.hrudhaykanth116.weather.ui.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hrudhaykanth116.weather.domain.models.WeatherListItemUIState

class ForeCastListAdapter(
    private val eventListener: (ItemEvent) -> Unit
) : ListAdapter<WeatherListItemUIState, WeatherListItemViewHolder>(
    DIFF_CALL_BACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListItemViewHolder {
        return WeatherListItemViewHolder(
            ListItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherListItemViewHolder, position: Int) {
        holder.bind(getItem(position), eventListener)
    }

    companion object {

        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<WeatherListItemUIState>() {
            override fun areItemsTheSame(
                oldItem: WeatherListItemUIState,
                newItem: WeatherListItemUIState
            ): Boolean {
                return oldItem.day == newItem.day
            }

            override fun areContentsTheSame(
                oldItem: WeatherListItemUIState,
                newItem: WeatherListItemUIState
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

    sealed interface ItemEvent{
        data class Clicked(val weatherListItemUIState: WeatherListItemUIState): ItemEvent
    }

}