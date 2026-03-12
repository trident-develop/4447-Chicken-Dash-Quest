package com.poshmark.game.model

data class Cell(
    val row: Int,
    val col: Int,
    val isMine: Boolean = false,
    val adjacentMines: Int = 0,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false
)
