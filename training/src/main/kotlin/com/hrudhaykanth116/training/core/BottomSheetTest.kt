package com.hrudhaykanth116.training.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.training.components.BottomSheetSample

@Composable
private fun BottomSheetTest() {
    var shouldShowBottomSheet by remember {
        mutableStateOf(false)
    }

    Column(Modifier.fillMaxSize()) {
        AppFormButton(
            btnText = "Toggle bottom sheet".toUIText(),
            onClick = {
                shouldShowBottomSheet = !shouldShowBottomSheet
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            CenteredColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Red)
            ) {

            }
            if (shouldShowBottomSheet) {
                BottomSheetSample {
                    shouldShowBottomSheet = false
                }
            }
        }
    }
}