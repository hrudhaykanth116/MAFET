package com.hrudhaykanth116.core.ui.components.inputtexts

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import ir.kaaveh.sdpcompose.ssp

// P4 Add max chars provision and max length accordingly.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    textFieldData: TextFieldData,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onInputChange: (TextFieldValue) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = textFieldData.inputValue,
        isError = textFieldData.error?.isNotBlank() == true,
        enabled = enabled,
        readOnly = readOnly,
        onValueChange = { fieldValue: TextFieldValue ->
            onInputChange(
                fieldValue.copy(
                    text = textFieldData.maxCharacters?.let { fieldValue.text.take(it) } ?: fieldValue.text
                )
            )
        },
        // visualTransformation = PasswordVisualTransformation(),
        label = {
            textFieldData.hint?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = if (enabled) Color(0xFF616161) else Color(0xFF9E9E9E),
                        fontSize = 14.ssp
                    )
                )
            }
        },
        textStyle = TextStyle(
            color = if (enabled) Color(0xFF212121) else Color(0xFF757575),
            fontSize = 16.ssp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFAFAFA),
            unfocusedContainerColor = Color(0xFFFAFAFA),
            disabledContainerColor = Color(0xFFF5F5F5),
            errorContainerColor = Color(0xFFFAFAFA),
            focusedBorderColor = Color(0xFF000000),
            unfocusedBorderColor = Color(0xFF000000),
            disabledBorderColor = Color(0xFF9E9E9E),
            errorBorderColor = Color(0xFFD32F2F),
            focusedLabelColor = Color(0xFF2962FF),
            unfocusedLabelColor = Color(0xFF616161),
            errorLabelColor = Color(0xFFD32F2F),
            cursorColor = Color(0xFF2962FF),
            disabledTextColor = Color(0xFF9E9E9E)
        ),
        maxLines = textFieldData.maxLines ?: Int.MAX_VALUE,
        minLines = textFieldData.minLines ?: 1,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        shape = RoundedCornerShape(30)
    )
}


@AppPreview
@Composable
private fun AppTextFieldPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("Sample text"),
                    hint = "Enter text here",
                ),
                enabled = true,
            )
        }
    }
}

@AppPreview
@Composable
private fun AppTextFieldWithErrorPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("Invalid input"),
                    hint = "Username",
                    error = "This field is required",
                ),
                enabled = true,
            )
        }
    }
}

@AppPreview
@Composable
private fun AppTextFieldDisabledPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("Disabled field"),
                    hint = "Disabled input",
                ),
                enabled = false,
            )
        }
    }
}

@AppPreview
@Composable
private fun AppTextFieldMultilinePreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("This is a multiline text field\nwith multiple lines"),
                    hint = "Enter description",
                    minLines = 3,
                    maxLines = 5,
                ),
                enabled = true,
            )
        }
    }
}

