package com.braza.chickenroad.util

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
import com.braza.chickenroad.R

@Composable
fun Modifier.createCustomClickEffect(
    onClick: () -> Unit
): Modifier {
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
                    clickDelay(onClick)
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