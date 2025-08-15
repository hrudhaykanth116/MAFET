package com.hrudhaykanth116.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import ir.kaaveh.sdpcompose.ssp


@Composable
fun TooltipWithTriangle(
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit,
    backgroundColor: Color = Color.White,
    textColor: Color = Color(0xFF272727)
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tooltip body
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = textSize,
                    fontWeight = FontWeight(700),
                    color = textColor,
                    textAlign = TextAlign.Center,
                )
            )
        }

        // Triangle pointer
        Canvas(modifier = Modifier.size(12.dp)) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width / 2f, size.height/1.5f)
                close()
            }
            drawPath(path = path, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun ToolTipWithTrianglePreview() {
    AppPreviewContainer {
        TooltipWithTriangle(text = "John doe", textSize = 12.ssp)
    }
}
