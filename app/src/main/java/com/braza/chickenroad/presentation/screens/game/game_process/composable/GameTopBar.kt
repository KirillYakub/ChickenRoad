package com.braza.chickenroad.presentation.screens.game.game_process.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.TextWithBorderComponent
import com.braza.chickenroad.presentation.ui.theme.DarkGreen
import com.braza.chickenroad.presentation.ui.theme.Green
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_LARGE
import com.braza.chickenroad.util.createCustomClickEffect

@Composable
fun GameTopBar(
    time: Int,
    maxTime: Int,
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    backButtonSizeDp: (Dp) -> Unit
) {
    val density = LocalDensity.current
    var maxTimeBarWidthDp by remember { mutableStateOf(0.dp) }

    val fraction = time.toFloat() / maxTime
    val animatedWidth by animateDpAsState(
        targetValue = maxTimeBarWidthDp * fraction,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = null,
            modifier = Modifier
                .createCustomClickEffect(onClick = onHomeClick)
                .onGloballyPositioned {
                    with(density) {
                        backButtonSizeDp(it.size.width.toDp())
                    }
                }
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            TextWithBorderComponent(
                text = stringResource(R.string.time),
                font = Font(R.font.futura_pt),
                style = MaterialTheme.typography.titleMedium
            )
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.blue_bg),
                    contentDescription = null,
                    modifier = Modifier.onGloballyPositioned {
                        with(density) {
                            maxTimeBarWidthDp =
                                it.size.width.toDp() - PADDING_CALCULATION_DIVISOR_LARGE.dp
                        }
                    }
                )
                Row(modifier = Modifier.width(maxTimeBarWidthDp)) {
                    Box(
                        modifier = Modifier
                            .width(animatedWidth)
                            .height(12.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Green, DarkGreen)
                                )
                            )
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = null,
            modifier = Modifier
                .createCustomClickEffect(onClick = onSettingsClick)
                .onGloballyPositioned {
                    with(density) {
                        backButtonSizeDp(it.size.width.toDp())
                    }
                }
        )
    }
}