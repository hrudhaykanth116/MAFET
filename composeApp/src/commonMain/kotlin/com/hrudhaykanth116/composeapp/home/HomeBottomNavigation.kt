package com.hrudhaykanth116.composeapp.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.composeapp.home.models.HomeBottomNavigationUIState

@Composable
fun HomeBottomNavigation(
    uiState: HomeBottomNavigationUIState,
    modifier: Modifier = Modifier,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit = {},
) {

    HomeBottomNavigationUI(
        uiState = uiState,
        modifier = modifier,
        onNavItemSelected = onNavItemSelected
    )

}

@AppPreview
@Composable
private fun HomeBottomNavigationPreview() {
    AppPreviewContainer {
        HomeBottomNavigation(
            uiState = HomeBottomNavigationUIState(
                listOf()
            )
        )
    }
}
