package com.braza.chickenroad.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_MEDIUM
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_SMALL
import com.braza.chickenroad.util.createCustomClickEffect

@Composable
fun NavigationButtonComponent(
    buttonRes: Int,
    collectButtonHeightDp: ((Dp) -> Unit)? = null,
    onClick: () -> Unit
) {
    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.createCustomClickEffect(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = buttonRes),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(3f)
                .onGloballyPositioned {
                    with(density) {
                         collectButtonHeightDp?.invoke(it.size.height.toDp())
                    }
                }
        )
    }
}

@Composable
fun NavigationButtonWithTextComponent(
    buttonRes: Int,
    text: String,
    textColor: Color = Color.Blue,
    font: Font = Font(R.font.futura_pt),
    collectButtonHeightDp: ((Dp) -> Unit)? = null,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    var buttonTextHeightDp by remember { mutableStateOf(0.dp) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.createCustomClickEffect(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = buttonRes),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(3f)
                .onGloballyPositioned {
                    with(density) {
                        collectButtonHeightDp?.invoke(it.size.height.toDp())
                    }
                }
        )
        Column {
            TextWithBorderComponent(
                text = text,
                font = font,
                textColor = textColor,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.onGloballyPositioned {
                    with(density) {
                        buttonTextHeightDp = it.size.height.toDp()
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(
                    buttonTextHeightDp / PADDING_CALCULATION_DIVISOR_SMALL
                )
            )
        }
    }
}


@Preview
@Composable
fun NavigationButtonComponentPreview() {
    NavigationButtonComponent(buttonRes = R.drawable.start_button) { }
}

@Preview
@Composable
fun NavigationButtonWithTextComponentPreview() {
    NavigationButtonWithTextComponent(
        text = "1",
        buttonRes = R.drawable.wood_button_bg
    ) { }
}