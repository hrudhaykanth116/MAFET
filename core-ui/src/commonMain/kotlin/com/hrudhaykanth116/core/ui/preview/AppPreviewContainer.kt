package com.hrudhaykanth116.core.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.modifier.screenBackground

@Composable
fun AppPreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {


    val navigationEventDispatcher = remember { NavigationEventDispatcher() }

    CompositionLocalProvider(
        LocalNavigationEventDispatcherOwner provides object : androidx.navigationevent.NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = navigationEventDispatcher
        }
    ) {
        CenteredColumn(
            modifier = modifier.fillMaxSize().screenBackground()
        ){
            content()
        }
    }

}