package com.hrudhaykanth116.media.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.components.VideoPlayerScreen
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.media.domain.models.MediaItem
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.ui.components.ColorPaletteDisplay

@Composable
fun MediaDetailScreenUI(
    mediaItem: MediaItem,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onDownloadClick: (DownloadQuality) -> Unit,
    onPhotographerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDownloadMenu by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppToolbar(
                text = if (mediaItem.type == MediaType.VIDEOS) "Video" else "Photo",
                onBackClicked = onBackClick,
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }

                    Box {
                        IconButton(onClick = { showDownloadMenu = true }) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Download",
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = showDownloadMenu,
                            onDismissRequest = { showDownloadMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Original Quality", fontSize = 13.ssp) },
                                onClick = {
                                    onDownloadClick(DownloadQuality.ORIGINAL)
                                    showDownloadMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Medium Quality", fontSize = 13.ssp) },
                                onClick = {
                                    onDownloadClick(DownloadQuality.MEDIUM)
                                    showDownloadMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Small Quality", fontSize = 13.ssp) },
                                onClick = {
                                    onDownloadClick(DownloadQuality.SMALL)
                                    showDownloadMenu = false
                                }
                            )
                        }
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.sdp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.sdp),
                    shape = RoundedCornerShape(16.sdp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.sdp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Column {
                        if (mediaItem.type == MediaType.VIDEOS) {
                            VideoPlayerScreen(
                                videoUrl = mediaItem.originalUrl,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(500.sdp)
                                    .clip(RoundedCornerShape(topStart = 16.sdp, topEnd = 16.sdp))
                            )
                        } else {
                            AsyncImage(
                                model = mediaItem.originalUrl,
                                contentDescription = "Photo by ${mediaItem.photographer}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(500.sdp)
                                    .clip(RoundedCornerShape(topStart = 16.sdp, topEnd = 16.sdp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.sdp)
                        ) {
                            Text(
                                text = if (mediaItem.type == MediaType.VIDEOS) "Video by" else "Photo by",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6B7280),
                                fontSize = 11.ssp
                            )

                            Spacer(modifier = Modifier.height(4.sdp))

                            Text(
                                text = mediaItem.photographer,
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 18.ssp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            Spacer(modifier = Modifier.height(8.sdp))

                            Text(
                                text = "View on Pexels",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.ssp,
                                color = Color(0xFF3B82F6),
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable(onClick = onPhotographerClick)
                            )

                            Spacer(modifier = Modifier.height(20.sdp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Dimensions",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF6B7280),
                                        fontSize = 10.ssp
                                    )
                                    Spacer(modifier = Modifier.height(4.sdp))
                                    Text(
                                        text = "${mediaItem.width} × ${mediaItem.height}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 13.ssp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF111827)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(40.sdp)
                                        .background(Color(0xFFE5E7EB))
                                )

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Orientation",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF6B7280),
                                        fontSize = 10.ssp
                                    )
                                    Spacer(modifier = Modifier.height(4.sdp))
                                    Text(
                                        text = when (mediaItem.orientation) {
                                            com.hrudhaykanth116.media.domain.models.OrientationType.PORTRAIT -> "Portrait"
                                            com.hrudhaykanth116.media.domain.models.OrientationType.LANDSCAPE -> "Landscape"
                                            com.hrudhaykanth116.media.domain.models.OrientationType.SQUARE -> "Square"
                                            else -> "Unknown"
                                        },
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 13.ssp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF111827)
                                    )
                                }
                            }

                            mediaItem.avgColor?.let { color ->
                                Spacer(modifier = Modifier.height(20.sdp))

                                Text(
                                    text = "Dominant Color",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF6B7280),
                                    fontSize = 10.ssp
                                )

                                Spacer(modifier = Modifier.height(8.sdp))

                                ColorPaletteDisplay(dominantColor = color)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.sdp))
            }
        }
    }
}
