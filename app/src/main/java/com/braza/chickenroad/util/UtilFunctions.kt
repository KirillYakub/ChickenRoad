package com.braza.chickenroad.util

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.sound.ClickViewModel

@Composable
fun Modifier.createCustomClickEffect(
    onClick: () -> Unit
): Modifier {

    val model = hiltViewModel<ClickViewModel>()
    var isPressed by remember { mutableStateOf(false) }
    val componentClickScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = stringResource(R.string.component_click_anim)
    )

    return this
        .graphicsLayer(
            scaleX = componentClickScale,
            scaleY = componentClickScale
        )
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                    clickDelay {
                        model.startClick()
                        onClick()
                    }
                }
            )
        }
}

private var clickTime = 0L
private fun clickDelay(onClick: () -> Unit) {
    val tempTime = System.currentTimeMillis()
    if (tempTime - clickTime >= 400L) {
        clickTime = tempTime
        onClick()
    }
}

@SuppressLint("DefaultLocale")
fun formatTimeFromSeconds(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}