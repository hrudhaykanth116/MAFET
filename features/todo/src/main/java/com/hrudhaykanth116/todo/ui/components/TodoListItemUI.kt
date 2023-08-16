package com.hrudhaykanth116.todo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.CircularImage
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel

import com.hrudhaykanth116.core.R as CoreR

// @Preview(showBackground = true, widthDp = 200, heightDp = 100)
@Composable
fun TodoListItemUI(
    toDoTaskUIState: ToDoTaskUIState,
    modifier: Modifier = Modifier,
    onRemoveClicked: () -> Unit = {}
) {
    Logger.d("hrudhay_logs", ": TodoListItemUI: ${toDoTaskUIState.data.title}")

    // TODO: May be this could be hoisted. Savable because, state retained on scroll or more.
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }


    val height by animateDpAsState(
        if (isExpanded) 120.dp else 60.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    AppCard(
        modifier = modifier
            .height(
                height.coerceAtLeast(60.dp) // Makes sure atleast 60dp is set.
            )
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp),
        ) {

            // Category image.
            CircularImage(
                image = CoreR.drawable.profile_icon
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppText(text = toDoTaskUIState.data.title.text.toUIText())
                AppText(text = toDoTaskUIState.data.description.text.toUIText())
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    isExpanded = !isExpanded
                }
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ArrowUpward else Icons.Default.ArrowDropDown,
                    contentDescription = "Expand",
                    tint = Color.Red,
                )
            }

            IconButton(onClick = onRemoveClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Expand",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoListItemUIPreview() {
    TodoListItemUI(
        toDoTaskUIState = ToDoTaskUIState(
            TodoUIModel(
                "1",
                TextFieldValue("Title"),
                TextFieldValue("Description"),
            )
        )
    )
}