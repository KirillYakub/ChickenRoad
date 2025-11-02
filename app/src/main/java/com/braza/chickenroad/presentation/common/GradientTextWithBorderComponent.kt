package com.braza.chickenroad.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.ui.theme.Blue
import com.braza.chickenroad.presentation.ui.theme.Orange
import com.braza.chickenroad.presentation.ui.theme.Red

@Composable
private fun TextWithDifferentShadowOffsetComponent(
    text: String,
    offset: Offset,
    style: TextStyle,
    cornerColor: Color,
    font: Font
) {
    Text(
        text = text,
        style = style.copy(
            shadow = Shadow(
                color = cornerColor,
                offset = offset,
                blurRadius = 1f
            ),
            fontFamily = FontFamily(font)
        )
    )
}

@Composable
fun GradientTextWithBorderComponent(
    modifier: Modifier = Modifier,
    text: String,
    textColors: List<Color> = listOf(Red, Orange),
    cornerColor: Color = Color.White,
    font: Font = Font(R.font.chicken_crispy),
    style: TextStyle = MaterialTheme.typography.displaySmall
) {
    Box(modifier = modifier) {
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = 4f, y = 4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = -4f, y = -4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = 4f, y = -4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = -4f, y = 4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        Text(
            text = text,
            style = style.copy(
                brush = Brush.verticalGradient(
                    colors = textColors,
                    startY = 60f
                ),
                fontFamily = FontFamily(font)
            )
        )
    }
}

@Composable
fun TextWithBorderComponent(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Blue,
    cornerColor: Color = Color.White,
    font: Font = Font(R.font.chicken_crispy),
    style: TextStyle = MaterialTheme.typography.displaySmall
) {
    Box(modifier = modifier) {
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = 4f, y = 4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = -4f, y = -4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = 4f, y = -4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        TextWithDifferentShadowOffsetComponent(
            offset = Offset(x = -4f, y = 4f),
            text = text,
            style = style,
            cornerColor = cornerColor,
            font = font
        )
        Text(
            text = text,
            style = style.copy(
                color = textColor,
                fontFamily = FontFamily(font)
            )
        )
    }
}