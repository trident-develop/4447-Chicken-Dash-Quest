package com.poshmark.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poshmark.R
import com.poshmark.storage.LeaderboardEntry
import com.poshmark.ui.components.GameBackground
import com.poshmark.ui.components.GameFont
import com.poshmark.ui.components.SquareButton
import com.poshmark.ui.components.TitleImage

@Composable
fun LeaderboardScreen(
    entries: List<LeaderboardEntry>,
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
                    .padding(horizontal = 16.dp)
            ) {
                SquareButton(
                    btnRes = R.drawable.back_button,
                    btnMaxWidth = 0.12f,
                    btnClickable = onBack
                )
            }

            TitleImage(R.drawable.leaders_tittle)

            Spacer(modifier = Modifier.height(16.dp))

            if (entries.isEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.pop_up_1),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .aspectRatio(1.2f),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(R.drawable.egg_gold),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "No records yet!",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = GameFont
                        )
                        Text(
                            text = "Complete levels to see\nyour best times here.",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp,
                            fontFamily = GameFont
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            } else {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "#",
                        color = Color(0xFFFFD700),
                        fontSize = 24.sp,
                        fontFamily = GameFont,
                        modifier = Modifier.width(30.dp)
                    )
                    Text(
                        "Level",
                        color = Color(0xFFFFD700),
                        fontSize = 24.sp,
                        fontFamily = GameFont,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "Best Time",
                        color = Color(0xFFFFD700),
                        fontSize = 24.sp,
                        fontFamily = GameFont
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .weight(1f)
                ) {
                    itemsIndexed(entries) { index, entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = GameFont,
                                modifier = Modifier.width(22.dp)
                            )
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "Level ${entry.level}",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontFamily = GameFont
                                )
                            }
                            Text(
                                text = formatLeaderboardTime(entry.bestTimeMs),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = GameFont
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatLeaderboardTime(ms: Long): String {
    val sec = (ms / 1000) % 60
    val min = (ms / 1000) / 60
    return "%d:%02d".format(min, sec)
}