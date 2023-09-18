package com.hrudhaykanth116.tv.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.tv.data.models.models.TvShowData

@Composable
fun TvShowListItems(
    nullableTvShows: List<TvShowData?>
) {

    // TODO: Change this
    val tvShows: List<TvShowData> = nullableTvShows.filterNotNull()

    LazyColumn(){
        items(tvShows){ tvShowData ->
            TvShowListItem(tvShowData)
        }
    }
    tvShows.forEach { tvShowData: TvShowData ->

    }

}

@Composable
fun TvShowListItem(
    tvShowData: TvShowData,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            // .clip(RoundedCornerShape(50))
            // .border(
            //     width = 20.dp, color = Color.LightGray
            // )
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(50))
            .padding(
                vertical = Dimens.DEFAULT_PADDING,
                horizontal = Dimens.DEFAULT_PADDING * 2,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            uiText = tvShowData.name?.toUIText(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalSpacer()
        AppText(uiText = tvShowData.popularity.toUIText("- -"), style = MaterialTheme.typography.titleMedium)
    }


}