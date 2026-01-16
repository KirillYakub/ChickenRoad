package com.chicken.chickenroad.presentation.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_LARGE
import kotlinx.coroutines.delay

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LoadingContent() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val bottomPadding = configuration.screenHeightDp.dp / PADDING_CALCULATION_DIVISOR_LARGE

    var loadingText by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(
        label = stringResource(R.string.load_rotation_label)
    )
    val loadImageRotation by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = stringResource(R.string.rotation_anim_label)
    )

    LaunchedEffect(Unit) {
        while (true) {
            loadingText = context.getString(R.string.loading)
            repeat(3) {
                loadingText += "."
                delay(300)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.chicken_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .rotate(degrees = loadImageRotation)
        )
        Spacer(modifier = Modifier.height(14.dp))
        GradientTextWithBorderComponent(text = loadingText)
    }
}