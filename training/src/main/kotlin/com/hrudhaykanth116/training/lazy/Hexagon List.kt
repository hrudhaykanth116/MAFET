package com.hrudhaykanth116.training.lazy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@MyPreview
@Composable
fun HexagonList() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy((-92).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(50){
            Hexagon()
        }
    }
}

@Preview
@Composable
fun Hexagon() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.50f)
            .requiredHeight(200.dp)
            .clip(CutCornerShape(50.dp))
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Hexagon")
    }
}