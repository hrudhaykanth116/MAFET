package com.hrudhaykanth116.journal

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.journal.navigation.JournalNavigation

@Composable
fun JournalScreen(
    onBackClicked: () -> Unit = {}
) {
    JournalNavigation(
        onBackClicked = onBackClicked
    )
}
