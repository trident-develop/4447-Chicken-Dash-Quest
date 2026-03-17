package com.poshmark.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poshmark.LoadingActivity
import com.poshmark.MainActivity
import com.poshmark.game.present.AppUiState
import com.poshmark.game.present.StartDestination
import com.poshmark.game.viewmodel.AppStartViewModel
import com.poshmark.ui.components.helpers.ThirdBoard
import com.poshmark.ui.screens.ConnectScreen
import com.poshmark.ui.screens.LoadingScreen
import com.poshmark.ui.screens.isFruitConnected
import kotlinx.coroutines.delay

@SuppressLint("ContextCastToActivity")
@Composable
fun LoadingGraph(
    viewModel: AppStartViewModel,
    web: ThirdBoard
) {

    val navController = rememberNavController()
    val context = LocalContext.current as LoadingActivity
    var mainOpened by remember { mutableStateOf(false) }

    fun openMainIfNeeded() {
        if (mainOpened) return
        mainOpened = true

        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        context.startActivity(intent)
        context.finish()
    }

    NavHost(
        navController = navController,
        startDestination = if (context.isFruitConnected()) Screens.Loading.route else Screens.Connect.route
    ) {
        composable(Screens.Loading.route) {

            val route = rememberRouteToken()

            when {
                route.isLoading() -> LoadingScreen()
                route.isGame() -> {
                    LaunchedEffect(Unit) {
                        openMainIfNeeded()
                    }
                }
                route.isRules() -> {}
            }

            LoadingScreenRoute(
                viewModel = viewModel,
                onOpenPlayer = { url ->
                    web.loadUrl(url)
                },
                onOpenScreen1 = {
                    openMainIfNeeded()
                },
                onOpenScreen2 = {
                    openMainIfNeeded()
                },
                onOpenScreen3 = {
                    openMainIfNeeded()
                },
                onError = {
                    openMainIfNeeded()
                }
            )

            LoadingScreen()
        }

        composable(Screens.Connect.route) {
            ConnectScreen(navController)
        }
    }
}

@Composable
fun LoadingScreenRoute(
    viewModel: AppStartViewModel,
    onOpenPlayer: (String) -> Unit,
    onOpenScreen1: () -> Unit,
    onOpenScreen2: () -> Unit,
    onOpenScreen3: () -> Unit,
    onError: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startIfNeeded()
    }


    when (val state = uiState.value) {
        AppUiState.Loading -> LoadingScreen()

        is AppUiState.Ready -> {

            LaunchedEffect(state.destination) {
                when (val destination = state.destination) {
                    is StartDestination.Player -> onOpenPlayer(destination.score)
                    StartDestination.Screen1 -> onOpenScreen1()
                    StartDestination.Screen2 -> onOpenScreen2()
                    StartDestination.Screen3 -> onOpenScreen3()
                }
            }
        }

        is AppUiState.Error -> {
            onError()
        }
    }
}