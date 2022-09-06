package com.hrudhaykanth116.mafet.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.mafet.common.ui.components.AppFormButton
import com.hrudhaykanth116.mafet.common.ui.components.CenteredColumn

@Composable
fun HomeScreen(
    name: String?,
    onTodoClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    CenteredColumn {
        Text(text = "Hi $name")
        AppFormButton(onClick = onTodoClicked, btnText = "Todo")
        Spacer(modifier = Modifier.height(8.dp))
        AppFormButton(onClick = onLogoutClicked, btnText = "Logout")
    }
}