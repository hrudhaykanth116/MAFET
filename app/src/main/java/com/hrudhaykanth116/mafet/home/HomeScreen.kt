package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.common.ui.components.AppButton
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn

@Composable
fun HomeScreen(
    name: String?,
    onTodoClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    CenteredColumn {
        Text(text = "Hi $name")
        AppButton(onClick = onTodoClicked, btnText = "Todo")
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(onClick = onLogoutClicked, btnText = "Logout")
    }
}