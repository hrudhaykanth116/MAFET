package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.mafet.home.models.HomeBottomNavigationItem

@Composable
fun MaterialHomeBottomNavigationUI(
    modifier: Modifier,
    navItems: Array<HomeBottomNavigationItem>,
    selectedIndex: Int,
    onNavItemSelected: (HomeBottomNavigationItem) -> Unit,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        navItems.forEachIndexed { index, navigationItem ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onNavItemSelected(navigationItem)
                },
                icon = {
                    AppIcon(
                        imageHolder = navigationItem.iconDrawable.toImageHolder(),
                        modifier = Modifier.size(24.dp),
                        // tint = if(isSelected) Color.Blue else Color.White
                    )
                },
                label = {
                    AppText(uiText = navigationItem.displayName.toUIText())
                },
                // colors = NavigationBarItemDefaults.colors().apply {
                //
                // }
            )
        }
    }
}