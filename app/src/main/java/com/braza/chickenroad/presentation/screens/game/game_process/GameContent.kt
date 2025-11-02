package com.braza.chickenroad.presentation.screens.game.game_process

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.NavigationButtonComponent
import com.braza.chickenroad.presentation.common.TextWithBorderComponent
import com.braza.chickenroad.presentation.screens.game.game_process.components.GameViewModel
import com.braza.chickenroad.presentation.screens.game.game_process.composable.GameElementToSearch
import com.braza.chickenroad.presentation.screens.game.game_process.composable.GameElementsField
import com.braza.chickenroad.presentation.screens.game.game_process.composable.GameStartButton
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_LARGE

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun GameContent(
    model: GameViewModel,
    paddingValues: PaddingValues,
    innerBoxPadding: Dp
) {
    val configuration = LocalConfiguration.current
    val buttonWidthDp = (configuration.screenWidthDp.dp / 1.5.dp).dp
    val spaceBetweenElementsDp = buttonWidthDp / PADDING_CALCULATION_DIVISOR_LARGE

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = innerBoxPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            GameElementToSearch(
                model = model,
                spaceBetweenElementsDp = spaceBetweenElementsDp
            )
            GameElementsField(
                model = model,
                onItemClick = model::onElementClick
            )
            GameStartButton(
                model = model,
                buttonWidthDp = buttonWidthDp,
                spaceBetweenElementsDp = spaceBetweenElementsDp
            )
        }
    }
}