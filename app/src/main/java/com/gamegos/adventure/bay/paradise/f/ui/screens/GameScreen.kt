package com.gamegos.adventure.bay.paradise.f.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.gamegos.adventure.bay.paradise.f.R
import com.gamegos.adventure.bay.paradise.f.game.logic.EggSweeperEngine
import com.gamegos.adventure.bay.paradise.f.game.model.Cell
import com.gamegos.adventure.bay.paradise.f.game.model.GameStatus
import com.gamegos.adventure.bay.paradise.f.game.model.LevelConfig
import com.gamegos.adventure.bay.paradise.f.ui.components.GameBackground
import com.gamegos.adventure.bay.paradise.f.ui.components.GameFont
import com.gamegos.adventure.bay.paradise.f.ui.components.MenuButton
import com.gamegos.adventure.bay.paradise.f.ui.components.SquareButton
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    level: Int,
    onBack: () -> Unit,
    onHome: () -> Unit,
    onNextLevel: () -> Unit,
    onWin: (Long) -> Unit
) {
    val config = LevelConfig.forLevel(level)
    var engine by remember(level) { mutableStateOf(EggSweeperEngine(config)) }
    var gameState by remember(level) { mutableStateOf(engine.currentState()) }
    var elapsedMs by remember(level) { mutableLongStateOf(0L) }
    var isPaused by remember { mutableStateOf(false) }
    var isExitingScreen by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isExitingScreen = true
        onBack()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, engine) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE && gameState.status != GameStatus.WON && gameState.status != GameStatus.LOST && !isExitingScreen)
                isPaused = true
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    // Timer
    LaunchedEffect(level, gameState.status, isPaused) {
        while (gameState.status == GameStatus.PLAYING && !isPaused) {
            delay(100)
            elapsedMs += 100
        }
    }

    // Notify win
    LaunchedEffect(gameState.status) {
        if (gameState.status == GameStatus.WON) {
            onWin(elapsedMs)
        }
    }

    fun replay() {
        engine = EggSweeperEngine(config)
        gameState = engine.currentState()
        elapsedMs = 0L
        isPaused = false
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cellSize = calculateCellSize(screenWidth, config.cols, config.rows)

    GameBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.1f,
                    btnClickable = {
                        isExitingScreen = true
                        onBack()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                SquareButton(
                    btnRes = R.drawable.pause_button,
                    btnMaxWidth = 0.115f,
                    btnClickable = { isPaused = true }
                )
                Spacer(modifier = Modifier.weight(1f))

                // Lives
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(gameState.maxLives) { i ->
                        Image(
                            painter = painterResource(R.drawable.heart),
                            contentDescription = "life",
                            modifier = Modifier
                                .size(28.dp)
                                .padding(2.dp)
                                .alpha(if (i < gameState.lives) 1f else 0.25f),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Level info bar
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.score_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(40.dp),
                    contentScale = ContentScale.FillBounds
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth(0.65f)
                ) {
                    Text(
                        text = "Level $level",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = GameFont
                    )
                    Text(
                        text = formatTime(elapsedMs),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = GameFont
                    )
                    Text(
                        text = "${gameState.totalMines}",
                        color = Color(0xFFFF6B6B),
                        fontSize = 16.sp,
                        fontFamily = GameFont
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Game grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    for (row in 0 until config.rows) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            for (col in 0 until config.cols) {
                                val cell = gameState.cells[row][col]
                                CellView(
                                    cell = cell,
                                    cellSize = cellSize,
                                    gameOver = gameState.status != GameStatus.PLAYING,
                                    onTap = {
                                        if (gameState.status == GameStatus.PLAYING) {
                                            gameState = engine.reveal(row, col)
                                        }
                                    },
                                    onLongPress = {
                                        if (gameState.status == GameStatus.PLAYING) {
                                            gameState = engine.toggleFlag(row, col)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Overlays
        if (isPaused) {
            OverlayContainer {
                PopupContent {
                    Text("Paused", color = Color.White, fontSize = 32.sp, fontFamily = GameFont)
                    Spacer(Modifier.height(24.dp))
                    MenuButton(text = "Resume", onClick = { isPaused = false })
                    MenuButton(text = "Replay", onClick = {
                        isExitingScreen = true
                        replay()
                    })
                    Spacer(Modifier.height(8.dp))
                    SquareButton(
                        btnRes = R.drawable.home_button,
                        btnMaxWidth = 0.15f,
                        btnClickable = {
                            isExitingScreen = true
                            onHome()
                        }
                    )
                }
            }
        }

        if (gameState.status == GameStatus.WON) {
            OverlayContainer {
                PopupContent {
                    Image(
                        painter = painterResource(R.drawable.you_win),
                        contentDescription = "You Win",
                        modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(2.5f),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(8.dp))
                    Row {
                        repeat(3) {
                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Time: ${formatTime(elapsedMs)}",
                        color = Color.White, fontSize = 18.sp, fontFamily = GameFont
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SquareButton(btnRes = R.drawable.home_button, btnMaxWidth = 0.13f, btnClickable = {
                            isExitingScreen = true
                            onHome()
                        })
                        SquareButton(btnRes = R.drawable.replay_button, btnMaxWidth = 0.16f, btnClickable = {
                            isExitingScreen = true
                            replay()
                        })
                        if (level < 30) {
                            SquareButton(btnRes = R.drawable.next_level_button, btnMaxWidth = 0.21f, btnClickable = {
                                isExitingScreen = true
                                onNextLevel()
                            })
                        }
                    }
                }
            }
        }

        if (gameState.status == GameStatus.LOST) {
            OverlayContainer {
                PopupContent {
                    Image(
                        painter = painterResource(R.drawable.game_over),
                        contentDescription = "Game Over",
                        modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(2.5f),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        SquareButton(btnRes = R.drawable.home_button, btnMaxWidth = 0.15f, btnClickable = {
                            isExitingScreen = true
                            onHome()
                        })
                        SquareButton(btnRes = R.drawable.replay_button, btnMaxWidth = 0.19f, btnClickable = {
                            isExitingScreen = true
                            replay()
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun OverlayContainer(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .pointerInput(Unit) { detectTapGestures { /* consume */ } },
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
private fun PopupContent(content: @Composable ColumnScope.() -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.pop_up_1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(0.85f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
private fun CellView(
    cell: Cell,
    cellSize: Dp,
    gameOver: Boolean,
    onTap: () -> Unit,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(cellSize)
            .padding(0.5.dp)
            .pointerInput(cell.isRevealed, cell.isFlagged, gameOver) {
                if (!gameOver && !cell.isRevealed) {
                    detectTapGestures(
                        onTap = { if (!cell.isFlagged) onTap() },
                        onLongPress = { onLongPress() }
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        when {
            !cell.isRevealed -> {
                Image(
                    painter = painterResource(
                        if (cell.isFlagged) R.drawable.hatch_red else R.drawable.hatch_gray
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                if (cell.isFlagged) {
                    Image(
                        painter = painterResource(R.drawable.egg_red),
                        contentDescription = "flag",
                        modifier = Modifier.fillMaxSize(0.45f),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            cell.isMine -> {
                Image(
                    painter = painterResource(R.drawable.hatch_red),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(R.drawable.egg_red),
                    contentDescription = "mine",
                    modifier = Modifier.fillMaxSize(0.6f),
                    contentScale = ContentScale.Fit
                )
            }
            else -> {
                Image(
                    painter = painterResource(R.drawable.hatch_gold),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                if (cell.adjacentMines > 0) {
                    Text(
                        text = "${cell.adjacentMines}",
                        color = numberColor(cell.adjacentMines),
                        fontSize = (cellSize.value * 0.4f).sp,
                        fontFamily = GameFont
                    )
                }
            }
        }
    }
}

private fun numberColor(n: Int): Color = when (n) {
    1 -> Color(0xFF2196F3)
    2 -> Color(0xFF4CAF50)
    3 -> Color(0xFFF44336)
    4 -> Color(0xFF9C27B0)
    5 -> Color(0xFFFF9800)
    6 -> Color(0xFF009688)
    7 -> Color(0xFF795548)
    else -> Color(0xFFE91E63)
}

private fun calculateCellSize(screenWidth: Dp, cols: Int, rows: Int): Dp {
    val maxW = (screenWidth - 32.dp) / cols
    val maxH = 420.dp / rows
    return minOf(maxW, maxH, 44.dp)
}

private fun formatTime(ms: Long): String {
    val sec = (ms / 1000) % 60
    val min = (ms / 1000) / 60
    return "%d:%02d".format(min, sec)
}