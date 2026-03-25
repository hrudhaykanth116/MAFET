package com.hrudhaykanth116.mafet.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.hrudhaykanth116.core.common.di.coreCommonModule
import com.hrudhaykanth116.core.network.di.networkModule
import com.hrudhaykanth116.core.ui.di.coreUIModule
import com.hrudhaykanth116.todo.di.todoModule
import com.hrudhaykanth116.todo.navigation.TodoNavigation
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(
            coreCommonModule,
            coreUIModule,
            networkModule,
            todoModule
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MAFET Desktop - Todo App",
            state = rememberWindowState(width = 1000.dp, height = 700.dp)
        ) {
            MaterialTheme {
                DesktopApp(
                    onBackClicked = ::exitApplication
                )
            }
        }
    }
}

@Composable
fun DesktopApp(
    onBackClicked: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TodoNavigation(
            onBackClicked = onBackClicked
        )
    }
}
