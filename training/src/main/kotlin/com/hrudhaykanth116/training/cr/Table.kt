package com.hrudhaykanth116.training.cr

import android.util.Log
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.components.AppFormButton
import com.hrudhaykanth116.core.ui.components.AppIcon
import com.hrudhaykanth116.core.ui.components.AppText
import com.hrudhaykanth116.core.ui.components.HorizontalSpacer
import com.hrudhaykanth116.core.ui.components.VerticalSpacer
import com.hrudhaykanth116.core.ui.models.toImageHolder
import kotlin.math.roundToInt

val offset = 250.dp

private const val TAG = "Table"

var discardOffSet: Offset? = null
var discardSize: IntSize? = null

@Composable
fun Table() {

    var moved by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
        ) {
            Card(text = "Discard".toUIText(), Modifier.onGloballyPositioned {
                Log.d(TAG, "Table: $it")
                discardOffSet = it.positionInWindow()
                discardSize = it.size
            })
            HorizontalSpacer()
            AppFormButton(btnText = "Animate".toUIText()) {
                moved = !moved
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
        ) {
            for (i in 0..9) {
                DragableCard(
                    text = "#$i".toUIText(),
                    i,
                    moved,
                    modifier = Modifier
                )
            }
        }
    }


}

@Composable
fun DragableCard(text: UIText, i: Int, moved: Boolean, modifier: Modifier = Modifier) {

    // IntOffset.Zero

    var isAnimated by remember {
        mutableStateOf(false)
    }

    val animationOffset: IntOffset by animateIntOffsetAsState(
        targetValue = if (moved) IntOffset(700 + (100 * i), -400) else IntOffset.Zero,
        label = "offset",
        animationSpec = tween(5000),
        finishedListener = {
            isAnimated = true
        }
    )

    var offsetX: Float by remember { mutableStateOf(700f + (100 * i)) }
    var offsetY: Float by remember { mutableStateOf(0f) }

    val moveOffSet = IntOffset(offsetX.roundToInt(), offsetY.roundToInt())

    val cardOffset = if (isAnimated) {
        moveOffSet
    } else {
        animationOffset
    }

    Column(
        modifier = modifier
            .absoluteOffset {
                cardOffset
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        Log.d(TAG, "DragableCard: ${offsetX}")
                        if (offsetX > discardOffSet!!.x
                            && (offsetX < discardOffSet!!.x + discardSize!!.width)
                        ) {
                            Log.d(TAG, "DragableCard: In position")
                        } else {
                            Log.d(TAG, "DragableCard: Not In position")
                            offsetX = 700f + (100 * i)
                            offsetY = 0f
                        }

                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                )
            }
            .size(width = 80.dp, 130.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(size = 20.dp))
            .border(2.dp, color = Color.Red, shape = RoundedCornerShape(size = 20.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppText(uiText = text, color = Color.White, modifier = Modifier.fillMaxWidth())
        AppIcon(
            resId = R.drawable.ic_account,
            modifier = Modifier.size(40.dp),
            tint = Color.White,
        )
        VerticalSpacer()
        AppText(
            uiText = "${cardOffset.x}, ${cardOffset.y}".toUIText(),
            color = Color.White,
            fontSize = 15.sp,
        )

    }
}

@Composable
fun Card(text: UIText, modifier: Modifier = Modifier) {


    Column(
        modifier = modifier
            .size(width = 80.dp, 130.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(size = 20.dp))
            .border(2.dp, color = Color.Red, shape = RoundedCornerShape(size = 20.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppText(uiText = text, color = Color.White, modifier = Modifier.fillMaxWidth())
        AppIcon(
            resId = R.drawable.ic_account,
            modifier = Modifier.size(40.dp),
            tint = Color.White,
        )

    }
}


@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)
@Composable
fun TablePreview() {

    Table()

}