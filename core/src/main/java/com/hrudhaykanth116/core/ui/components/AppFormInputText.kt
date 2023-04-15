package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.models.InputType


@Preview(showSystemUi = true)
@Composable
fun AppFormInputText(
    inputValue: TextFieldValue = TextFieldValue(),
    label: String? = "Label",
    hint: String? = "Hint",
    isError: Boolean = false,
    error: String? = null,
    inputType: InputType = InputType.RegularInputType,
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

        when (inputType) {
            is InputType.PwdInputType -> {
                AppPwdTextField(inputValue, label, isError, onInputChange)
            }
            InputType.RegularInputType -> {
                AppTextField(
                    inputValue, label = label, isError = isError, onInputChange = onInputChange
                )
            }
            InputType.EmailInputType -> {
                AppTextField(
                    inputValue, label = label, isError = isError, onInputChange = onInputChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    )
                )
            }
        }

    }

}

@Composable
private fun AppPwdTextField(
    inputValue: TextFieldValue,
    label: String?,
    isError: Boolean,
    onInputChange: (TextFieldValue) -> Unit,
) {

    // State hoisting is not done for now. See if needed.
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    AppTextField(
        inputValue, label = label, isError = isError, onInputChange = onInputChange,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }
    )
}

@Preview
@Composable
fun AppTextField(
    inputValue: TextFieldValue = TextFieldValue(),
    label: String? = null,
    isError: Boolean = false,
    error: String? = null,
    onInputChange: (TextFieldValue) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = inputValue,
        isError = isError,
        onValueChange = onInputChange,
        // visualTransformation = PasswordVisualTransformation(),
        label = {
            label?.let { Text(text = it) }
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}