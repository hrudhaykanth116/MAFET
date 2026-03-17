package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import org.koin.androidx.compose.koinViewModel
import com.hrudhaykanth116.core.common.utils.compose.modifier.screenBackground
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.tv.ui.TvUIDimens
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenCallbacks
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenUIState
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import com.hrudhaykanth116.tv.ui.screens.updatemytv.UpdateTvScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EntertainmentHomeScreenUI(
    state: EntertainmentHomeScreenUIState,
    entertainmentHomeScreenCallbacks: EntertainmentHomeScreenCallbacks,
) {
    val list = state.tvShows
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shouldShowScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 2 }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .screenBackground()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { entertainmentHomeScreenCallbacks.onAddNewClicked() },
                    modifier = Modifier.size(TvUIDimens.FabSize),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add show",
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            topBar = {
                AppToolbar(
                    text = "My Shows (${list?.size?.takeIf { it > 0 } ?: "0"})",
                    navigationIcon = {},
                )
            }
        ) { paddingValues ->
            if (list.isNullOrEmpty()) {
                EmptyStateContent(modifier = Modifier.padding(paddingValues))
            } else {
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(TvUIDimens.ListItemSpacing),
                        contentPadding = PaddingValues(
                            horizontal = TvUIDimens.SpacerXLarge,
                            vertical = TvUIDimens.SpacerLarge
                        ),
                    ) {
                        items(
                            items = list,
                            key = { item -> item.id }
                        ) { myTv: MyTvUIState ->
                            MyTvListItemUI(
                                state = myTv,
                                modifier = Modifier
                                    .animateItem(
                                        fadeInSpec = spring(stiffness = Spring.StiffnessLow),
                                        fadeOutSpec = spring(stiffness = Spring.StiffnessLow),
                                        placementSpec = spring(stiffness = Spring.StiffnessLow)
                                    )
                                    .clickable {
                                        entertainmentHomeScreenCallbacks.onTvListItemClicked(myTv)
                                    },
                                onDeleteClicked = {
                                    entertainmentHomeScreenCallbacks.onTvListItemDismissed(myTv.id)
                                }
                            )
                        }
                    }

                    state.updateTv?.let { myTvUIState ->
                        val data =
                            UpdateMyTvUIStateActual.UpdateTvData(
                                id = myTvUIState.id,
                                name = myTvUIState.name.getText(),
                                lastWatchedSeason = TextFieldValue(
                                    text = myTvUIState.lastWatchedSeason?.toString() ?: ""
                                ),
                                lastWatchedEpisode = TextFieldValue(
                                    text = myTvUIState.lastWatchedEpisode?.toString() ?: ""
                                ),
                                imgSource = myTvUIState.imgSource,
                                lastWatchedTime = myTvUIState.lastWatchedTime,
                                lastWatchedTimeUIText = TextFieldValue(text = myTvUIState.lastWatchedTimeUIText.getText()),
                            )

                        UpdateTvScreen(
                            onCancelled = {
                                entertainmentHomeScreenCallbacks.onUpdateTvCloseRequest()
                            },
                            updateMyTvViewModel = koinViewModel<UpdateMyTvViewModel>().apply {
                                setData(data)
                            },
                        )
                    }
                }
            }
        }

        // Scroll to top FAB
        AnimatedVisibility(
            visible = shouldShowScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 140.dp),
            enter = fadeIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + scaleIn(
                initialScale = 0.3f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
            exit = fadeOut(
                animationSpec = spring(stiffness = Spring.StiffnessHigh)
            ) + scaleOut(
                targetScale = 0.3f,
                animationSpec = spring(stiffness = Spring.StiffnessHigh)
            )
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.size(56.dp),
                containerColor = Color(0xFF3B82F6),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to top",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyStateContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(TvUIDimens.EmptyStatePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(TvUIDimens.EmptyStateIconSize)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Tv,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "No Shows Yet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(TvUIDimens.SpacerMedium))

        Text(
            text = "Start tracking your favorite TV shows\nTap the + button to add your first show",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun TvHomeScreenUIPreview() {
    EntertainmentHomeScreenUI(
        state = EntertainmentHomeScreenUIState(
            tvShows = listOf(
                MyTvUIState(
                    id = 1,
                    name = "Breaking Bad".toUIText(),
                    lastWatchedSeason = 5,
                    lastWatchedEpisode = 16,
                    lastWatchedSeasonEpisode = UIText.Text("S05E16"),
                    lastWatchedTimeUIText = UIText.Text("2 days ago"),
                    lastWatchedTime = 9953,
                    imgSource = null,
                ),
                MyTvUIState(
                    id = 2,
                    name = "The Office".toUIText(),
                    lastWatchedSeason = 3,
                    lastWatchedEpisode = 8,
                    lastWatchedSeasonEpisode = UIText.Text("S03E08"),
                    lastWatchedTimeUIText = UIText.Text("Yesterday"),
                    lastWatchedTime = 1234,
                    imgSource = null,
                )
            )
        ),
        entertainmentHomeScreenCallbacks = EntertainmentHomeScreenCallbacks(
            {}, {}, {}, {}
        )
    )
}

@Preview
@Composable
private fun TvHomeScreenUIEmptyPreview() {
    EntertainmentHomeScreenUI(
        state = EntertainmentHomeScreenUIState(tvShows = null),
        entertainmentHomeScreenCallbacks = EntertainmentHomeScreenCallbacks(
            {}, {}, {}, {}
        )
    )
}
