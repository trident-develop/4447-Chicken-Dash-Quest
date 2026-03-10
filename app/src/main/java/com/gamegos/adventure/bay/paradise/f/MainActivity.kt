package com.gamegos.adventure.bay.paradise.f

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gamegos.adventure.bay.paradise.f.audio.MusicManager
import com.gamegos.adventure.bay.paradise.f.storage.GamePreferences
import com.gamegos.adventure.bay.paradise.f.ui.screens.GameScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.HowToPlayScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.LeaderboardScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.LevelsScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.MenuScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.PrivacyPolicyScreen
import com.gamegos.adventure.bay.paradise.f.ui.screens.SettingsScreen

class MainActivity : ComponentActivity() {

    private lateinit var musicManager: MusicManager
    private lateinit var prefs: GamePreferences
    private val windowController by lazy {
        WindowInsetsControllerCompat(window, window.decorView)
    }
    private var multiTouchDetected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prefs = GamePreferences(applicationContext)
        musicManager = MusicManager(applicationContext)

        setContent {
            EggSweeperApp(prefs, musicManager)
        }
    }

    override fun onResume() {
        super.onResume()
        windowController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowController.hide(WindowInsetsCompat.Type.systemBars())
        musicManager.resume()
    }

    override fun onPause() {
        super.onPause()
        musicManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicManager.release()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1) {
            if (!multiTouchDetected) {
                multiTouchDetected = true
                val cancelEvent = MotionEvent.obtain(ev)
                cancelEvent.action = MotionEvent.ACTION_CANCEL
                super.dispatchTouchEvent(cancelEvent)
                cancelEvent.recycle()
            }
            return true
        }
        if (multiTouchDetected) {
            if (ev.actionMasked == MotionEvent.ACTION_UP ||
                ev.actionMasked == MotionEvent.ACTION_CANCEL
            ) {
                multiTouchDetected = false
            }
            return true
        }
        return super.dispatchTouchEvent(ev)
    }
}

@Composable
private fun EggSweeperApp(prefs: GamePreferences, musicManager: MusicManager) {
    var screen by remember { mutableStateOf<Screen>(Screen.Menu) }
    var musicEnabled by remember { mutableStateOf(musicManager.isMusicEnabled) }
    var maxUnlocked by remember { mutableIntStateOf(prefs.maxUnlockedLevel) }

    when (val current = screen) {
        Screen.Menu -> MenuScreen(
            onPlay = { screen = Screen.Levels },
            onLeaderboard = { screen = Screen.Leaderboard },
            onSettings = { screen = Screen.Settings }
        )

        Screen.Levels -> LevelsScreen(
            maxUnlocked = maxUnlocked,
            onBack = { screen = Screen.Menu },
            onLevelSelected = { level -> screen = Screen.Game(level) }
        )

        is Screen.Game -> GameScreen(
            level = current.level,
            onBack = { screen = Screen.Levels },
            onHome = { screen = Screen.Menu },
            onNextLevel = {
                val next = current.level + 1
                if (next <= 30) screen = Screen.Game(next)
                else screen = Screen.Levels
            },
            onWin = { timeMs ->
                prefs.saveBestTime(current.level, timeMs)
                if (current.level >= maxUnlocked && current.level < 30) {
                    prefs.maxUnlockedLevel = current.level + 1
                    maxUnlocked = current.level + 1
                }
            }
        )

        Screen.Leaderboard -> LeaderboardScreen(
            entries = prefs.getLeaderboardEntries(),
            onBack = { screen = Screen.Menu }
        )

        Screen.Settings -> SettingsScreen(
            musicEnabled = musicEnabled,
            onToggleMusic = {
                musicEnabled = musicManager.toggle()
            },
            onHowToPlay = { screen = Screen.HowToPlay },
            onPrivacyPolicy = { screen = Screen.PrivacyPolicy },
            onBack = { screen = Screen.Menu }
        )

        Screen.HowToPlay -> HowToPlayScreen(onBack = { screen = Screen.Settings })
        Screen.PrivacyPolicy -> PrivacyPolicyScreen(onBack = { screen = Screen.Settings })
    }
}

private sealed class Screen {
    data object Menu : Screen()
    data object Levels : Screen()
    data class Game(val level: Int) : Screen()
    data object Leaderboard : Screen()
    data object Settings : Screen()
    data object HowToPlay : Screen()
    data object PrivacyPolicy : Screen()
}
