package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.modifier.gradientBackground
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.AppRoundedIcon
import com.hrudhaykanth116.core.ui.components.FullScreenImageViewer
import com.hrudhaykanth116.core.ui.components.FancyChipsFlow
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.core.ui.platform.sdp
import com.hrudhaykanth116.core.ui.platform.ssp
import com.hrudhaykanth116.tv.ui.screens.details.tabs.AboutTabContent
import com.hrudhaykanth116.tv.ui.screens.details.tabs.MoreLikeThisTabContent
import com.hrudhaykanth116.tv.ui.screens.details.tabs.ReviewsTabContent
import kotlinx.coroutines.launch
import mafet.core_ui.generated.resources.Res
import mafet.core_ui.generated.resources.ic_back
import mafet.core_ui.generated.resources.ic_bookmark
import mafet.core_ui.generated.resources.ic_bookmark_filled
import mafet.core_ui.generated.resources.image_place_holder

private val TAB_TITLES = listOf("About", "Reviews", "More Like This")

@Composable
fun TvDetailsScreenUI(
    state: TvDetailsScreenUIState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onBookMarkClicked: (Int) -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onSimilarShowClicked: (Int) -> Unit = {},
    onVideoClicked: (key: String, site: String) -> Unit = { _, _ -> },
    onImageClick: (url: String) -> Unit = {},
    onCloseFullscreen: () -> Unit = {},
    onDownloadImage: (url: String) -> Unit = {},
) {
    val pagerState = rememberPagerState { TAB_TITLES.size }
    val coroutineScope = rememberCoroutineScope()

    // Pager swipe → notify ViewModel
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            onTabSelected(page)
        }
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // --- HEADER ---
            Box {
                if (state.backdropImage != null) {
                    AppImage(
                        imageSource = state.backdropImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.sdp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.sdp)
                        .gradientBackground(
                            listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color(0xFF000000),
                            )
                        )
                        .padding(horizontal = 8.sdp, vertical = 10.sdp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = state.title,
                        fontSize = 18.ssp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(8.sdp))
                    Row {
                        Text(text = state.dateRange, fontSize = 10.ssp, color = Color.White)
                        HorizontalSpacer(width = 1.sdp)
                        Text(text = " | ", fontSize = 10.ssp, color = Color.White)
                        HorizontalSpacer(width = 1.sdp)
                        Text(text = state.rating, color = Color.White, fontSize = 10.ssp)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .padding(horizontal = 8.sdp, vertical = 8.sdp),
            ) {
                if (state.genres.isNotEmpty()) {
                    FancyChipsFlow(items = state.genres)
                }
            }

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color(0xFF000000),
                contentColor = Color.White,
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Color.White,
                        )
                    }
                },
            ) {
                TAB_TITLES.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 11.ssp,
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                            )
                        },
                    )
                }
            }

            // --- HORIZONTAL PAGER ---
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF000000)),
            ) { page ->
                when (page) {
                    0 -> AboutTabContent(
                        overview = state.overview,
                        aboutState = state.aboutTabState,
                        mediaState = state.mediaTabState,
                        onVideoClicked = onVideoClicked,
                        onImageClick = onImageClick,
                    )
                    1 -> ReviewsTabContent(
                        state = state.reviewsTabState,
                    )
                    2 -> MoreLikeThisTabContent(
                        state = state.moreLikeThisTabState,
                        onSimilarShowClicked = onSimilarShowClicked,
                    )
                }
            }
        }

        // --- OVERLAY ICONS ---
        AppRoundedIcon(
            icon = Res.drawable.ic_back,
            tint = Color.White,
            iconSize = 30.sdp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = 10.sdp, x = 10.sdp)
                .clickable { onBackClicked() },
        )

        AppRoundedIcon(
            icon = if (state.isBookmarked) Res.drawable.ic_bookmark_filled else Res.drawable.ic_bookmark,
            tint = Color.White,
            iconSize = 30.sdp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 10.sdp, x = (-10).sdp)
                .clickable { onBookMarkClicked(state.id) },
        )

        if (state.fullscreenImageUrl != null) {
            FullScreenImageViewer(
                imageUrl = state.fullscreenImageUrl,
                onBack = onCloseFullscreen,
                onDownloadClick = { onDownloadImage(state.fullscreenImageUrl) },
                isDownloading = state.isDownloadingImage,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}


@AppPreview
@Composable
private fun TvDetailsScreenPreview() {
    val placeholderImage = ImageHolder.LocalDrawableResource(Res.drawable.image_place_holder)

    AppPreviewContainer {
        TvDetailsScreenUI(
            state = TvDetailsScreenUIState(
                id = 1396,
                title = "Breaking Bad",
                overview = "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given only two years to live, he decides to risk everything by entering the meth business to secure his family's future.",
                backdropImage = placeholderImage,
                dateRange = "2008-01-20 - 2013-09-29",
                rating = "8.9 / 10",
                genres = listOf("Drama", "Crime", "Action & Adventure"),
                networks = listOf(
                    NetworkUIState(name = "AMC", logo = placeholderImage),
                    NetworkUIState(name = "HBO", logo = null),
                ),
                aboutTabState = AboutTabUIState(
                    cast = listOf(
                        CastUIState(1, "Bryan Cranston", "Walter White", placeholderImage),
                        CastUIState(2, "Aaron Paul", "Jesse Pinkman", placeholderImage),
                        CastUIState(3, "Anna Gunn", "Skyler White", placeholderImage),
                    ),
                    creators = listOf(
                        CreatorUIState(1, "Vince Gilligan", placeholderImage),
                    ),
                    seasons = listOf(
                        SeasonUIState(1, "Season 1", 7, "2008-01-20", placeholderImage, 1),
                        SeasonUIState(2, "Season 2", 13, "2009-03-08", placeholderImage, 2),
                    ),
                    productionCompanies = listOf(
                        ProductionCompanyUIState("High Bridge Entertainment", placeholderImage),
                    ),
                    languages = listOf("en", "es"),
                    status = "Ended",
                    type = "Scripted",
                    numberOfEpisodes = 62,
                    numberOfSeasons = 5,
                ),
                moreLikeThisTabState = MoreLikeThisTabUIState(
                    similarShows = listOf(
                        SimilarShowUIState(1, "Better Call Saul", "8.7 / 10", placeholderImage),
                        SimilarShowUIState(2, "Ozark", "8.5 / 10", placeholderImage),
                        SimilarShowUIState(3, "Narcos", "8.2 / 10", placeholderImage),
                    ),
                ),
                mediaTabState = MediaTabUIState(
                    images = listOf(
                        MediaImageUIState(placeholderImage, originalUrl = ""),
                        MediaImageUIState(placeholderImage, originalUrl = ""),
                    ),
                    videos = listOf(
                        MediaVideoUIState("dummyKey", "Official Trailer", placeholderImage, "YouTube"),
                    ),
                ),
            ),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
