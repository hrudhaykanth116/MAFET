package com.hrudhaykanth116.core.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import com.hrudhaykanth116.core.ui.models.TextFieldData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateTimeField(
    modifier: Modifier = Modifier,
) {

    val timePickerState = rememberTimePickerState()

    var shouldShowTimePicker by remember {
        mutableStateOf(true)
    }

    Box {


        AppTextField(textFieldData = TextFieldData(), modifier = Modifier.clickable {
            shouldShowTimePicker = !shouldShowTimePicker
        })

        if(shouldShowTimePicker){
            TimePicker(
                state = timePickerState
            )
        }

    }




}