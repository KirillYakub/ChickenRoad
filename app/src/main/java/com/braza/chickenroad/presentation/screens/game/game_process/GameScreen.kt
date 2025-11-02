package com.braza.chickenroad.presentation.screens.game.game_process

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.presentation.common.LoadingContent
import com.braza.chickenroad.presentation.screens.game.game_process.components.GameViewModel
import com.braza.chickenroad.presentation.screens.game.game_process.composable.GameTopBar
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_TINY
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    model: GameViewModel,
    onSettingsClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    var scaffoldContainerPadding by remember { mutableStateOf(0.dp) }
    var innerBoxPadding by remember { mutableStateOf(0.dp) }

    if(!model.gameProcessState.isLoading) {
        val alpha = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            delay(100)
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }

        Scaffold(
            topBar = {
                GameTopBar(
                    onHomeClick = onHomeClick,
                    onSettingsClick = onSettingsClick,
                    backButtonSizeDp = { dpSize ->
                        innerBoxPadding = dpSize
                        scaffoldContainerPadding = dpSize / PADDING_CALCULATION_DIVISOR_TINY
                    }
                )
            },
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = scaffoldContainerPadding)
                .padding(top = scaffoldContainerPadding)
                .alpha(alpha.value),
        ) { paddingValues ->
            GameContent(
                model = model,
                paddingValues = paddingValues,
                innerBoxPadding = innerBoxPadding
            )
        }
    }
    else
        LoadingContent()
}