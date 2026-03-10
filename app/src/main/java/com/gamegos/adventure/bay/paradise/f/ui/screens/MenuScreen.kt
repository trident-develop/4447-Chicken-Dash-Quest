package com.gamegos.adventure.bay.paradise.f.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gamegos.adventure.bay.paradise.f.R
import com.gamegos.adventure.bay.paradise.f.ui.components.GameBackground
import com.gamegos.adventure.bay.paradise.f.ui.components.MenuButton

@Composable
fun MenuScreen(
    onPlay: () -> Unit,
    onLeaderboard: () -> Unit,
    onSettings: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "menu")

    val floatY1 by infiniteTransition.animateFloat(
        initialValue = -6f, targetValue = 6f,
        animationSpec = infiniteRepeatable(tween(2400), RepeatMode.Reverse),
        label = "float1"
    )
    val floatY2 by infiniteTransition.animateFloat(
        initialValue = 5f, targetValue = -5f,
        animationSpec = infiniteRepeatable(tween(2800, delayMillis = 300), RepeatMode.Reverse),
        label = "float2"
    )
    val goldPulse by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.08f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "goldPulse"
    )
    val goldGlow by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.7f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Reverse),
        label = "goldGlow"
    )
    val hatchScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.04f,
        animationSpec = infiniteRepeatable(tween(2000, delayMillis = 500), RepeatMode.Reverse),
        label = "hatchAnim"
    )

    GameBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Animated egg decorations
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.egg_red),
                    contentDescription = null,
                    modifier = Modifier
                        .size(55.dp)
                        .offset(y = floatY1.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.egg_gold),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .scale(goldPulse + 0.15f)
                            .alpha(goldGlow)
                            .blur(10.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.egg_gold),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .scale(goldPulse)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(R.drawable.egg_red),
                    contentDescription = null,
                    modifier = Modifier
                        .size(55.dp)
                        .offset(y = floatY2.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Animated hatch
            Image(
                painter = painterResource(R.drawable.hatch_gray),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .scale(hatchScale)
            )

            Spacer(modifier = Modifier.weight(1f))

            MenuButton(text = "Play", onClick = onPlay)
            MenuButton(text = "Leaderboard", fontSize = 20.sp, onClick = onLeaderboard)
            MenuButton(text = "Settings", onClick = onSettings)

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}