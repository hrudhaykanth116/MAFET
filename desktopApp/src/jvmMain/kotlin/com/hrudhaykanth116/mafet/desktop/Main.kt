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
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MAFET Desktop - KMP Test",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        MaterialTheme {
            DesktopApp()
        }
    }
}

@Composable
fun DesktopApp() {
    var counter by remember { mutableStateOf(0) }
    var uuid by remember { mutableStateOf("") }
    var currentTime by remember { mutableStateOf("") }

    val uniqueIdGenerator = remember { UniqueIdGenerator() }
    val dateTimeUtils = remember { DateTimeUtils() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MAFET Desktop - KMP Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Counter: $counter",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    counter++
                }
            ) {
                Text("Click Me!")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    uuid = uniqueIdGenerator.getUniqueId()
                }
            ) {
                Text("Generate UUID")
            }

            if (uuid.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "UUID: $uuid",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val millis = System.currentTimeMillis()
                    currentTime = dateTimeUtils.getFormattedDateTime(millis) ?: "Error"
                }
            ) {
                Text("Get Current Time")
            }

            if (currentTime.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Time: $currentTime",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
