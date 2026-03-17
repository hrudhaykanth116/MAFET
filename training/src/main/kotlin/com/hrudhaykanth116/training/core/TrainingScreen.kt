package com.hrudhaykanth116.training.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.shared.getPlatformName
import com.hrudhaykanth116.shared.Result

@Composable
fun TrainingScreen() {

    CenteredColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "KMP Test Screen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Platform: ${getPlatformName()}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Result.Success: ${Result.Success("KMP Works!").getOrNull()}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Result.Error: ${Result.Error("Sample error").getOrNull() ?: "null (expected)"}",
            style = MaterialTheme.typography.bodyMedium
        )
    }

}