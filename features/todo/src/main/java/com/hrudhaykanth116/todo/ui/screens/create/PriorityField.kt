package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppDropDown
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState

@Composable
fun PriorityField(
    state: CreateOrUpdateTodoUIState,
    modifier: Modifier = Modifier,
) {

    var shouldShowDropDown by remember {
        mutableStateOf(false)
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Priority:")
        HorizontalSpacer(width = 10.dp)
        AppInputText(
            textFieldData = TextFieldData(
                inputValue = TextFieldValue(
                    text = "${state.todoUIModel.priority}"
                )
            ),
            modifier = Modifier.width(50.dp),
            readOnly = true
        )
        HorizontalSpacer(width = 10.dp)
        Box() {
            AppClickableIcon(
                imageHolder = R.drawable.ic_expand_arrow.toImageHolder(),
                onClick = {
                    shouldShowDropDown = !shouldShowDropDown
                })
            DropdownMenu(
                expanded = shouldShowDropDown,
                onDismissRequest = { shouldShowDropDown = !shouldShowDropDown }) {
                Column(
                    modifier = Modifier
                        .background(color = Color.Red)
                        .width(100.dp)
                ) {
                    VerticalSpacer(height = 10.dp)
                    for (index in 1..5) {
                        Text(
                            text = "$index",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Green)
                                .clickable {
                                    shouldShowDropDown = !shouldShowDropDown
                                }
                        )
                        VerticalSpacer(height = 10.dp)
                    }
                    VerticalSpacer(height = 10.dp)
                }

            }
        }
    }
}