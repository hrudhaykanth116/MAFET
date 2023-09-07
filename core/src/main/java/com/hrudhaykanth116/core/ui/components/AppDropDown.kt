package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.models.toImageHolder

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