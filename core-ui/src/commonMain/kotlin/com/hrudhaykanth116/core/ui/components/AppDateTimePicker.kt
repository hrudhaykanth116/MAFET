package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateTimePicker(
    onDateTimeSelected: (Long) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    // datePickerState.selectedDateMillis?.let { dateMillis ->
                    //     val calendar = Calendar.getInstance().apply {
                    //         timeInMillis = dateMillis
                    //     }
                    //
                    //     TimePickerDialog(
                    //         context,
                    //         { _, hour, minute ->
                    //             calendar.set(Calendar.HOUR_OF_DAY, hour)
                    //             calendar.set(Calendar.MINUTE, minute)
                    //             calendar.set(Calendar.SECOND, 0)
                    //             calendar.set(Calendar.MILLISECOND, 0)
                    //             onDateTimeSelected(calendar.timeInMillis)
                    //         },
                    //         calendar.get(Calendar.HOUR_OF_DAY),
                    //         calendar.get(Calendar.MINUTE),
                    //         true
                    //     ).show()
                    // }
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}
