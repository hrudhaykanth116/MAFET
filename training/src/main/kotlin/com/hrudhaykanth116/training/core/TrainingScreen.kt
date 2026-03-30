package com.hrudhaykanth116.training.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
fun TrainingScreen() {

    CenteredColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Training Screen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Training module placeholder",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

}