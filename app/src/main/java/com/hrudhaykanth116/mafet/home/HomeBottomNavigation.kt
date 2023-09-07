package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem

@Composable
fun HomeBottomNavigation(
    navItems: Array<HomeBottomNavigationItem>,
    modifier: Modifier = Modifier,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit = {},
) {

    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        modifier = modifier,
    ) {
        navItems.forEachIndexed { index, navigationItem ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedIndex = index
                    onNavItemSelected(navigationItem)
                },
                icon = {
                    AppIcon(
                        imageHolder = navigationItem.iconDrawable.toImageHolder(),
                        modifier = Modifier.size(24.dp),
                        tint = if(isSelected) Color.Blue else Color.White
                    )
                },
                label = {
                    AppText(uiText = navigationItem.displayName.toUIText())
                }
            )
        }
    }

}

@MyPreview
@Composable
fun HomeBottomNavigationPreview() {
    HomeBottomNavigation(navItems = HomeBottomNavigationItem.values())
}