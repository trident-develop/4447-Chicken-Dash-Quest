package com.gamegos.adventure.bay.paradise.f.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gamegos.adventure.bay.paradise.f.R

val GameFont = FontFamily(Font(R.font.font))

@Composable
fun Modifier.pressableWithCooldown(
    cooldownMillis: Long = 1000L,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier {
    var lastClickTimestamp by remember { mutableLongStateOf(0L) }
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "PressAnimation"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(enabled) {
            detectTapGestures(
                onPress = {
                    if (!enabled) return@detectTapGestures
                    val now = System.currentTimeMillis()
                    if (now - lastClickTimestamp >= cooldownMillis) {
                        lastClickTimestamp = now
                        isPressed = true
                        try {
                            tryAwaitRelease()
                            onClick()
                        } finally {
                            isPressed = false
                        }
                    }
                }
            )
        }
}

@Composable
fun Modifier.peckPress(
    cooldownMillis: Long = 1000L,
    isChickenReady: Boolean = true,
    onPeck: () -> Unit
): Modifier = pressableWithCooldown(
    cooldownMillis = cooldownMillis,
    enabled = isChickenReady,
    onClick = onPeck
)

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    btnRes: Int,
    btnMaxWidth: Float = 0.18f,
    cooldownMillis: Long = 1000L,
    btnEnabled: Boolean = true,
    btnClickable: () -> Unit
) {
    Image(
        painter = painterResource(id = btnRes),
        contentDescription = "Button",
        modifier = modifier
            .fillMaxWidth(btnMaxWidth)
            .aspectRatio(1f)
            .peckPress(
                onPeck = btnClickable,
                cooldownMillis = cooldownMillis,
                isChickenReady = btnEnabled
            )
    )
}

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    fontSize: TextUnit = 24.sp,
    cooldownMillis: Long = 1000L,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .height(height = 80.dp)
            .padding(vertical = 8.dp)
            .pressableWithCooldown(
                onClick = onClick,
                enabled = enabled,
                cooldownMillis = cooldownMillis
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.button_purple),
            contentDescription = "button",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontFamily = GameFont,
            modifier = Modifier.padding(bottom = 6.dp)
        )
    }
}

@Composable
fun GameBackground(content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_vertical),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        content()
    }
}

@Composable
fun TitleImage(drawableRes: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(drawableRes),
        contentDescription = "Title",
        modifier = modifier
            .fillMaxWidth(0.65f)
            .aspectRatio(3f),
        contentScale = ContentScale.Fit
    )
}
