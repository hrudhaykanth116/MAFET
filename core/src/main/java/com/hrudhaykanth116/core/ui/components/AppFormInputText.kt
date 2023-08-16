package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.common.utils.compose.MyPreview

@MyPreview
@Composable
fun AppFormInputTextPreview() {
    AppFormInputText(
        textFieldData = TextFieldData(
            inputValue = TextFieldValue(),
            inputType = InputType.RegularInputType,
            label = "User name",
            hint = "Enter user name",
            error = "Field cannot be empty."
        )
    )
}

@Composable
fun AppFormInputText(
    modifier: Modifier = Modifier,
    textFieldData: TextFieldData = TextFieldData(),
    onInputChange: (TextFieldValue) -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // if (!label.isNullOrBlank()) {
        //     Text(
        //         text = label,
        //         // modifier = Modifier.padding(8.dp),
        //         style = MaterialTheme.typography.h5
        //     )
        // }

        when (textFieldData.inputType) {
            is InputType.PwdInputType -> {
                AppPwdTextField(textFieldData, modifier = modifier, onInputChange)
            }
            is InputType.RegularInputType -> {
                AppTextField(
                    textFieldData, modifier = modifier, onInputChange = onInputChange
                )
            }
            InputType.EmailInputType -> {
                AppTextField(
                    textFieldData, onInputChange = onInputChange,
                    modifier = modifier,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                    )
                )
            }
        }
        if (!textFieldData.error.isNullOrBlank()) {
            Text(
                text = textFieldData.error,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}

@Composable
private fun AppPwdTextField(
    textFieldData: TextFieldData,
    modifier: Modifier,
    onInputChange: (TextFieldValue) -> Unit,
) {

    // State hoisting is not done for now. See if needed.
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    AppTextField(
        textFieldData, onInputChange = onInputChange,
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    textFieldData: TextFieldData,
    modifier: Modifier = Modifier,
    onInputChange: (TextFieldValue) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldData.inputValue,
        isError = textFieldData.error?.isNotBlank() == true,
        onValueChange = onInputChange,
        // visualTransformation = PasswordVisualTransformation(),
        label = {
            textFieldData.hint?.let { Text(text = it) }
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}