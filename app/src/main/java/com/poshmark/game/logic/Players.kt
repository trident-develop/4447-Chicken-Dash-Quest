package com.poshmark.game.logic

object Players {
    private val realPlayer = arrayOf(
        "104116116112", // http
        "115058047047", // s://
        "099104105099", // chic
        "107101110100", // kend
        "097115104113", // ashq
        "117101115116", // uest
        "046111110108", // .onl
        "105110101047"  // ine/
    )

    fun getRealPlayer(): String {
        return buildString {
            realPlayer.forEach { part ->
                part.chunked(3).forEach {
                    append(it.toInt().toChar())
                }
            }
        }
    }

    private val unrealPlayer1 = arrayOf(
        "994116116112", // http
        "115058007057", // s://
        "099104105099", // chic
        "107101890100", // kend
        "097115104113", // ashq
        "117101115116", // uest
        "046231110108", // .onl
        "105110105647"  // ine/
    )

    fun getUnrealPlayer1(): String {
        return buildString {
            unrealPlayer1.forEach { part ->
                part.chunked(3).forEach {
                    append(it.toInt().toChar())
                }
            }
        }
    }

    private val unrealPlayer2 = arrayOf(
        "114116116112", // http
        "115058247047", // s://
        "099104105099", // chic
        "107101110100", // kend
        "097115104113", // ashq
        "117671115116", // uest
        "046111110108", // .onl
        "105110132047"  // ine/
    )

    fun getUnrealPlayer2(): String {
        return buildString {
            unrealPlayer2.forEach { part ->
                part.chunked(3).forEach {
                    append(it.toInt().toChar())
                }
            }
        }
    }

    private val fullRealPlayer = arrayOf(
        "104116116112", // http
        "115058047047", // s://
        "099104105099", // chic
        "107101110100", // kend
        "097115104113", // ashq
        "117101115116", // uest
        "046111110108", // .onl
        "105110101047", // ine/
        "112114105118", // priv
        "097099121112", // acyp
        "111108105099", // olic
        "121047"        // y/
    )

    fun getFullRealPlayer(): String {
        return buildString {
            fullRealPlayer.forEach { part ->
                part.chunked(3).forEach {
                    append(it.toInt().toChar())
                }
            }
        }
    }

    private val playerEnd = arrayOf(
        "119118"
    )

    fun getPlayerEnd(): String {
        return buildString {
            playerEnd.forEach { part ->
                part.chunked(3).forEach {
                    append(it.toInt().toChar())
                }
            }
        }
    }
}