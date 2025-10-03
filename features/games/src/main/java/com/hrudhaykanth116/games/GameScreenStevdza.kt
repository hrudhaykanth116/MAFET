package com.hrudhaykanth116.games

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.games.domain.Game
import com.hrudhaykanth116.games.domain.GameSettings
import com.hrudhaykanth116.games.domain.GameStatus
import com.hrudhaykanth116.games.domain.MoveDirection
import com.hrudhaykanth116.games.domain.Weapon
import com.hrudhaykanth116.games.domain.levels
import com.hrudhaykanth116.games.domain.target.EasyTarget
import com.hrudhaykanth116.games.domain.target.MediumTarget
import com.hrudhaykanth116.games.domain.target.StrongTarget
import com.hrudhaykanth116.games.domain.target.Target
import com.hrudhaykanth116.games.stevdza_san.sprite.component.drawSpriteView
import com.hrudhaykanth116.games.stevdza_san.sprite.domain.SpriteFlip
import com.hrudhaykanth116.games.stevdza_san.sprite.domain.SpriteSheet
import com.hrudhaykanth116.games.stevdza_san.sprite.domain.SpriteSpec
import com.hrudhaykanth116.games.stevdza_san.sprite.domain.rememberSpriteState
import com.hrudhaykanth116.games.util.detectMoveGesture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

private const val NINJA_FRAME_WIDTH = 253
private const val NINJA_FRAME_HEIGHT = 303
const val WEAPON_SPAWN_RATE = 150L
const val WEAPON_SIZE = 32f
const val TARGET_SPAWN_RATE = 1500L
const val TARGET_SIZE = 40f

