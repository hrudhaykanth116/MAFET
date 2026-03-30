package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
fun CreateTodoDateTimeField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (Long) -> Unit,
) {

    var shouldShowDropDown by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Priority:")
        HorizontalSpacer()
        AppInputText(
            textFieldData = TextFieldData(
                inputValue = value
            ),
            readOnly = true,
        )
    }

}