package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface,
        ),
        content = content,
    )
}

@MyPreview
@Composable
private fun AppCardPreview() {
    CenteredColumn {
        AppCard(
            modifier = Modifier.fillMaxWidth().requiredHeight(80.dp).padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier.fillMaxWidth().requiredHeight(80.dp).padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier.fillMaxWidth().requiredHeight(80.dp).padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
        AppCard(
            modifier = Modifier.fillMaxWidth().requiredHeight(80.dp).padding(8.dp),
        ) {
            Text(text = "Card preview", modifier = Modifier.padding(20.dp))
        }
    }

}