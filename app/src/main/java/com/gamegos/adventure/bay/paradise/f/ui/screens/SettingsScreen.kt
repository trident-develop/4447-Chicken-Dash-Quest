package com.gamegos.adventure.bay.paradise.f.ui.screens

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
import com.gamegos.adventure.bay.paradise.f.R
import com.gamegos.adventure.bay.paradise.f.ui.components.GameBackground
import com.gamegos.adventure.bay.paradise.f.ui.components.MenuButton
import com.gamegos.adventure.bay.paradise.f.ui.components.SquareButton
import com.gamegos.adventure.bay.paradise.f.ui.components.TitleImage

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

            MenuButton(text = "How To Play", fontSize = 20.sp, onClick = onHowToPlay)
            MenuButton(text = "Privacy Policy", fontSize = 18.sp, onClick = onPrivacyPolicy)

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}