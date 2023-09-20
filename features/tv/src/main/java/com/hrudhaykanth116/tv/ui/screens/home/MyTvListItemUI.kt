package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.R as CoreR
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.search.MyTvUIState

@Composable
fun MyTvListItemUI(
    state: MyTvUIState,
    modifier: Modifier = Modifier,
) {

    AppCard (
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
            .padding(Dimens.DEFAULT_PADDING)
        ) {

            CircularImage(
                image = state.imgSource
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
                    uiText = state.lastWatched,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            HorizontalSpacer()

            AppText(uiText = state.currentEpisode)
        }

    }

}

@Preview
@Composable
fun MyTvListItemUIPreview() {
    MyTvListItemUI(
        MyTvUIState(
            id = "1",
            name = "Suits".toUIText(),
            currentEpisode = "S09E04".toUIText(),
            lastWatched = "20 June 2022".toUIText(),
            imgSource = CoreR.drawable.ic_tv.toImageHolder()
        )
    )
}