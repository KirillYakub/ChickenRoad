package com.chicken.chickenroad.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.presentation.navigation.NavRoutes
import com.chicken.chickenroad.util.createCustomClickEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    text: String,
    currentRoute: String? = null,
    onRulesClick: () -> Unit = {},
    onBackClick: () -> Unit,
    backButtonSizeDp: (Dp) -> Unit
) {
    val density = LocalDensity.current

    CenterAlignedTopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .createCustomClickEffect(onClick = onBackClick)
                    .onGloballyPositioned {
                        with(density) {
                            backButtonSizeDp(it.size.width.toDp())
                        }
                    }
            )
        },
        actions = {
            if(currentRoute != NavRoutes.RulesScreenRoute.route) {
                Image(
                    painter = painterResource(id = R.drawable.rules_button),
                    contentDescription = null,
                    modifier = Modifier.createCustomClickEffect(onClick = onRulesClick)
                )
            }
        },
        title = {
            GradientTextWithBorderComponent(
                text = text,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}