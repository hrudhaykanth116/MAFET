package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppDropDown(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    shouldShowDropDown: Boolean = false,
    dropDown: @Composable () -> Unit,
) {



    Box(modifier = modifier) {
        content()
        if(shouldShowDropDown) dropDown()
    }

}