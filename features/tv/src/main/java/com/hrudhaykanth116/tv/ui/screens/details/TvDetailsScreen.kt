package com.hrudhaykanth116.tv.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.AppScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvDetailsScreen(
    viewModel: TvDetailsViewModel = koinViewModel(),
    onBackClicked: () -> Unit = {},
) {

    val onBackClicked = {
        onBackClicked()
    }

    val onBookMarkClicked = { id: Int ->
        viewModel.processEvent(TvDetailsScreenEvent.OnAddClicked(id))
    }



    AppScreen(viewModel) { state ->
        TvDetailsScreenUI(
            state = state,
            modifier = Modifier,
            onBackClicked = onBackClicked,
            onBookMarkClicked = onBookMarkClicked
        )
    }

}