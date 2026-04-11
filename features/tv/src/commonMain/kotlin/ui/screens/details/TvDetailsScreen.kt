package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.hrudhaykanth116.core.ui.components.AppScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TvDetailsScreen(
    viewModel: TvDetailsViewModel = koinViewModel(),
    onBackClicked: () -> Unit = {},
    onNavigateToTvDetails: (Int) -> Unit = {},
) {

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TvDetailsScreenEffect.NavigateToTvDetails -> {
                    onNavigateToTvDetails(effect.id)
                }
                is TvDetailsScreenEffect.OpenVideoUrl -> {
                    uriHandler.openUri(effect.url)
                }
            }
        }
    }

    AppScreen(viewModel) { state ->
        TvDetailsScreenUI(
            state = state,
            modifier = Modifier,
            onBackClicked = { onBackClicked() },
            onBookMarkClicked = { id ->
                viewModel.processEvent(TvDetailsScreenEvent.OnAddClicked(id))
            },
            onTabSelected = { tabIndex ->
                viewModel.processEvent(TvDetailsScreenEvent.OnTabSelected(tabIndex))
            },
            onSimilarShowClicked = { id ->
                viewModel.processEvent(TvDetailsScreenEvent.OnSimilarShowClicked(id))
            },
            onVideoClicked = { key, site ->
                viewModel.processEvent(TvDetailsScreenEvent.OnVideoClicked(key, site))
            },
        )
    }
}
