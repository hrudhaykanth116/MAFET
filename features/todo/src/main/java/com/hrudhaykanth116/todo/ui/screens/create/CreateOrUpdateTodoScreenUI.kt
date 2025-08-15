package com.hrudhaykanth116.todo.ui.screens.create

import AppDateTimePicker
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.resources.Dimens.DEFAULT_PADDING
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.components.AppToolbar
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState

@Composable
fun CreateOrUpdateTodoScreenUI(
    state: CreateOrUpdateTodoUIState,
    modifier: Modifier = Modifier,
    onTitleChanged: (TextFieldValue) -> Unit = {},
    onDescriptionChanged: (TextFieldValue) -> Unit = {},
    onCategoryChanged: (TextFieldValue) -> Unit = {},
    onCreateBtnClicked: () -> Unit = {},
    onPriorityChanged: (Int) -> Unit = {},
    onTargetTimeChanged: (Long) -> Unit = {},
    onTargetTimeDateTimePickerCloseRequest: () -> Unit = {},
    onTargetFieldClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = DEFAULT_PADDING)
    ) {

        AppToolbar(
            text = "Create Todo task",
            onBackClicked = onBackClicked
        )
        VerticalSpacer(height = 10.dp)
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(
                    weight = 1f,
                )
                .padding(horizontal = DEFAULT_PADDING)
        ) {
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter title for the task.",
                    inputValue = state.todoUIModel.title,
                    error = state.titleError
                ),
                onInputChange = onTitleChanged
            )
            Spacer(modifier = Modifier.height(Dimens.DEFAULT_PADDING))
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter description for the task.",
                    inputValue = state.todoUIModel.description
                ),
                onInputChange = onDescriptionChanged
            )
            Spacer(modifier = Modifier.height(Dimens.DEFAULT_PADDING))
            OutlinedTextField(
                value = state.todoUIModel.targetTime,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Black
                ),
                placeholder = { Text("Click to select Date Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onTargetFieldClicked()
                    },
                singleLine = true
            )
            if (state.showTargetTimePicker) {
                AppDateTimePicker(
                    onDateTimeSelected = {
                        onTargetTimeChanged(it)
                    },
                    onDismissRequest = {
                        onTargetTimeDateTimePickerCloseRequest()
                    }
                )
            }
            Spacer(modifier = Modifier.height(Dimens.DEFAULT_PADDING))
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter Category for the task.",
                    inputValue = state.todoUIModel.category
                ),
                onInputChange = onCategoryChanged
            )
            Spacer(modifier = Modifier.height(Dimens.DEFAULT_PADDING))
            Row {
                PriorityField(
                    state.todoUIModel.priority,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onPriorityChanged = onPriorityChanged
                )
                // CreateTodoDateTimeField(
                //     value = state.todoUIModel.targetTime,
                //     modifier = Modifier
                //         .fillMaxWidth()
                //         .weight(1f),
                //     onValueChange = {
                //
                //     }
                // )
            }
            // Spacer(modifier = Modifier.height(Dimens.DEFAULT_PADDING))
            // AppInputText(
            //     textFieldData = TextFieldData(
            //         hint = "Enter Category for the task.",
            //         inputValue = state.todoUIModel.category
            //     ),
            //     onInputChange = onCategoryChanged
            // )
        }

        AppFormButton(
            btnText = "Submit".toUIText(),
            modifier = Modifier.align(Alignment.End),
            onClick = onCreateBtnClicked
        )

    }

}

@MyPreview
@Composable
fun CreateOrUpdateTodoScreenUIPreview() {
    AppPreviewContainer {
        CreateOrUpdateTodoScreenUI(
            state = CreateOrUpdateTodoUIState(),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onCategoryChanged = {},
            onCreateBtnClicked = {},
        )
    }
}