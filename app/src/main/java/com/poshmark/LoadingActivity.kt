package com.poshmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poshmark.device.DevicePropertiesRepository
import com.poshmark.game.logic.Players
import com.poshmark.game.params.ParamProvider1
import com.poshmark.game.params.ParamProvider2
import com.poshmark.game.params.ParamProvider3
import com.poshmark.game.params.ParamProvider4
import com.poshmark.game.params.ParamProvider5
import com.poshmark.game.params.ParamProvider6
import com.poshmark.game.params.ParamProvider7
import com.poshmark.game.params.ParamProvider8
import com.poshmark.game.params.ParamProvider9
import com.poshmark.game.viewmodel.AppStartViewModel
import com.poshmark.game.viewmodel.AppStartViewModelFactory
import com.poshmark.navigation.LoadingGraph
import com.poshmark.storage.ScoreRepoImpl
import com.poshmark.storage.questStore
import com.poshmark.ui.components.PlayerBuilder
import com.poshmark.ui.components.ScoreDestinationResolver
import com.poshmark.ui.components.helpers.ThirdBoard

class LoadingActivity : ComponentActivity() {

    private var controller: WindowInsetsControllerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        controller = WindowInsetsControllerCompat(window, window.decorView)
        controller?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller?.hide(WindowInsetsCompat.Type.systemBars())
        enableEdgeToEdge()

        val scoreRepo = ScoreRepoImpl(questStore)

        val deviceRepo = DevicePropertiesRepository(applicationContext)

        val playerBuilder = PlayerBuilder(
            baseName = Players.getFullRealPlayer(),
            providers = listOf(
                ParamProvider1(),
                ParamProvider2(),
                ParamProvider7(deviceRepo),
                ParamProvider8(deviceRepo),
                ParamProvider9(deviceRepo),
                ParamProvider3(),
                ParamProvider4(),
                ParamProvider5(),
                ParamProvider6()
            ),
            context = applicationContext
        )

        val destinationResolver = ScoreDestinationResolver()

        val thirdBoard = ThirdBoard(this, scoreRepo)

        setContent {
            val factory = remember {
                AppStartViewModelFactory(
                    scoreRepo = scoreRepo,
                    playerBuilder = playerBuilder,
                    destinationResolver = destinationResolver
                )
            }
            val viewModel: AppStartViewModel = viewModel(
                factory = factory
            )
            LoadingGraph(viewModel, thirdBoard)
        }
    }
}
