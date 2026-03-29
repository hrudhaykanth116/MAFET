package com.hrudhaykanth116.journal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrudhaykanth116.core.ui.components.CenteredColumn

@Composable
actual fun JournalScreen() {
    CenteredColumn(modifier = Modifier.fillMaxSize()) {
        Text("Journal feature coming soon to iOS")
    }
}
