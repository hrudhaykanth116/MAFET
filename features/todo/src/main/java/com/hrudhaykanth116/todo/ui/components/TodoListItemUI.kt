package com.hrudhaykanth116.todo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.resources.Dimens
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppCard
import com.hrudhaykanth116.core.ui.components.AppClickableIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.models.ImageHolder
import com.hrudhaykanth116.todo.ui.models.ToDoTaskUIState
import com.hrudhaykanth116.todo.ui.models.TodoUIModel
import ir.kaaveh.sdpcompose.sdp

import com.hrudhaykanth116.core.R as CoreR

@Composable
fun TodoListItemUI(
    toDoTaskUIState: ToDoTaskUIState,
    modifier: Modifier = Modifier,
    onRemoveClicked: () -> Unit = {}
) {
    // hrudhay_check_list: May be this could be hoisted. Savable because, state retained on scroll or more.
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
        // modifier = Modifier.fillMaxWidth().height(80.dp).padding(8.dp),
        modifier = modifier
            // .height(
            //     height.coerceAtLeast(60.dp) // Makes sure at least 60dp is set.
            // )
            .fillMaxWidth()
            // .animateContentSize(
            //     animationSpec = tween(100)
            // )
            // .padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.DEFAULT_PADDING, horizontal = 20.sdp)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppText(
                    uiText = toDoTaskUIState.data.title.text.toUIText(),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                )
                if(isExpanded){
                    Spacer(modifier = Modifier.height(4.dp))
                    AppText(
                        uiText = toDoTaskUIState.data.description.text.toUIText(),
                        // hrudhay_check_list: Modify according to final ui
                        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                        overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            AppClickableIcon(
                resId = if(isExpanded) CoreR.drawable.ic_collapse_arrow else CoreR.drawable.ic_expand_arrow,
                contentDescriptionUIText = "Expand".toUIText(),
                onClick = {
                    isExpanded = !isExpanded
                }
            )

            AppClickableIcon(
                resId = CoreR.drawable.ic_delete,
                contentDescriptionUIText = "Delete".toUIText(),
                onClick = onRemoveClicked
            )

        }
    }
}

@MyPreview
@Composable
fun TodoListItemUIPreview() {
    AppPreviewContainer {
        TodoListItemUI(
            toDoTaskUIState = ToDoTaskUIState(
                TodoUIModel(
                    "1",
                    TextFieldValue("Title"),
                    TextFieldValue("Description"),
                )
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}