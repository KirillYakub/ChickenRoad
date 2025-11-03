package com.braza.chickenroad.presentation.screens.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.NavigationButtonComponent
import com.braza.chickenroad.presentation.navigation.NavRoutes
import com.braza.chickenroad.util.Constants.BUTTON_WIDTH_ASPECT_RATIO
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_SMALL

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MenuScreen(onNavButtonClick: (String) -> Unit) {
    val screenHalfWidthDp = LocalConfiguration.current.screenWidthDp / BUTTON_WIDTH_ASPECT_RATIO
    var paddingBetweenButtons by remember { mutableStateOf(0.dp) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.chicken_label_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scale(2f)
                .padding(top = paddingBetweenButtons)
        )
        Column(
            modifier = Modifier
                .width(screenHalfWidthDp.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.chicken_logo),
                contentDescription = null,
                modifier = Modifier.aspectRatio(2f)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(paddingBetweenButtons)
            ) {
                NavigationButtonComponent(
                    buttonRes = R.drawable.start_button,
                    collectButtonHeightDp = { heightDp ->
                        paddingBetweenButtons = heightDp / PADDING_CALCULATION_DIVISOR_SMALL
                    },
                    onClick = { onNavButtonClick(NavRoutes.LevelsScreenRoute.route) }
                )
                NavigationButtonComponent(
                    buttonRes = R.drawable.rating_button,
                    onClick = { onNavButtonClick(NavRoutes.RatingScreenRoute.route) }
                )
                NavigationButtonComponent(
                    buttonRes = R.drawable.settings_button,
                    onClick = { onNavButtonClick(NavRoutes.SettingsScreenRoute.route) }
                )
            }
        }
    }
}