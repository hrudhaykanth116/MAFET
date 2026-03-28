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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.platform.ssp
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_back
import mafet.core_ui.generated.resources.ic_search

@Composable
fun TvHomeScreenUI(
    uiState: TvHomeScreenUIState,
    processEvent: (TvHomeScreenEvent) -> Unit,
    onNavigateToSearch: () -> Unit,
    onBackClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onNavigateToViewAll: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .screenBackground(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AppClickableIcon(
                    resource = Res.drawable.ic_back,
                    onClick = onBackClick,
                    iconColor = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .clickable { onNavigateToSearch() }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AppIcon(
                        resource = Res.drawable.ic_search,
                        tint = Color.White
                    )
                    HorizontalSpacer()
                    Text(
                        "Search for Tv show",
                        modifier = Modifier.weight(1f),
                        fontSize = 12.ssp,
                        color = Color.White
                    )
                }
            }
        }

        items(uiState.categories) { category ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Blue,
                        modifier = Modifier.clickable { onNavigateToViewAll(category.category.routeParam) }
                    )
                }
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
                                .clickable {
                                    onItemClick(show.id)
                                }
                        ) {
                            AppImage(
                                imageSource = show.posterImage,
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
private fun TvHomeScreenPreview(
    @PreviewParameter(TvHomeScreenPreviewParameterProvider::class) uiState: TvHomeScreenUIState
) {
    AppPreviewContainer {
        TvHomeScreenUI(
            uiState = uiState,
            processEvent = {},
            onNavigateToSearch = {},
            onItemClick = {},
            onNavigateToViewAll = {},
            modifier = Modifier,
            onBackClick = {}
        )
    }
}
