package com.hrudhaykanth116.tv.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.AppCircularImage
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState

@Composable
fun TvSearchItemUI(
    state: SearchScreenItemUIState,
    onAdd: () -> Unit
) {

    AppCard(
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.DEFAULT_PADDING)
        ) {

            AppCircularImage(
                image = state.image,
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
            }

            HorizontalSpacer()

            if(state.isMyTvList){
                AppIcon(imageHolder = com.hrudhaykanth116.core.R.drawable.ic_check.toImageHolder(), modifier = Modifier.size(40.dp), tint = Color.Green)
            }else{
                AppClickableIcon(imageHolder = com.hrudhaykanth116.core.R.drawable.ic_add.toImageHolder(), modifier = Modifier.size(40.dp), onClick = onAdd, iconColor = Color.Green)
            }
        }

    }

}