package com.braza.chickenroad.presentation.screens.game.game_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.braza.chickenroad.R
import com.braza.chickenroad.util.createCustomClickEffect

@Composable
fun GameTopBar(
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    backButtonSizeDp: (Dp) -> Unit
) {
    val density = LocalDensity.current
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