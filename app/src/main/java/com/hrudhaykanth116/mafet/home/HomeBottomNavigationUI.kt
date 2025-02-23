package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationUIState
import com.hrudhaykanth116.mafet.home.models.NavigationItemUIState

@Composable
fun HomeBottomNavigationUI(
    uiState: HomeBottomNavigationUIState,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {

    val navItems = uiState.list

    val newModifier = modifier
        .fillMaxWidth()
        // .padding(horizontal = Dimens.DEFAULT_PADDING.times(2))

    Card(
        modifier = newModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        ),
        shape = RoundedCornerShape(topStartPercent = 40, topEndPercent = 40),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            // disabledContentColor = MaterialTheme.colorScheme.surface,
            // disabledContainerColor = MaterialTheme.colorScheme.onSurface,
        ),
        border = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.DEFAULT_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            navItems.forEach { navigationItem ->
                AppBottomBarItem(
                    onNavItemSelected = onNavItemSelected,
                    navigationItem
                )
            }
        }
    }
}

@Composable
private fun AppBottomBarItem(
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit,
    navigationItem: NavigationItemUIState,
    modifier: Modifier = Modifier,
) {

    CenteredColumn(modifier = modifier
        .padding(Dimens.DEFAULT_PADDING)
        .clickable {
            onNavItemSelected(navigationItem.homeBottomNavigationItem)
        }
    ) {
        AppIcon(
            imageHolder = navigationItem.homeBottomNavigationItem.iconDrawable.toImageHolder(),
            modifier = Modifier.size(24.dp),
            tint = if (navigationItem.isSelected) Color.Blue else Color.White
        )
        // VerticalSpacer()
        // AppText(uiText = navigationItem.displayName.toUIText(), fontSize = 12.sp)

    }

}

@Preview
@Composable
fun HomeBottomNavigationUIPreview() {
    HomeBottomNavigationUI(
        uiState = HomeBottomNavigationUIState(),
        onNavItemSelected = {}
    )
}