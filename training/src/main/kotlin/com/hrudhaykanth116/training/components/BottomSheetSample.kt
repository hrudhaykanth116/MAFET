package com.hrudhaykanth116.training.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import com.hrudhaykanth116.training.data.getDummyStringList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSample(
    onDismiss: () -> Unit,
) {

    // ModalBottomSheetSample(onDismiss = onDismiss)

    BottomSheetScaffoldSample()

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheetScaffoldSample() {

    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    // scaffoldState.bottomSheetState.hasPartiallyExpandedState
    // scaffoldState.bottomSheetState.partialExpand()
    // scaffoldState.bottomSheetState.expand()


    BottomSheetScaffold(
        sheetContent = {
            // Text(text = "Sheet content")

            LazyColumn(modifier = Modifier) {
                itemsIndexed(getDummyStringList(100)) { index: Int, item: Int ->
                    Text(text = "Index: $index", modifier = Modifier.fillMaxWidth())
                    VerticalSpacer(height = 10.dp)
                }
            }

        },
        sheetPeekHeight = 300.dp,
        sheetSwipeEnabled = false,
    ) {
        val modifier = Modifier.padding(it)

        Text(text = "Content")

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ModalBottomSheetSample(
    onDismiss: () -> Unit,
) {

    val bottomSheetState: SheetState = rememberModalBottomSheetState()


    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = bottomSheetState,
        scrimColor = Color.Blue,

        dragHandle = {
            // BottomSheetDefaults.DragHandle(
            //
            // )

            AppIcon(
                imageHolder = R.drawable.ic_expand_arrow.toImageHolder(),
                modifier = Modifier.size(100.dp)
            )

        }
    ) {

        LazyColumn() {
            itemsIndexed(getDummyStringList(100)) { index: Int, item: Int ->
                Text(text = "Index: $index", modifier = Modifier.fillMaxWidth())
                VerticalSpacer(height = 10.dp)
            }
        }
    }
}