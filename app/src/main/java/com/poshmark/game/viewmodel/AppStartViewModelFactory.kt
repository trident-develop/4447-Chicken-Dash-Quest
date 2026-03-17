package com.poshmark.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poshmark.storage.ScoreRepo
import com.poshmark.ui.components.PlayerBuilder
import com.poshmark.ui.components.ScoreDestinationResolver

class AppStartViewModelFactory(
    private val scoreRepo: ScoreRepo,
    private val playerBuilder: PlayerBuilder,
    private val destinationResolver: ScoreDestinationResolver
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppStartViewModel::class.java)) {
            return AppStartViewModel(
                scoreRepo = scoreRepo,
                playerBuilder = playerBuilder,
                destinationResolver = destinationResolver
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}