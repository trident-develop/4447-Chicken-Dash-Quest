package com.poshmark.game.logic

import com.poshmark.game.model.*
import kotlin.random.Random

class EggSweeperEngine(private val config: LevelConfig) {

    private var grid: Array<Array<Cell>> = emptyArray()
    private var lives = 3
    private var status = _root_ide_package_.com.poshmark.game.model.GameStatus.PLAYING
    private var flagCount = 0
    private var minesPlaced = false

    init {
        initEmptyGrid()
    }

    private fun initEmptyGrid() {
        grid = Array(config.rows) { r ->
            Array(config.cols) { c ->
                Cell(row = r, col = c)
            }
        }
    }

    private fun placeMines(excludeRow: Int, excludeCol: Int) {
        val positions = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until config.rows) {
            for (c in 0 until config.cols) {
                if (r != excludeRow || c != excludeCol) {
                    positions.add(r to c)
                }
            }
        }
        positions.shuffle(Random)
        val minePositions = positions.take(config.mines).toSet()

        grid = Array(config.rows) { r ->
            Array(config.cols) { c ->
                val isMine = (r to c) in minePositions
                Cell(row = r, col = c, isMine = isMine)
            }
        }

        // Calculate adjacency
        for (r in 0 until config.rows) {
            for (c in 0 until config.cols) {
                if (!grid[r][c].isMine) {
                    val count = neighbors(r, c).count { (nr, nc) -> grid[nr][nc].isMine }
                    grid[r][c] = grid[r][c].copy(adjacentMines = count)
                }
            }
        }
        minesPlaced = true
    }

    private fun neighbors(r: Int, c: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val nr = r + dr
                val nc = c + dc
                if (nr in 0 until config.rows && nc in 0 until config.cols) {
                    result.add(nr to nc)
                }
            }
        }
        return result
    }

    fun reveal(row: Int, col: Int): GameState {
        if (status != GameStatus.PLAYING) return currentState()
        val cell = grid[row][col]
        if (cell.isRevealed || cell.isFlagged) return currentState()

        if (!minesPlaced) {
            placeMines(row, col)
        }

        val current = grid[row][col]
        if (current.isMine) {
            grid[row][col] = current.copy(isRevealed = true)
            lives--
            if (lives <= 0) {
                revealAllMines()
                status = GameStatus.LOST
            }
        } else {
            if (current.adjacentMines == 0) {
                floodFill(row, col)
            } else {
                grid[row][col] = current.copy(isRevealed = true)
            }
            checkWin()
        }
        return currentState()
    }

    private fun floodFill(r: Int, c: Int) {
        if (r !in 0 until config.rows || c !in 0 until config.cols) return
        val cell = grid[r][c]
        if (cell.isRevealed || cell.isFlagged || cell.isMine) return

        grid[r][c] = cell.copy(isRevealed = true)

        if (cell.adjacentMines == 0) {
            neighbors(r, c).forEach { (nr, nc) -> floodFill(nr, nc) }
        }
    }

    fun toggleFlag(row: Int, col: Int): GameState {
        if (status != GameStatus.PLAYING) return currentState()
        val cell = grid[row][col]
        if (cell.isRevealed) return currentState()

        val newFlagged = !cell.isFlagged
        grid[row][col] = cell.copy(isFlagged = newFlagged)
        flagCount += if (newFlagged) 1 else -1
        return currentState()
    }

    private fun revealAllMines() {
        for (r in 0 until config.rows) {
            for (c in 0 until config.cols) {
                if (grid[r][c].isMine) {
                    grid[r][c] = grid[r][c].copy(isRevealed = true)
                }
            }
        }
    }

    private fun checkWin() {
        val totalSafe = config.rows * config.cols - config.mines
        val revealedSafe = grid.sumOf { row -> row.count { it.isRevealed && !it.isMine } }
        if (revealedSafe >= totalSafe) {
            status = GameStatus.WON
        }
    }

    fun currentState(): GameState {
        return GameState(
            cells = grid.map { it.toList() },
            status = status,
            lives = lives,
            maxLives = 3,
            flagCount = flagCount,
            totalMines = config.mines
        )
    }
}
