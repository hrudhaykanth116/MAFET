package com.hrudhaykanth116.training.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.common.utils.compose.MyPreview
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.training.R

@MyPreview
@Composable
fun ProfileImage() {

    CenteredColumn(
        modifier = Modifier
            .size(400.dp)
            .background(color = Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.dc_3),
            contentDescription = "Profile image",
            modifier = Modifier
                .background(color = Color.Red)
                .requiredSize(300.dp) // Enforces size. Like minSize
                .background(color = Color.Green)
                .size(200.dp) // Takes constraints from above but works as max size
                .background(color = Color.Blue)
                .requiredSize(100.dp)
                .clip(CircleShape)
                .padding(10.dp) // Passes 50dp to parent adding padding of 2*10 to below 100 size.
                .size(50.dp) // Takes 100dp space from above since parent constraints to 100 dp size.
        )
    }

}