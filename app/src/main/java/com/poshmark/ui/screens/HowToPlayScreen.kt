package com.poshmark.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.poshmark.R
import com.poshmark.ui.components.GameBackground
import com.poshmark.ui.components.GameFont
import com.poshmark.ui.components.SquareButton
import com.poshmark.ui.components.TitleImage
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun HowToPlayScreen(onBack: () -> Unit) {
    val isInPreview = LocalInspectionMode.current
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

            TitleImage(R.drawable.how_to_play_tittle)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.pop_up_1),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 28.dp, vertical = 60.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RuleItem("Tap a hatch to open it and reveal what's inside.")
                    RuleItem("Numbers show how many red eggs are hiding in adjacent cells.")
                    RuleItem("Avoid red eggs! Each one costs you a life. You have 3 lives.")
                    RuleItem("Long press a hatch to flag it as dangerous.")
                    RuleItem("Flagged hatches can't be opened by accident. Long press again to unflag.")
                    RuleItem("Open all safe hatches to win the level!")
                    RuleItem("Empty cells with no adjacent red eggs will auto-reveal their neighbors.")

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.hatch_gray),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                "Closed",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontFamily = GameFont
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.hatch_gold),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                "Safe",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontFamily =GameFont
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.egg_red),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                "Danger!",
                                color = Color(0xFFFF6B6B),
                                fontSize = 11.sp,
                                fontFamily = GameFont
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (!isInPreview) {
            AndroidView(
                factory = {
                    val adView = AdView(it)
                    adView.setAdSize(AdSize.BANNER)
                    adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
                    adView.loadAd(AdRequest.Builder().build())
                    adView
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun RuleItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "•",
            color = Color(0xFFFFD700),
            fontSize = 18.sp,
            fontFamily = GameFont
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = GameFont,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp
        )
    }
}