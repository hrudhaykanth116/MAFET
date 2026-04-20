package com.hrudhaykanth116.tv.ui.screens.details.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.tv.ui.screens.details.MoreLikeThisTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.SimilarShowUIState

@Composable
fun MoreLikeThisTabContent(
    state: MoreLikeThisTabUIState?,
    onSimilarShowClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state == null) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    if (state.similarShows.isEmpty()) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            Text(
                text = "No similar shows found",
                color = Color.Gray,
                fontSize = 12.ssp,
            )
        }
        return
    }

    val spacing = 8.dp
    val targetItemWidth = 140.dp

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val effectiveColumns = (
            ((maxWidth + spacing) / (targetItemWidth + spacing)).toInt()
        ).coerceIn(2, 6)

        LazyVerticalGrid(
            columns = GridCells.Fixed(effectiveColumns),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.sdp),
            verticalArrangement = Arrangement.spacedBy(10.sdp),
            horizontalArrangement = Arrangement.spacedBy(8.sdp),
        ) {
            items(state.similarShows) { show ->
                SimilarShowItem(
                    show = show,
                    onClick = { onSimilarShowClicked(show.id) },
                )
            }
        }
    }
}

@Composable
private fun SimilarShowItem(
    show: SimilarShowUIState,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        if (show.posterImage != null) {
            AppImage(
                imageSource = show.posterImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.sdp)),
                contentScale = ContentScale.Crop,
            )
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = show.name,
            color = Color.White,
            fontSize = 10.ssp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = show.rating,
            color = Color.Gray,
            fontSize = 8.ssp,
        )
    }
}
