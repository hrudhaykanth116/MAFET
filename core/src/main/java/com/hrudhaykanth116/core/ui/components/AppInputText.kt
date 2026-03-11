package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.functions.TextFieldChangedHandler
import com.hrudhaykanth116.core.ui.components.inputtexts.AppPwdTextField
import com.hrudhaykanth116.core.ui.components.inputtexts.AppTextField
import com.hrudhaykanth116.core.ui.models.InputType
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
fun AppInputText(
    modifier: Modifier = Modifier,
    textFieldData: TextFieldData = TextFieldData(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    onInputChange: TextFieldChangedHandler = {},
) {

    Column(
        modifier = modifier,
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
                AppPwdTextField(
                    textFieldData, modifier = Modifier.fillMaxWidth(), onInputChange,
                )
            }

            is InputType.RegularInputType -> {
                AppTextField(
                    textFieldData,
                    modifier = Modifier.fillMaxWidth(),
                    onInputChange = onInputChange,
                    enabled = enabled,
                    singleLine = singleLine
                )
            }

            InputType.EmailInputType -> {
                AppTextField(
                    textFieldData, onInputChange = onInputChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = enabled,
                    readOnly = readOnly,
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

@AppPreview
@Composable
private fun AppInputTextPreview() {
    AppPreviewContainer {
        CenteredColumn {
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter title for the task.",
                    inputValue = TextFieldValue("asf"),
                    error = null
                ),
                singleLine = true,
                enabled = true,
            )
        }
    }
}