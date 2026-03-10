package com.gamegos.adventure.bay.paradise.f.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gamegos.adventure.bay.paradise.f.R
import com.gamegos.adventure.bay.paradise.f.ui.components.GameBackground
import com.gamegos.adventure.bay.paradise.f.ui.components.GameFont
import com.gamegos.adventure.bay.paradise.f.ui.components.SquareButton
import com.gamegos.adventure.bay.paradise.f.ui.components.TitleImage
import com.gamegos.adventure.bay.paradise.f.ui.components.peckPress

@Composable
fun LevelsScreen(
    maxUnlocked: Int,
    onBack: () -> Unit,
    onLevelSelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    BackHandler(enabled = true) { onBack() }
    GameBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 40.dp)
        ) {
            // Top bar
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

            // Title
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TitleImage(R.drawable.levels_tittle)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Winding path of levels
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in 1..30) {
                    val rowIndex = i - 1
                    val isUnlocked = i <= maxUnlocked
                    val isCurrent = i == maxUnlocked

                    // Zigzag: odd rows left, even rows right
                    val alignment = if (rowIndex % 2 == 0) Alignment.CenterStart else Alignment.CenterEnd
                    val horizontalPadding = if (rowIndex % 4 < 2) 20.dp else 60.dp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = if (alignment == Alignment.Start) horizontalPadding else 0.dp,
                                end = if (alignment == Alignment.End) horizontalPadding else 0.dp
                            ),
                        contentAlignment = alignment
                    ) {
                        LevelNode(
                            level = i,
                            isUnlocked = isUnlocked,
                            isCurrent = isCurrent,
                            onClick = { if (isUnlocked) onLevelSelected(i) }
                        )
                    }

                    if (i < 30) {
                        // Connector dot
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "·",
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun LevelNode(
    level: Int,
    isUnlocked: Boolean,
    isCurrent: Boolean,
    onClick: () -> Unit
) {
    val buttonRes = if (isUnlocked) R.drawable.open_level_button else R.drawable.close_level_button
    val alphaVal = if (isUnlocked) 1f else 0.5f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .alpha(alphaVal)
    ) {
        Image(
            painter = painterResource(buttonRes),
            contentDescription = "Level $level",
            modifier = Modifier
                .fillMaxSize()
                .peckPress(
                    onPeck = onClick,
                    isChickenReady = isUnlocked
                ),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "$level",
            color = Color.White,
            fontSize = if (isCurrent) 25.sp else 27.sp,
            fontFamily = GameFont
        )
    }
}