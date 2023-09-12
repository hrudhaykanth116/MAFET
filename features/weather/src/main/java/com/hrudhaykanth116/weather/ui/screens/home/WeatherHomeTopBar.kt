package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.weather.R
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import com.hrudhaykanth116.core.R as CoreR

@Composable
fun WeatherHomeTopBar(
    location: String,
    weatherHomeScreenCallbacks: WeatherHomeScreenCallbacks,
    modifier: Modifier = Modifier,
) {

    // AppToolbar(
    //     text = location,
    //     navigationIcon = {},
    // ) {
    //     AppClickableIcon(
    //         imageHolder = CoreR.drawable.ic_menu_vertical.toImageHolder(),
    //         onClick = {
    //
    //         }
    //     )
    // }

    AppSearchBar(
        text = location,
        modifier = Modifier.padding(horizontal = Dimens.DEFAULT_PADDING),
        onTextChange = weatherHomeScreenCallbacks.onLocationTextChanged,
        onSearch = weatherHomeScreenCallbacks.search,
    )

}