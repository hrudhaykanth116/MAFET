package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText
import com.hrudhaykanth116.core.ui.components.AppUIState
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.utils.Logger
import com.hrudhaykanth116.todo.domain.model.create.CreateTodoUIState
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateTodoEvent

private const val TAG = "CreateTodoListScreen"

@Composable
fun CreateTodoListScreen(
    viewModel: CreateTodoListViewModel = hiltViewModel(),
    onCreated: () -> Unit,
) {
    Logger.d(TAG, "CreateTodoListScreen: ")

    val state: UIState<CreateTodoUIState> by viewModel.stateFlow.collectAsStateWithLifecycle()

    AppUIState(state = state) { createTodoUIState: CreateTodoUIState ->

        if (createTodoUIState.isSubmitted) {
            onCreated()
        } else {
            CreateTodoUI(
                state = createTodoUIState,
                onTitleChanged = {
                    viewModel.processEvent(CreateTodoEvent.TitleChanged(it))
                },
                onDescriptionChanged = {
                    viewModel.processEvent(CreateTodoEvent.DescriptionChanged(it))
                },
                onCreateBtnClicked = {
                    viewModel.processEvent(CreateTodoEvent.Create)
                }
            )
        }
    }
}

@Composable
private fun CreateTodoUI(
    state: CreateTodoUIState,
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
                    inputValue = state.title
                ),
                onInputChange = onTitleChanged
            )
            Spacer(modifier = Modifier.height(4.dp))
            AppFormInputText(
                textFieldData = TextFieldData(
                    hint = "Enter description for the task.",
                    inputValue = state.description
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