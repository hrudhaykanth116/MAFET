package com.hrudhaykanth116.mafet.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.constants.Dimens
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CenteredColumn
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
    val interactionSource = remember { MutableInteractionSource() }

    val isSelected = navigationItem.isSelected

    // Animated values
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "icon_scale"
    )

    val iconSize by animateDpAsState(
        targetValue = if (isSelected) 32.dp else 24.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "icon_size"
    )

    val iconTint by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        },
        animationSpec = tween(durationMillis = 300),
        label = "icon_tint"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 300),
        label = "background_color"
    )

    val pillWidth by animateDpAsState(
        targetValue = if (isSelected) 64.dp else 48.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pill_width"
    )

    Box(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .width(pillWidth)
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onNavItemSelected(navigationItem.homeBottomNavigationItem)
            },
        contentAlignment = Alignment.Center
    ) {
        CenteredColumn(
            modifier = Modifier
                .scale(scale)
                .graphicsLayer {
                    if (isSelected) {
                        rotationZ = 0f
                        translationY = -2f
                    }
                }
        ) {
            AppIcon(
                resource = navigationItem.homeBottomNavigationItem.iconDrawable,
                modifier = Modifier.size(iconSize),
                tint = iconTint
            )

            // Show label only when selected
            if (isSelected) {
                AppText(
                    uiText = navigationItem.homeBottomNavigationItem.displayName.toUIText(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Add a subtle glow effect for selected item
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun HomeBottomNavigationUIPreview() {
    MaterialTheme {
        HomeBottomNavigationUI(
            uiState = HomeBottomNavigationUIState(
                list = listOf(
                    NavigationItemUIState(HomeBottomNavigationItem.TODO, isSelected = false),
                    NavigationItemUIState(HomeBottomNavigationItem.WEATHER, isSelected = true),
                    NavigationItemUIState(HomeBottomNavigationItem.ENTERTAINMENT, isSelected = false),
                    NavigationItemUIState(HomeBottomNavigationItem.ACCOUNT, isSelected = false),
                )
            ),
            onNavItemSelected = {}
        )
    }
}