package com.gamegos.adventure.bay.paradise.f.game.model

data class GameState(
    val cells: List<List<Cell>>,
    val status: GameStatus = GameStatus.PLAYING,
    val lives: Int = 3,
    val maxLives: Int = 3,
    val flagCount: Int = 0,
    val totalMines: Int = 0,
    val elapsedMs: Long = 0L
)
