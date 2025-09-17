package com.hrudhaykanth116.training.examples

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.training.R

private val headerHeight = 250.dp
private val toolbarHeight = 56.dp

@Composable
fun CollapsingToolBar(
    modifier: Modifier = Modifier,
) {


    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFF161616))
    ) {
        Header(
            scroll, headerHeightPx
        )
        Body(scroll)

        val toolbarBottom by remember {
            mutableFloatStateOf(headerHeightPx - toolbarHeightPx)
        }

        val showToolbar by remember {
            derivedStateOf { scroll.value >= toolbarBottom }
        }

        AnimatedVisibility(
            visible = showToolbar,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Toolbar()
        }


        Title(
            scroll = scroll,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx
        )
    }

}


@Composable
private fun Header(
    scroll: ScrollState, headerHeightPx: Float
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = -1f / headerHeightPx * scroll.value + 1
            }
    ) {
        Image(
            painter = painterResource(id = com.hrudhaykanth116.core.R.drawable.img_newyork),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        )

        Box(
            Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAAE90F0F)),
                        startY = 3 * headerHeightPx / 4 // to wrap the title only
                    )
                )
        )
    }

    // Box(modifier = Modifier.fillMaxWidth()) {
    //     Image(
    //         painter = painterResource(id = com.hrudhaykanth116.core.R.drawable.img_newyork),
    //         contentDescription = "",
    //         contentScale = ContentScale.FillWidth
    //     )
    //
    //     Box(
    //         Modifier
    //             .matchParentSize()
    //             .background(
    //                 brush = Brush.verticalGradient(
    //                     colors = listOf(Color.Transparent, Color(0xAAE90F0F)),
    //                     startY = 3 * headerHeightPx / 4 // to wrap the title only
    //                 )
    //             )
    //     )
    // }

    // BoxWithConstraints(
    //     modifier = Modifier.fillMaxWidth()
    // ) {
    //     val imageHeight = maxHeight
    //
    //     Box(modifier = Modifier.height(imageHeight)) {
    //         Image(
    //             painter = painterResource(id = com.hrudhaykanth116.core.R.drawable.img_newyork),
    //             contentDescription = "",
    //             contentScale = ContentScale.FillWidth,
    //             modifier = Modifier
    //                 .fillMaxWidth()
    //                 .height(imageHeight)
    //         )
    //
    //         Box(
    //             modifier = Modifier
    //                 .matchParentSize()
    //                 .background(
    //                     brush = Brush.verticalGradient(
    //                         colors = listOf(Color.Transparent, Color(0xAA000000)),
    //                         startY = with(LocalDensity.current) { (imageHeight * 0.75f).toPx() }
    //                     )
    //                 )
    //         )
    //     }
    // }

}

@Composable
private fun Body(
    scrollState: ScrollState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(headerHeight))

        repeat(5) {
            Text(
                text = stringResource(R.string.newyork_information),
                style = TextStyle(
                    color = Color.White
                ),
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar() {
    TopAppBar(
        modifier = Modifier.background(
            brush = Brush.horizontalGradient(
                listOf(Color(0xff026586), Color(0xff032C45))
            )
        ),
        navigationIcon = {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        title = {},
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )
}

@Composable
private fun Title(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    modifier: Modifier = Modifier,
) {

    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text(
        text = "New York",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .graphicsLayer {
                val collapseRange: Float = headerHeightPx - toolbarHeightPx
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val titleYFirstInterpolatedPoint = lerp(
                    headerHeight - titleHeightPx.toDp(),
                    headerHeight / 2,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()

            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}

@AppPreview
@Composable
private fun CollapsingToolBarPreview() {
    CollapsingToolBar(

    )
}

