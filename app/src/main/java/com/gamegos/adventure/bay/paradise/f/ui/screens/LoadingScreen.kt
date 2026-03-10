package com.gamegos.adventure.bay.paradise.f.ui.screens

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gamegos.adventure.bay.paradise.f.R
import com.gamegos.adventure.bay.paradise.f.ui.components.GameBackground
import com.gamegos.adventure.bay.paradise.f.ui.components.GameFont

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    // Three eggs appearing with staggered timing
    val egg1Offset by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "egg1Y"
    )

    val egg2Offset by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(200)
        ),
        label = "egg2Y"
    )

    val egg3Offset by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(400)
        ),
        label = "egg3Y"
    )

    // Wobble rotation for eggs
    val wobble1 by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(200)
        ),
        label = "wobble1"
    )
    val wobble2 by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(500)
        ),
        label = "wobble2"
    )
    val wobble3 by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(900)
        ),
        label = "wobble3"
    )

    // Gold egg glow pulse
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    GameBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 38.sp,
                fontFamily = GameFont
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Three eggs row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                // Red egg 1
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.egg_red),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .offset(y = egg1Offset.dp)
                            .rotate(wobble1)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Gold egg (center) with glow
                Box(contentAlignment = Alignment.Center) {
                    // Glow behind

                    Image(
                        painter = painterResource(R.drawable.egg_gold),
                        contentDescription = null,
                        modifier = Modifier
                            .size(85.dp)
                            .offset(y = egg2Offset.dp)
                            .rotate(wobble2)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Red egg 2
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.egg_red),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .offset(y = egg3Offset.dp)
                            .rotate(wobble3)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            CircularProgressIndicator(
                color = Color(0xFFFFD700),
                strokeWidth = 7.dp,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading...",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp,
                fontFamily = GameFont
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

private val EaseOutBounce = Easing { fraction ->
    val n1 = 7.5625f
    val d1 = 2.75f
    var t = fraction
    when {
        t < 1f / d1 -> n1 * t * t
        t < 2f / d1 -> { t -= 1.5f / d1; n1 * t * t + 0.75f }
        t < 2.5f / d1 -> { t -= 2.25f / d1; n1 * t * t + 0.9375f }
        else -> { t -= 2.625f / d1; n1 * t * t + 0.984375f }
    }
}

private val EaseInOutSine = Easing { fraction ->
    -(kotlin.math.cos(Math.PI * fraction).toFloat() - 1f) / 2f
}