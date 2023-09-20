package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState

@Composable
fun TvSearchItemUI(
    state: SearchScreenItemUIState,
) {

    AppCard(
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.DEFAULT_PADDING)
        ) {

            CircularImage(
                image = state.image
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
            }
        }

    }

}