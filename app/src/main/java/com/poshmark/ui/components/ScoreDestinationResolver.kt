package com.poshmark.ui.components

import com.poshmark.game.logic.Players
import com.poshmark.game.present.StartDestination

class ScoreDestinationResolver {

    fun resolve(link: String): StartDestination {
//        Log.d("MYTAG", "Players.getRealPlayer() ${Players.getRealPlayer()}")
//        Log.d("MYTAG", "Players.getUnrealPlayer1() ${Players.getUnrealPlayer1()}")
//        Log.d("MYTAG", "Players.getUnrealPlayer2() ${Players.getUnrealPlayer2()}")

        return when (link) {

            Players.getRealPlayer() -> StartDestination.Screen1

            Players.getUnrealPlayer1() -> StartDestination.Screen2

            Players.getUnrealPlayer2() -> StartDestination.Screen3

            else -> StartDestination.Player(link)
        }
    }
}