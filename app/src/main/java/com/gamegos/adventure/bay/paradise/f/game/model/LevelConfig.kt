package com.gamegos.adventure.bay.paradise.f.game.model

data class LevelConfig(
    val level: Int,
    val rows: Int,
    val cols: Int,
    val mines: Int
) {
    companion object {
        fun forLevel(level: Int): LevelConfig {
            return when (level) {
                1  -> LevelConfig(1, 4, 4, 2)
                2  -> LevelConfig(2, 4, 5, 3)
                3  -> LevelConfig(3, 5, 5, 4)
                4  -> LevelConfig(4, 5, 5, 5)
                5  -> LevelConfig(5, 5, 6, 5)
                6  -> LevelConfig(6, 5, 6, 6)
                7  -> LevelConfig(7, 6, 6, 7)
                8  -> LevelConfig(8, 6, 6, 8)
                9  -> LevelConfig(9, 6, 7, 8)
                10 -> LevelConfig(10, 6, 7, 9)
                11 -> LevelConfig(11, 7, 7, 10)
                12 -> LevelConfig(12, 7, 7, 11)
                13 -> LevelConfig(13, 7, 8, 12)
                14 -> LevelConfig(14, 7, 8, 13)
                15 -> LevelConfig(15, 8, 8, 13)
                16 -> LevelConfig(16, 8, 8, 14)
                17 -> LevelConfig(17, 8, 9, 15)
                18 -> LevelConfig(18, 8, 9, 16)
                19 -> LevelConfig(19, 9, 9, 17)
                20 -> LevelConfig(20, 9, 9, 18)
                21 -> LevelConfig(21, 9, 10, 19)
                22 -> LevelConfig(22, 9, 10, 20)
                23 -> LevelConfig(23, 10, 10, 21)
                24 -> LevelConfig(24, 10, 10, 22)
                25 -> LevelConfig(25, 10, 10, 23)
                26 -> LevelConfig(26, 10, 11, 24)
                27 -> LevelConfig(27, 10, 11, 25)
                28 -> LevelConfig(28, 11, 11, 26)
                29 -> LevelConfig(29, 11, 11, 28)
                30 -> LevelConfig(30, 11, 12, 30)
                else -> LevelConfig(level, 11, 12, 30)
            }
        }
    }
}