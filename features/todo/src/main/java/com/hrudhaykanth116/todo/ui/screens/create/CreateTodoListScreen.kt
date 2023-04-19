package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppFormInputText

@Composable
fun CreateTodoListScreen(
    createTodoListViewModel: CreateTodoListViewModel = hiltViewModel()
) {



    Column() {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false) // fill false enforces column to become smaller for other components
        ) {
            // AppFormInputText(label = "Title", hint = "Enter title for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Description", hint = "Enter description for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
            // AppFormInputText(label = "Category", hint = "Enter category for the task.")
            // Spacer(modifier = Modifier.height(4.dp))
        }

        AppFormButton(
            btnText = "Add Button",
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            // onCreate(
            //     ToDoTask(
            //
            //     )
            // )
        }

    }


}