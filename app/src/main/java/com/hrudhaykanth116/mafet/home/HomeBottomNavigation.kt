package com.hrudhaykanth116.mafet.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationUIState

@Composable
fun HomeBottomNavigation(
    uiState: HomeBottomNavigationUIState,
    modifier: Modifier = Modifier,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit = {},
) {

    // TODO: Listen for navigation stack changes and update icon accordingly 
    HomeBottomNavigationUI(
        uiState = uiState,
        modifier = modifier,
        onNavItemSelected = onNavItemSelected
    )
    // MaterialHomeBottomNavigationUI(modifier, navItems, selectedIndex, onNavItemSelected)

}

@AppPreview
@Composable
private fun HomeBottomNavigationPreview() {
    AppPreviewContainer {
        HomeBottomNavigation(
            uiState = HomeBottomNavigationUIState(

            )
        )
    }
}
