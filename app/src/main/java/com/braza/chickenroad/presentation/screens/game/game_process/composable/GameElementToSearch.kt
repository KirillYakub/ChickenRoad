package com.braza.chickenroad.presentation.screens.game.game_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.TextWithBorderComponent
import com.braza.chickenroad.presentation.screens.game.game_process.components.GameViewModel

@Composable
fun GameElementToSearch(
    model: GameViewModel,
    spaceBetweenElementsDp: Dp
) {
    val density = LocalDensity.current
    var topElementBoxHeightDp by remember { mutableStateOf(0.dp) }

    if(!model.gameProcessState.isGameStart) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.onGloballyPositioned {
                with(density) {
                    topElementBoxHeightDp = it.size.height.toDp()
                }
            }
        ) {
            if (model.elementToSearch != null) {
                Image(
                    painter = painterResource(id = model.elementToSearch!!),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
            TextWithBorderComponent(
                text = stringResource(R.string.you_need_to_find_a_pair),
                font = Font(R.font.futura_pt),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spaceBetweenElementsDp))
        }
    }
    else
        Spacer(modifier = Modifier.height(topElementBoxHeightDp))
}