package com.gamegos.adventure.bay.paradise.f.navigation

sealed class Screens(val route: String) {
    object Loading : Screens("loading")
    object Connect : Screens("connect")
}