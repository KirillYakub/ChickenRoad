package com.chicken.chickenroad.presentation.screens.game.levels

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.presentation.common.NavigationButtonWithTextComponent
import com.chicken.chickenroad.presentation.ui.theme.Blue
import com.chicken.chickenroad.util.Constants.BUTTON_WIDTH_ASPECT_RATIO
import com.chicken.chickenroad.util.Constants.MAX_LEVEL
import com.chicken.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_MEDIUM

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LevelsContent(
    paddingValues: PaddingValues,
    maxOpenedLevel: Int,
    innerPaddingDp: Dp,
    onLevelSelected: (Int) -> Unit
) {
    val screenHalfWidthDp = LocalConfiguration.current.screenWidthDp / BUTTON_WIDTH_ASPECT_RATIO
    var paddingBetweenButtons by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = innerPaddingDp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(screenHalfWidthDp.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(paddingBetweenButtons)
        ) {
            repeat(MAX_LEVEL) { levelId ->
                val level = levelId + 1
                val isOpened = maxOpenedLevel >= level
                NavigationButtonWithTextComponent(
                    text = level.toString(),
                    textColor = if(isOpened) Blue else Color.DarkGray,
                    buttonRes = if(isOpened) R.drawable.wood_button_bg else R.drawable.wood_bg_non_active,
                    collectButtonHeightDp = { heightDp ->
                        paddingBetweenButtons = heightDp / PADDING_CALCULATION_DIVISOR_MEDIUM
                    },
                    onClick = {
                        if(isOpened)
                            onLevelSelected(level)
                    }
                )
            }
        }
    }
}