package com.hrudhaykanth116.weather.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppSearchBar
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.weather.domain.models.WeatherHomeScreenCallbacks
import ir.kaaveh.sdpcompose.sdp

@Composable
fun WeatherHomeTopBar(
    location: String,
    isSearchActive: Boolean,
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

    Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        AppSearchBar(
            text = location,
            modifier = Modifier,
            onTextChange = weatherHomeScreenCallbacks.onLocationTextChanged,
            onSearch = weatherHomeScreenCallbacks.search,
            onCancelled = weatherHomeScreenCallbacks.onSearchCancelled,
            expanded = isSearchActive,
            onExpandedChange = weatherHomeScreenCallbacks.onExpandedChange,
            placeHolderText = "Enter city name"
        )
        if(!isSearchActive){
            AppClickableIcon(
                resId = com.hrudhaykanth116.core.R.drawable.ic_gps,
                onClick = weatherHomeScreenCallbacks.onGpsIconClicked
            )
        }
    }

}

@AppPreview
@Composable
private fun WeatherHomeTopBarPreview() {
    AppPreviewContainer {
        CenteredColumn {
            WeatherHomeTopBar(
                location = "Bengaluru",
                isSearchActive = false,
                weatherHomeScreenCallbacks = WeatherHomeScreenCallbacks(),
                modifier = Modifier.padding(Dimens.DEFAULT_PADDING)
            )
        }
    }
}