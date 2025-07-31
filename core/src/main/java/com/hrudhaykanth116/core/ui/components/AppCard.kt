package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    cornerPercent: Int = 25,
    borderStroke: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        ),
        shape = RoundedCornerShape(percent = cornerPercent),
        colors = CardDefaults.cardColors(
            contentColor = Color.Unspecified,
            containerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        ),
        content = content,
        border = borderStroke
    )
}

@MyPreview
@Composable
private fun AppCardPreview() {
    CenteredColumn {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
    }

}