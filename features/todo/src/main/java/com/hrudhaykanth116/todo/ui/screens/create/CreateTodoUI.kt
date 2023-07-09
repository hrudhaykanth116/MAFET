package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import androidx.compose.runtime.*


@Composable
fun CreateTodoUI(
    state: CreateOrUpdateTodoUIState,
    onTitleChanged: (TextFieldValue) -> Unit,
    onDescriptionChanged: (TextFieldValue) -> Unit,
    onCreateBtnClicked: () -> Unit,
) {

    Column() {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .weight(
                    weight = 1f,
                    fill = false
                ) // fill false enforces column to become smaller for other components
        ) {
            AppFormInputText(
                textFieldData = TextFieldData(
                    hint = "Enter title for the task.",
                    inputValue = state.todoUIModel.title,
                    error = state.titleError
                ),
                onInputChange = onTitleChanged
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppFormInputText(
                textFieldData = TextFieldData(
                    hint = "Enter description for the task.",
                    inputValue = state.todoUIModel.description
                ),
                onInputChange = onDescriptionChanged
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        AppFormButton(
            btnText = "Add Button",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onCreateBtnClicked
        )

    }

}