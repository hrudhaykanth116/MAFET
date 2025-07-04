package com.hrudhaykanth116.todo.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppDropDown
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppInputText
import com.hrudhaykanth116.core.ui.components.AppSlider
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.TextFieldData
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.todo.ui.models.createtodo.CreateOrUpdateTodoUIState
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityField(
    value: Int,
    modifier: Modifier = Modifier,
    onPriorityChanged: (Int) -> Unit = {},
) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Priority: $value")
        HorizontalSpacer(width = Dimens.DEFAULT_PADDING)

        AppSlider(
            value.toFloat(),
            textToShow = value.toString(),
            onValueChange = {
                onPriorityChanged(floor(it).toInt())
            },
            steps = 3,
            valueRange = 1f..5f,
        )

        // AppInputText(
        //     textFieldData = TextFieldData(
        //         inputValue = TextFieldValue(
        //             text = value
        //         ),
        //         maxLines = 1,
        //         maxCharacters = 1,
        //     ),
        //     modifier = Modifier.width(50.dp),
        //     readOnly = true,
        //     onInputChange = onPriorityChanged
        // )
        // HorizontalSpacer()
        // Box() {
        //     AppClickableIcon(
        //         imageHolder = R.drawable.ic_expand_arrow.toImageHolder(),
        //         onClick = {
        //             shouldShowDropDown = !shouldShowDropDown
        //         })
        //     DropdownMenu(
        //         expanded = shouldShowDropDown,
        //         onDismissRequest = { shouldShowDropDown = !shouldShowDropDown }) {
        //         Column(
        //             modifier = Modifier
        //                 .background(color = Color.Red)
        //                 .width(100.dp)
        //         ) {
        //             VerticalSpacer(height = 10.dp)
        //             for (index in 1..5) {
        //                 Text(
        //                     text = "$index",
        //                     modifier = Modifier
        //                         .fillMaxWidth()
        //                         .background(Color.Green)
        //                         .clickable {
        //                             shouldShowDropDown = !shouldShowDropDown
        //                         }
        //                 )
        //                 VerticalSpacer(height = 10.dp)
        //             }
        //             VerticalSpacer(height = 10.dp)
        //         }
        //
        //     }
        // }
    }
}

@Preview
@Composable
private fun PriorityFieldPreview(){
    PriorityField(
        value = 1,
        onPriorityChanged = {}
    )
}