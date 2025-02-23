package com.hrudhaykanth116.core.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.data.models.toUIText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismissRequest: () -> Unit,
    dateTimeUtils: DateTimeUtils = DateTimeUtils()
) {

    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AppFormButton(btnText = "Confirm".toUIText()){
                onDateSelected(datePickerState.selectedDateMillis)
            }
        },
        dismissButton = {
            AppFormButton(btnText = "Cancel".toUIText()){
                onDismissRequest()
            }
        }
    ) {
        // AppText(uiText = "Date picker".toUIText())
        DatePicker(state = datePickerState)
    }

}