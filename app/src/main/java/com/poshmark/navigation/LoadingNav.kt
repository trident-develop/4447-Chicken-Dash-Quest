package com.poshmark.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poshmark.LoadingActivity
import com.poshmark.MainActivity
import com.poshmark.ui.screens.ConnectScreen
import com.poshmark.ui.screens.LoadingScreen
import com.poshmark.ui.screens.isFruitConnected
import kotlinx.coroutines.delay

@SuppressLint("ContextCastToActivity")
@Composable
fun LoadingGraph() {

    val navController = rememberNavController()
    val context = LocalContext.current as LoadingActivity

    NavHost(
        navController = navController,
        startDestination = if (context.isFruitConnected()) Screens.Loading.route else Screens.Connect.route
    ) {
        composable(Screens.Loading.route) {

            LaunchedEffect(Unit) {
                delay(2000)
                context.startActivity(Intent(context, MainActivity::class.java))
                context.finish()
            }

            LoadingScreen()
        }

        composable(Screens.Connect.route) {
            ConnectScreen(navController)
        }
    }
}