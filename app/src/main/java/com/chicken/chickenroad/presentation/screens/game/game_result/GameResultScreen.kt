package com.chicken.chickenroad.presentation.screens.game.game_result

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.presentation.common.GradientTextWithBorderComponent
import com.chicken.chickenroad.presentation.common.NavigationButtonComponent
import com.chicken.chickenroad.presentation.common.TextWithBorderComponent
import com.chicken.chickenroad.presentation.ui.theme.Orange
import com.chicken.chickenroad.util.Constants.MAX_LEVEL
import com.chicken.chickenroad.util.createCustomClickEffect
import kotlinx.coroutines.delay

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun GameResultScreen(
    isVictory: Boolean = false,
    currentLevel: Int = 1,
    timeAsString: String = "",
    onHomeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(100)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha.value),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.game_result_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val density = LocalDensity.current
        var boxWidth by remember { mutableStateOf(0.dp) }
        var boxHeight by remember { mutableStateOf(0.dp) }

        if(isVictory) {
            Image(
                painter = painterResource(id = R.drawable.back_light),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id =
                    if(isVictory) R.drawable.golden_frame_wood_box
                    else R.drawable.silver_frame_wood_box
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        with(density) {
                            val backgroundWidth = it.size.width.toDp()
                            val backgroundHeight = it.size.height.toDp()
                            boxWidth = (backgroundWidth / 1.75.dp).dp
                            boxHeight = (backgroundHeight / 1.75.dp).dp
                        }
                    }
            )
            Column(
                modifier = Modifier.size(
                    width = boxWidth,
                    height = boxHeight
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                GradientTextWithBorderComponent(text =
                    if(isVictory) stringResource(id = R.string.victory)
                    else stringResource(id = R.string.game_over)
                )
                Image(
                    painter = painterResource(id =
                        if(isVictory) R.drawable.gold_stars
                        else R.drawable.silver_stars
                    ),
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(2f)
                )
                Text(
                    text = if(isVictory) "Your Time: $timeAsString" else stringResource(R.string.try_again),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.futura_pt))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = null,
                        modifier = Modifier.createCustomClickEffect(onClick = onHomeClick)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.restart),
                        contentDescription = null,
                        modifier = Modifier.createCustomClickEffect(onClick = onRestartClick)
                    )
                }
                if(currentLevel != MAX_LEVEL) {
                    NavigationButtonComponent(
                        buttonRes = if(isVictory) R.drawable.active_button_next else R.drawable.non_active_button_next,
                        onClick = {
                            if (isVictory)
                                onNextClick()
                        }
                    )
                }
                else if(isVictory) {
                    TextWithBorderComponent(
                        text = stringResource(R.string.game_completed),
                        textColor = Orange,
                        font = Font(R.font.futura_pt),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}