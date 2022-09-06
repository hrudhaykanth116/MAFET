package com.hrudhaykanth116.mafet.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun AppFormInputText(
    inputValue: TextFieldValue = TextFieldValue(),
    label: String? = "Label",
    hint: String? = "Hint",
    isError: Boolean = false,
    onInputChange: (TextFieldValue) -> Unit = {}
) {

    Column() {

        // if (!label.isNullOrBlank()) {
        //     Text(
        //         text = label,
        //         // modifier = Modifier.padding(8.dp),
        //         style = MaterialTheme.typography.h5
        //     )
        // }

        AppTextField(inputValue, label = label, isError = isError, onInputChange = onInputChange)

    }

}

@Preview
@Composable
fun AppTextField(
    inputValue: TextFieldValue = TextFieldValue(),
    label: String? = null,
    isError: Boolean = false,
    onInputChange: (TextFieldValue) -> Unit = {}
) {
    OutlinedTextField(
        value = inputValue,
        isError = isError,
        onValueChange = onInputChange,
        // visualTransformation = PasswordVisualTransformation(),
        label = {
            label?.let { Text(text = it) }
        }
    )
}