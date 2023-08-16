package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hrudhaykanth116.core.common.utils.compose.MyPreview

@OptIn(ExperimentalMaterial3Api::class)
@MyPreview
@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    text: String = "Test toolbar",
) {

    TopAppBar(
        title = { Text(text = "Register", color = Color.White) },
    )
/*
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .background(
                color = Color.Gray
            ),
        fontSize = 20.sp,
        color = Color.White,
    )*/

}