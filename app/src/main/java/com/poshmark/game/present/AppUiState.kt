package com.poshmark.game.present

sealed class AppUiState {
    data object Loading : AppUiState()
    data class Ready(val destination: StartDestination) : AppUiState()
    data class Error(val message: String) : AppUiState()
}