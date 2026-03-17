package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.R as CoreR
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.tv.ui.TvColors
import com.hrudhaykanth116.tv.ui.TvUIDimens
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState

@Composable
fun MyTvListItemUI(
    state: MyTvUIState,
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit = {},
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDeleteClicked()
                true
            } else {
                false
            }
        },
        positionalThreshold = { distance -> distance * 0.5f }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            BackgroundContent(dismissState)
        },
        enableDismissFromStartToEnd = false,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded },
            shape = RoundedCornerShape(TvUIDimens.ListItemCornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = TvUIDimens.ListItemElevation),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(TvUIDimens.ListItemVerticalPadding)
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(TvUIDimens.SpacerLarge),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Show thumbnail
                    Surface(
                        modifier = Modifier.size(TvUIDimens.ThumbnailSize),
                        shape = RoundedCornerShape(TvUIDimens.ThumbnailCornerRadius),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        when (val imageHolder = state.imgSource) {
                            is com.hrudhaykanth116.core.ui.models.ImageHolder.Url -> {
                                AsyncImage(
                                    model = imageHolder.url,
                                    contentDescription = state.name.getText(),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            is com.hrudhaykanth116.core.ui.models.ImageHolder.LocalDrawableResource -> {
                                Icon(
                                    painter = androidx.compose.ui.res.painterResource(imageHolder.resId),
                                    contentDescription = state.name.getText(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            else -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.PlayCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp),
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    // Show info
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(TvUIDimens.SpacerSmall)
                    ) {
                        Text(
                            text = state.name.getText(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(TvUIDimens.SpacerMedium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Season & Episode chip
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            ) {
                                Text(
                                    text = state.lastWatchedSeasonEpisode.getText(),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            // Last watched date
                            Text(
                                text = state.lastWatchedTimeUIText.getText(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Expanded details section
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(
                        expandFrom = Alignment.Top,
                        clip = false,
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            delayMillis = 100,
                            easing = LinearEasing
                        )
                    ),
                    exit = shrinkVertically(
                        shrinkTowards = Alignment.Top,
                        clip = false,
                        animationSpec = tween(
                            durationMillis = 250,
                            easing = FastOutLinearInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 150,
                            easing = FastOutLinearInEasing
                        )
                    )
                ) {
                    Column(modifier = Modifier.padding(top = TvUIDimens.SpacerLarge)) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Season",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${state.lastWatchedSeason ?: "-"}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Episode",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${state.lastWatchedEpisode ?: "-"}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Last Watched",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = state.lastWatchedTimeUIText.getText(),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun BackgroundContent(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
            else -> Color.Transparent
        },
        label = "swipe_color"
    )
    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1f else 0.8f,
        label = "icon_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(TvUIDimens.ListItemCornerRadius))
            .background(color)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier.scale(scale)
        )
    }
}

@Preview
@Composable
fun MyTvListItemUIPreview() {
    MyTvListItemUI(
        MyTvUIState(
            id = 1,
            name = "Suits".toUIText(),
            lastWatchedSeasonEpisode = "S09E04".toUIText(),
            lastWatchedTime = 10000145,
            imgSource = CoreR.drawable.ic_tv.toImageHolder(),
            lastWatchedSeason = 5,
            lastWatchedEpisode = 6,
            lastWatchedTimeUIText = "10/Oct".toUIText(),
        )
    )
}