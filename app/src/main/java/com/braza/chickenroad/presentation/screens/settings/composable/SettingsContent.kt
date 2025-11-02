package com.braza.chickenroad.presentation.screens.settings.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.braza.chickenroad.presentation.common.NavigationButtonComponent
import com.braza.chickenroad.presentation.common.TextWithBorderComponent
import com.braza.chickenroad.presentation.screens.settings.components.SettingsState
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_MEDIUM
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_TINY

@Composable
fun SettingsContent(
    state: SettingsState,
    paddingValues: PaddingValues,
    innerPaddingDp: Dp,
    onPrivacyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onMusicClick: (Boolean) -> Unit,
    onSoundClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val density = LocalDensity.current
        var boxContentPadding by remember { mutableStateOf(0.dp) }
        var columnHeight by remember { mutableStateOf(0.dp) }

        Box(
            modifier = Modifier.padding(top = innerPaddingDp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wood_box_bg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        with(density) {
                            columnHeight = it.size.height.toDp()
                            boxContentPadding = it.size.width.toDp() / PADDING_CALCULATION_DIVISOR_MEDIUM
                        }
                    }
            )
            Column(
                modifier = Modifier
                    .height(columnHeight)
                    .padding(boxContentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                var switcherContainerHeightDp by remember { mutableStateOf(0.dp) }
                var paddingBetweenButtons by remember { mutableStateOf(0.dp) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextWithBorderComponent(
                        modifier = Modifier.onGloballyPositioned {
                            with(density) {
                                switcherContainerHeightDp = (it.size.height.toDp() / 1.5.dp).dp
                            }
                        },
                        text = stringResource(R.string.music_label),
                        font = Font(R.font.futura_pt),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    SettingsSwitcher(
                        isChecked = state.isMusicOn,
                        switcherContainerHeightDp = switcherContainerHeightDp,
                        onCheckedChange = { isChecked -> onMusicClick(isChecked) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextWithBorderComponent(
                        modifier = Modifier.onGloballyPositioned {
                            with(density) {
                                switcherContainerHeightDp = (it.size.height.toDp() / 1.5.dp).dp
                            }
                        },
                        text = stringResource(R.string.sound_label),
                        font = Font(R.font.futura_pt),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    SettingsSwitcher(
                        isChecked = state.isSoundOn,
                        switcherContainerHeightDp = switcherContainerHeightDp,
                        onCheckedChange = { isChecked -> onSoundClick(isChecked) }
                    )
                }
                Spacer(modifier = Modifier.height(paddingBetweenButtons))
                NavigationButtonComponent(
                    buttonRes = R.drawable.privacy_button,
                    collectButtonHeightDp = { heightDp ->
                        paddingBetweenButtons = heightDp / PADDING_CALCULATION_DIVISOR_TINY
                    },
                    onClick = onPrivacyClick
                )
                NavigationButtonComponent(
                    buttonRes = R.drawable.terms_button,
                    onClick = onTermsClick
                )
            }
        }
    }
}