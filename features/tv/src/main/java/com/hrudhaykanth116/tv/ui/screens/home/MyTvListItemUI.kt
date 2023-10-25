package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.AppCircularImage
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState

@Composable
fun MyTvListItemUI(
    state: MyTvUIState,
    modifier: Modifier = Modifier,
) {

    AppCard (
        modifier = modifier,
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
            .padding(Dimens.DEFAULT_PADDING)
        ) {

            AppCircularImage(
                image = state.imgSource,
                modifier = Modifier.size(50.dp)
            )

            HorizontalSpacer()

            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppText(
                    uiText = state.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                )
                AppText(
                    uiText = state.lastWatchedTime,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            HorizontalSpacer()

            AppText(uiText = state.lastWatchedSeasonEpisode)
        }

    }

}

@Preview
@Composable
fun MyTvListItemUIPreview() {
    // MyTvListItemUI(
    //     MyTvUIState(
    //         id = 1,
    //         name = "Suits".toUIText(),
    //         lastWatchedSeasonEpisode = "S09E04".toUIText(),
    //         lastWatchedTime = "20 June 2022".toUIText(),
    //         imgSource = CoreR.drawable.ic_tv.toImageHolder()
    //     )
    // )
}