@Composable
fun GameScreenStevdza() {
    val scope = rememberCoroutineScope()
    // val audio = koinInject<AudioPlayer>()
    var game by remember { mutableStateOf(Game()) }
    val weapons = remember { mutableStateListOf<Weapon>() }

    val targets = remember { mutableStateListOf<com.hrudhaykanth116.games.domain.target.Target>() }

    var moveDirection by remember { mutableStateOf(MoveDirection.None) }
    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    // Update difficulty levels
    LaunchedEffect(game.score) {
        levels
            .filter { it.first.score == game.score }
            .takeIf { it.isNotEmpty() }
            ?.forEach { (_, nextLevel) ->
                game = game.copy(
                    settings = GameSettings(
                        ninjaSpeed = game.settings.ninjaSpeed + nextLevel.ninjaSpeed,
                        weaponSpeed = game.settings.weaponSpeed + nextLevel.weaponSpeed,
                        targetSpeed = game.settings.targetSpeed + nextLevel.targetSpeed,
                    )
                )
            }
    }

    val runningSprite = rememberSpriteState(
        totalFrames = 9,
        framesPerRow = 3
    )
    val standingSprite = rememberSpriteState(
        totalFrames = 1,
        framesPerRow = 1
    )
    val currentRunningFrame by runningSprite.currentFrame.collectAsState()
    val currentStandingFrame by standingSprite.currentFrame.collectAsState()
    val isRunning by runningSprite.isRunning.collectAsState()

    val runSpriteImageBitMap = ImageBitmap.imageResource(id = R.drawable.run_sprite)
    val standingSpriteImageBitMap = ImageBitmap.imageResource(id = R.drawable.standing_ninja)
    val kunaiSpriteImageBitMap = ImageBitmap.imageResource(id = R.drawable.kunai)

    val runningSpriteSpec = remember {
        SpriteSpec(
            screenWidth = screenWidth.toFloat(),
            default = SpriteSheet(
                frameWidth = NINJA_FRAME_WIDTH,
                frameHeight = NINJA_FRAME_HEIGHT,
                image = runSpriteImageBitMap
            )
        )
    }
    val standingSpriteSpec = remember {
        SpriteSpec(
            screenWidth = screenWidth.toFloat(),
            default = SpriteSheet(
                frameWidth = NINJA_FRAME_WIDTH,
                frameHeight = NINJA_FRAME_HEIGHT,
                image = standingSpriteImageBitMap
            )
        )
    }
    val runningImage = runningSpriteSpec.imageBitmap
    val standingImage = standingSpriteSpec.imageBitmap
    val kunaiImage = kunaiSpriteImageBitMap

    val ninjaOffsetX = remember(key1 = screenWidth) {
        Animatable(
            initialValue = ((screenWidth.toFloat()) / 2 - (NINJA_FRAME_WIDTH / 2))
        )
    }

    // Spawn the Weapons
    LaunchedEffect(isRunning, game.status) {
        while (isRunning && game.status == GameStatus.Started) {
            delay(WEAPON_SPAWN_RATE)
            weapons.add(
                Weapon(
                    x = ninjaOffsetX.value + (NINJA_FRAME_WIDTH / 2),
                    y = screenHeight - NINJA_FRAME_HEIGHT.toFloat() * 2,
                    radius = WEAPON_SIZE,
                    shootingSpeed = -game.settings.weaponSpeed
                )
            )
        }
    }

    // Spawn the Targets
    LaunchedEffect(game.status) {
        while (game.status == GameStatus.Started) {
            delay(TARGET_SPAWN_RATE)
            val randomX = (0..screenWidth).random()
            val isEven = (randomX % 2 == 0)
            if (isEven) {
                targets.add(
                    MediumTarget(
                        x = randomX.toFloat(),
                        y = Animatable(0f),
                        radius = TARGET_SIZE,
                        fallingSpeed = game.settings.targetSpeed
                    )
                )
            } else if (randomX > screenWidth * 0.75) {
                targets.add(
                    StrongTarget(
                        x = randomX.toFloat(),
                        y = Animatable(0f),
                        radius = TARGET_SIZE,
                        fallingSpeed = game.settings.targetSpeed * 0.25f
                    )
                )
            } else {
                targets.add(
                    EasyTarget(
                        x = randomX.toFloat(),
                        y = Animatable(0f),
                        radius = TARGET_SIZE,
                        fallingSpeed = game.settings.targetSpeed
                    )
                )
            }
        }
    }

    // Move Weapons & Targets and add Collision Detection
    LaunchedEffect(game.status) {
        while (game.status == GameStatus.Started) {
            withFrameMillis {
                targets.forEach { target ->
                    scope.launch(Dispatchers.Main) {
                        target.y.animateTo(
                            targetValue = target.y.value + target.fallingSpeed
                        )
                    }
                }
                weapons.forEach { weapon ->
                    weapon.y += weapon.shootingSpeed
                }

                // Check for collision
                val weaponIterator = weapons.iterator()
                while (weaponIterator.hasNext()) {
                    val weapon = weaponIterator.next()
                    val targetIterator = targets.listIterator()
                    while (targetIterator.hasNext()) {
                        val target = targetIterator.next()
                        if (isCollision(weapon, target)) {
                            // audio.playSound(index = 0)
                            if (target is StrongTarget) {
                                if (target.lives > 0) {
                                    targetIterator.set(
                                        element = target.copy(
                                            radius = target.radius + 10,
                                            lives = target.lives - 1
                                        )
                                    )
                                    weaponIterator.remove()
                                } else {
                                    weaponIterator.remove()
                                    targetIterator.remove()
                                    game = game.copy(score = game.score + 5)
                                }
                            } else if (target is MediumTarget) {
                                if (target.lives > 0) {
                                    targetIterator.set(
                                        element = target.copy(
                                            radius = target.radius + 10,
                                            lives = target.lives - 1
                                        )
                                    )
                                    weaponIterator.remove()
                                } else {
                                    weaponIterator.remove()
                                    targetIterator.remove()
                                    game = game.copy(score = game.score + 5)
                                }
                            } else if (target is EasyTarget) {
                                weaponIterator.remove()
                                targetIterator.remove()
                                game = game.copy(score = game.score + 5)
                            }
                            break
                        }
                    }
                }

                // Check if Game Over
                val offScreenTarget = targets.firstOrNull {
                    it.y.value > screenHeight
                }
                if (offScreenTarget != null) {
                    game = game.copy(
                        status = GameStatus.Over
                    )
                    runningSprite.stop()
                    weapons.removeAll { true }
                    targets.removeAll { true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
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
                            moveDirection = MoveDirection.Left
                            runningSprite.start()
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
                            moveDirection = MoveDirection.Right
                            runningSprite.start()
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
                            moveDirection = MoveDirection.None
                            runningSprite.stop()
                        }
                    )
                }
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            targets.forEach { target ->
                drawCircle(
                    color = target.color,
                    radius = target.radius,
                    center = Offset(
                        x = target.x,
                        y = target.y.value
                    )
                )
            }
            weapons.forEach { weapon ->
                drawImage(
                    image = kunaiImage,
                    dstOffset = IntOffset(
                        x = weapon.x.toInt(),
                        y = weapon.y.toInt()
                    )
                )
            }
            drawSpriteView(
                spriteState = if (isRunning) runningSprite else standingSprite,
                spriteSpec = if (isRunning) runningSpriteSpec else standingSpriteSpec,
                currentFrame = if (isRunning) currentRunningFrame else currentStandingFrame,
                image = if (isRunning) runningImage else standingImage,
                spriteFlip = if (moveDirection == MoveDirection.Left)
                    SpriteFlip.Horizontal else null,
                offset = IntOffset(
                    x = ninjaOffsetX.value.toInt(),
                    y = (screenHeight - NINJA_FRAME_HEIGHT - (NINJA_FRAME_HEIGHT / 2))
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 34.dp,
                vertical = 34.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Level: ${levels.firstOrNull { it.first.score >= game.score }?.first?.name ?: "MAX"}",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )
        Text(
            text = "Score: ${game.score}",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )
    }

    if (game.status == GameStatus.Idle) {
        Column(
            modifier = Modifier
                .clickable(enabled = false) { }
                .background(Color.Black.copy(alpha = 0.7f))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ready?",
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    game = game.copy(status = GameStatus.Started)
                }
            ) {
                Text(text = "Start")
            }
        }
    }

    if (game.status == GameStatus.Over) {
        Column(
            modifier = Modifier
                .clickable(enabled = false) { }
                .background(Color.Black.copy(alpha = 0.7f))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Over!",
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Your Score: ${game.score}",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    game = game.copy(
                        score = 0,
                        status = GameStatus.Started,
                        settings = GameSettings()
                    )
                }
            ) {
                Text(text = "Play again")
            }
        }
    }
}

fun isCollision(weapon: Weapon, target: Target): Boolean {
    val dx = weapon.x - target.x
    val dy = weapon.y - target.y.value
    val distance = sqrt(dx * dx + dy * dy)
    return distance < (weapon.radius + target.radius)
}