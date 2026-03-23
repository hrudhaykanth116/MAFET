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
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.preview.AppPreview
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.platform.ssp

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
        placeholder = {
            textFieldData.hint?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = Color(0xFF9CA3AF),
                        fontSize = 15.ssp
                    )
                )
            }
        },
        textStyle = TextStyle(
            color = if (enabled) Color(0xFF111827) else Color(0xFF9CA3AF),
            fontSize = 15.ssp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color(0xFFF9FAFB),
            errorContainerColor = Color(0xFFFEF2F2),
            focusedBorderColor = Color(0xFF3B82F6),
            unfocusedBorderColor = Color(0xFFE5E7EB),
            disabledBorderColor = Color(0xFFF3F4F6),
            errorBorderColor = Color(0xFFEF4444),
            cursorColor = Color(0xFF3B82F6),
            disabledTextColor = Color(0xFF9CA3AF),
            errorCursorColor = Color(0xFFEF4444)
        ),
        maxLines = textFieldData.maxLines ?: Int.MAX_VALUE,
        minLines = textFieldData.minLines ?: 1,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        shape = RoundedCornerShape(12.dp)
    )
}


@AppPreview
@Composable
private fun AppTextFieldPreview() {
    AppPreviewContainer {
        CenteredColumn {
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
        CenteredColumn {
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
        CenteredColumn {
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
        CenteredColumn {
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

