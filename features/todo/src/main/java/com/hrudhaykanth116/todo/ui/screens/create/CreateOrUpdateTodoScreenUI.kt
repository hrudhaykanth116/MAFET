package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState


@Composable
fun CreateOrUpdateTodoScreenUI(
    state: CreateOrUpdateTodoUIState,
    onTitleChanged: (TextFieldValue) -> Unit,
    onDescriptionChanged: (TextFieldValue) -> Unit,
    onCategoryChanged: (TextFieldValue) -> Unit,
    onCreateBtnClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                // .verticalScroll(rememberScrollState())
                .weight(
                    weight = 1f,
                )
        ) {
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter title for the task.",
                    inputValue = state.todoUIModel.title,
                    error = state.titleError
                ),
                onInputChange = onTitleChanged
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter description for the task.",
                    inputValue = state.todoUIModel.description
                ),
                onInputChange = onDescriptionChanged
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppInputText(
                textFieldData = TextFieldData(
                    hint = "Enter Category for the task.",
                    inputValue = state.todoUIModel.category
                ),
                onInputChange = onCategoryChanged
            )
            // DropdownMenu(
            //
            // )
            Spacer(modifier = Modifier.height(4.dp))
        }

        AppFormButton(
            btnText = "Submit",
            modifier = Modifier.align(Alignment.End),
            onClick = onCreateBtnClicked
        )

    }

}

@MyPreview
@Composable
fun CreateOrUpdateTodoScreenUIPreview() {
    CreateOrUpdateTodoScreenUI(
        state = CreateOrUpdateTodoUIState(),
        onTitleChanged = {},
        onDescriptionChanged = {},
        onCategoryChanged = {},
        onCreateBtnClicked = {},
    )
}