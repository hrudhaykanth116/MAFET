package com.hrudhaykanth116.training.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.util.lerp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.log.COMPOSE_TAG
import com.hrudhaykanth116.core.ui.components.AppImage
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.training.data.SAMPLE_IMAGE_URL
import com.hrudhaykanth116.training.data.getRandomImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.random.Random

private const val PAGES_SIZE = 20
private const val PRE_LOAD_SIZE = 20
private const val FLING_SIZE = 3
val randomImageIdOffset = Random.nextInt(200, 250)


@OptIn(ExperimentalFoundationApi::class)
@MyPreview
@Composable
fun MyPagerContainer() {

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState()

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(FLING_SIZE)
    )

    val scrollAction: (Int) -> Unit = { page: Int ->
        coroutineScope.launch {
            pagerState.animateScrollToPage(page)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Logger.d(COMPOSE_TAG, "MyPagerContainer: currentPage: $page")
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            Logger.d(COMPOSE_TAG, "MyPagerContainer: settledPage: $page")
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.targetPage }.collect { page ->
            Logger.d(COMPOSE_TAG, "MyPagerContainer: targetPage: $page")
        }
    }


    CenteredColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            PAGES_SIZE,
            state = pagerState,
            // pageSize = PageSize.Fixed(220.dp),
            contentPadding = PaddingValues(horizontal = 40.dp),
            beyondBoundsPageCount = PRE_LOAD_SIZE,
            flingBehavior = fling,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) { page ->
            // CenteredText(
            //     "Page: $page",
            //     modifier = Modifier.background(color = Color.Cyan)
            // )
            AppImage(
                image = getRandomImage("${page + randomImageIdOffset}"),
                // image = SAMPLE_IMAGE_URL,
                modifier = Modifier
                    .fillMaxSize()
                    // .background(color = Color.Cyan)
                    // .graphicsLayer {
                    //     // Calculate the absolute offset for the current page from the
                    //     // scroll position. We use the absolute value which allows us to mirror
                    //     // any effects for both directions
                    //     val pageOffset = (
                    //             (pagerState.currentPage - page) + pagerState
                    //                 .currentPageOffsetFraction
                    //             ).absoluteValue
                    //
                    //     // We animate the alpha, between 50% and 100%
                    //     alpha = lerp(
                    //         start = 0.2f,
                    //         stop = 1f,
                    //         fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    //     )
                    // }
            )
            // Spacer(modifier = Modifier.fillMaxSize().background(color = Color.Red))
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                // .background(color = Color.LightGray)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Add 5 items
            items(PAGES_SIZE) { index ->
                Button(onClick = { scrollAction(index) }) {
                    Text(text = "$index")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(PAGES_SIZE) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)

                )
            }
        }
    }

}