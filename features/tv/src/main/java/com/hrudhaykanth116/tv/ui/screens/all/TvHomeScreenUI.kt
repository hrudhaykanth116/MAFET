package com.hrudhaykanth116.tv.ui.screens.all

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TvHomeScreenUI(
    uiState: TvHomeScreenUIState,
    processEvent: (TvHomeScreenEvent) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 20))
                    .clickable {
                        onNavigateToSearch()
                    }
                    .padding(10.sdp)
            ) {
                Text("Search for Tv show", modifier = Modifier.weight(1f))
                HorizontalSpacer()
                AppIcon(
                    resId = R.drawable.ic_search,
                )
            }
        }

        items(uiState.categories) { category ->
            Column {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(category.shows) { show ->
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${show.posterUrl}",
                                contentDescription = show.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}


@AppPreview
@Composable
private fun TvHomeScreenPreview() {

    AppPreviewContainer {
        TvHomeScreenUI(
            uiState = TvHomeScreenUIState(),
            processEvent = {},
            onNavigateToSearch = {},
            modifier = Modifier,
        )
    }

}
