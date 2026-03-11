package com.hrudhaykanth116.core.ui.components.inputtexts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.models.TextFieldData

@Composable
internal fun AppPwdTextField(
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
        },
        singleLine = true,
    )
}

@AppPreview
@Composable
private fun AppPwdTextFieldPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppPwdTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("password123"),
                    hint = "Password",
                ),
                modifier = Modifier.fillMaxWidth(),
                onInputChange = {},
            )
        }
    }
}

@AppPreview
@Composable
private fun AppPwdTextFieldEmptyPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppPwdTextField(
                textFieldData = TextFieldData(
                    hint = "Enter password",
                ),
                modifier = Modifier.fillMaxWidth(),
                onInputChange = {},
            )
        }
    }
}

@AppPreview
@Composable
private fun AppPwdTextFieldWithErrorPreview() {
    AppPreviewContainer {
        _root_ide_package_.com.hrudhaykanth116.core.ui.components.CenteredColumn {
            AppPwdTextField(
                textFieldData = TextFieldData(
                    inputValue = TextFieldValue("weak"),
                    hint = "Password",
                    error = "Password must be at least 8 characters",
                ),
                modifier = Modifier.fillMaxWidth(),
                onInputChange = {},
            )
        }
    }
}
