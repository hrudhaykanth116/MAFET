package com.hrudhaykanth116.games

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.hrudhaykanth116.core.common.ui.preview.AppPreview
import com.hrudhaykanth116.core.common.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.games.domain.Game
import com.hrudhaykanth116.games.domain.GameStatus
import com.hrudhaykanth116.games.domain.MoveDirection
import com.hrudhaykanth116.games.util.detectMoveGesture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val NINJA_FRAME_WIDTH = 253
private const val NINJA_FRAME_HEIGHT = 303

@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    var game by remember { mutableStateOf(Game(status = GameStatus.Started)) }
    var moveDirection by remember { mutableStateOf(MoveDirection.None) }

    val ninjaOffsetX = remember(key1 = screenWidth) {
        Animatable(
            initialValue = ((screenWidth.toFloat()) / 2 - (NINJA_FRAME_WIDTH / 2))
        )
    }

    var isRunning by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                screenWidth = it.size.width
                screenHeight = it.size.height
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    detectMoveGesture(
                        gameStatus = game.status,
                        onLeft = {
                            Logger.d(TAG, "GameScreen: onLeft")
                            moveDirection = MoveDirection.Left
                            isRunning = true
                            scope.launch(Dispatchers.Main) {
                                while (isRunning) {
                                    ninjaOffsetX.animateTo(
                                        targetValue = if ((ninjaOffsetX.value - game.settings.ninjaSpeed) >= 0 - (NINJA_FRAME_WIDTH / 2))
                                            ninjaOffsetX.value - game.settings.ninjaSpeed else ninjaOffsetX.value,
                                        animationSpec = tween(30)
                                    )
                                }
                            }
                        },
                        onRight = {
                            Logger.d(TAG, "GameScreen: onRight")
                            moveDirection = MoveDirection.Right
                            isRunning = true
                            scope.launch(Dispatchers.Main) {
                                while (isRunning) {
                                    ninjaOffsetX.animateTo(
                                        targetValue = if ((ninjaOffsetX.value + game.settings.ninjaSpeed + NINJA_FRAME_WIDTH) <= screenWidth + (NINJA_FRAME_WIDTH / 2))
                                            ninjaOffsetX.value + game.settings.ninjaSpeed else ninjaOffsetX.value,
                                        animationSpec = tween(30)
                                    )
                                }
                            }
                        },
                        onFingerLifted = {
                            Logger.d(TAG, "GameScreen: onFingerLifted")
                            isRunning = false
                            moveDirection = MoveDirection.None
                        }
                    )
                }
            }
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource((R.drawable.background)),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )


        val density = LocalDensity.current
        val ninjaHeight = with(density) { NINJA_FRAME_HEIGHT.toDp() }
        val ninjaWidth = with(density) { NINJA_FRAME_WIDTH.toDp() }

        Image(
            modifier = Modifier
                .size(
                    height = ninjaHeight,
                    width = ninjaWidth
                )
                .offset {
                    IntOffset(
                        x = ninjaOffsetX.value.roundToInt(),
                        y = (screenHeight - NINJA_FRAME_HEIGHT - (NINJA_FRAME_HEIGHT / 2))
                    )
                },
            painter = painterResource((R.drawable.standing_ninja)),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )


    }

}

@AppPreview
@Composable
private fun GameScreenPreview() {
    AppPreviewContainer {
        CenteredColumn {
            GameScreen()
        }
    }
}

private const val TAG = "GameScreen"