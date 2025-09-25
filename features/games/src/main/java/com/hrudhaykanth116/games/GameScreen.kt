package com.hrudhaykanth116.games

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.hrudhaykanth116.games.domain.Game
import com.hrudhaykanth116.games.domain.GameStatus
import com.hrudhaykanth116.games.domain.MoveDirection
import com.hrudhaykanth116.games.util.detectMoveGesture
import com.stevdza_san.sprite.component.drawSpriteView
import com.stevdza_san.sprite.domain.SpriteFlip
import com.stevdza_san.sprite.domain.SpriteSheet
import com.stevdza_san.sprite.domain.SpriteSpec
import com.stevdza_san.sprite.domain.rememberSpriteState

const val NINJA_FRAME_WIDTH = 234
const val NINJA_FRAME_HEIGHT = 321

@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    //
    // var screenWidth by remember { mutableStateOf(0) }
    // var screenHeight by remember { mutableStateOf(0) }
    //
    // val game by remember { mutableStateOf(Game()) }
    // val moveDirection by remember { mutableStateOf(MoveDirection.None) }
    //
    // val runningSprite = rememberSpriteState(
    //     totalFrames = 9,
    //     framesPerRow = 3
    // )
    //
    // val standingSprite = rememberSpriteState(
    //     totalFrames = 1,
    //     framesPerRow = 1
    // )
    //
    // val currentRunningFrame by runningSprite.currentFrame.collectAsState()
    // val currentStandingFrame by standingSprite.currentFrame.collectAsState()
    // val isRunning by runningSprite.isRunning.collectAsState()
    //
    // val runningSpriteSpec = remember {
    //     SpriteSpec(
    //         screenWidth = screenWidth.toFloat(),
    //         default = SpriteSheet(
    //             frameWidth = NINJA_FRAME_WIDTH,
    //             frameHeight = NINJA_FRAME_HEIGHT,
    //             image = R.drawable.run_sprite
    //         )
    //     )
    // }
    //
    // Box(
    //     modifier = modifier
    //         .fillMaxSize()
    //         .onGloballyPositioned{
    //             screenWidth = it.size.width
    //             screenHeight = it.size.height
    //         }
    //         .pointerInput(Unit){
    //             awaitPointerEventScope {
    //                 detectMoveGesture(
    //                     gameStatus = game.status,
    //                     onLeft = {
    //
    //                     },
    //                     onRight = {
    //
    //                     },
    //                     onFingerLifted = {
    //
    //                     }
    //                 )
    //             }
    //         }
    // ) {
    //
    //     Image(
    //         modifier = Modifier.fillMaxSize(),
    //         painter = painterResource((R.drawable.background)),
    //         contentDescription = null,
    //         contentScale = ContentScale.FillBounds
    //     )
    //
    //     Canvas(
    //         modifier = Modifier.fillMaxSize()
    //     ) {
    //         drawSpriteView(
    //             spriteFlip = if(moveDirection == MoveDirection.Left) SpriteFlip.Horizontal else null,
    //
    //
    //         )
    //     }
    //
    //
    // }

}