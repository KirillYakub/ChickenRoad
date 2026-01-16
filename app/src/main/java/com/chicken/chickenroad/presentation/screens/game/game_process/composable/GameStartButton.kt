package com.chicken.chickenroad.presentation.screens.game.game_process.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.presentation.common.NavigationButtonComponent
import com.chicken.chickenroad.presentation.common.TextWithBorderComponent
import com.chicken.chickenroad.presentation.screens.game.game_process.components.GameViewModel

@Composable
fun GameStartButton(
    model: GameViewModel,
    buttonWidthDp: Dp,
    spaceBetweenElementsDp: Dp
) {
    val density = LocalDensity.current
    var bottomElementBoxWidthDp by remember { mutableStateOf(0.dp) }

    if(!model.gameProcessState.isGameStart) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(buttonWidthDp)
                .onGloballyPositioned {
                    with(density) {
                        bottomElementBoxWidthDp = it.size.height.toDp()
                    }
                }
        ) {
            Spacer(modifier = Modifier.height(spaceBetweenElementsDp))
            NavigationButtonComponent(
                buttonRes = R.drawable.button_go,
                onClick = model::gameProcessStart
            )
            Spacer(modifier = Modifier.height(2.dp))
            TextWithBorderComponent(
                text = "LEVEL ${model.currentLevel}",
                font = Font(R.font.futura_pt),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
    else
        Spacer(modifier = Modifier.height(bottomElementBoxWidthDp))
}