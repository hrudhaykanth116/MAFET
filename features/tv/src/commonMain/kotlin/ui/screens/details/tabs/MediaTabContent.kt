package com.hrudhaykanth116.tv.ui.screens.details.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.tv.ui.screens.details.MediaImageUIState
import com.hrudhaykanth116.tv.ui.screens.details.MediaTabUIState
import com.hrudhaykanth116.tv.ui.screens.details.MediaVideoUIState

@Composable
fun MediaTabContent(
    state: MediaTabUIState?,
    onVideoClicked: (key: String, site: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state == null) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    val hasContent = state.videos.isNotEmpty() || state.images.isNotEmpty()
    if (!hasContent) {
        CenteredColumn(modifier = modifier.fillMaxSize()) {
            Text(
                text = "No media available",
                color = Color.Gray,
                fontSize = 12.ssp,
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.sdp),
    ) {
        // Videos section
        if (state.videos.isNotEmpty()) {
            item {
                Text(
                    text = "Videos",
                    color = Color.White,
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.sdp, vertical = 6.sdp),
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.sdp),
                    contentPadding = PaddingValues(horizontal = 12.sdp),
                ) {
                    items(state.videos) { video ->
                        VideoItem(
                            video = video,
                            onClick = { onVideoClicked(video.key, video.site) },
                        )
                    }
                }
            }
            item {
                VerticalSpacer(height = 16.sdp)
            }
        }

        // Images section
        if (state.images.isNotEmpty()) {
            item {
                Text(
                    text = "Images",
                    color = Color.White,
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.sdp, vertical = 6.sdp),
                )
            }
            items(state.images) { image ->
                ImageItem(image)
            }
        }
    }
}

@Composable
private fun VideoItem(
    video: MediaVideoUIState,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(200.sdp)
            .clickable(onClick = onClick),
    ) {
        Box {
            AppImage(
                imageSource = video.thumbnail,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.sdp)
                    .clip(RoundedCornerShape(8.sdp)),
                contentScale = ContentScale.Crop,
            )
            // Play icon overlay
            Surface(
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(36.sdp)
                    .align(Alignment.Center),
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.padding(6.sdp),
                )
            }
        }
        VerticalSpacer(height = 4.sdp)
        Text(
            text = video.name,
            color = Color.White,
            fontSize = 9.ssp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun ImageItem(image: MediaImageUIState) {
    AppImage(
        imageSource = image.image,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.sdp, vertical = 4.sdp)
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.sdp)),
        contentScale = ContentScale.Crop,
    )
}
