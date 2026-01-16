package com.chicken.chickenroad.presentation.screens.settings.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.presentation.ui.theme.Brown
import com.chicken.chickenroad.presentation.ui.theme.Green
import com.chicken.chickenroad.presentation.ui.theme.LightRed
import com.chicken.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_SMALL

@Composable
fun SettingsSwitcher(
    switcherContainerHeightDp: Dp,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .height(switcherContainerHeightDp)
            .drawBehind {
                val shadowColor = Color.Black.copy(alpha = 0.9f)
                val offsetY = 2.dp.toPx()
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(0f, offsetY),
                    size = size,
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Brown.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onCheckedChange(!isChecked) }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(switcherContainerHeightDp - 4.dp)
                    .padding(2.dp)
                    .drawBehind {
                        if (!isChecked) {
                            val shadowColor = Color.Black.copy(alpha = 0.9f)
                            val offsetY = 2.dp.toPx()
                            drawRoundRect(
                                color = shadowColor,
                                topLeft = Offset(0f, offsetY),
                                size = size,
                                cornerRadius = CornerRadius(100.dp.toPx())
                            )
                        }
                    },
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = if (!isChecked) LightRed else Color.Transparent
                ),
                content = { }
            )
            Spacer(
                modifier = Modifier.width(
                    switcherContainerHeightDp / PADDING_CALCULATION_DIVISOR_SMALL
                )
            )
            Card(
                modifier = Modifier
                    .size(switcherContainerHeightDp - 4.dp)
                    .padding(2.dp)
                    .drawBehind {
                        if (isChecked) {
                            val shadowColor = Color.Black.copy(alpha = 0.9f)
                            val offsetY = 2.dp.toPx()
                            drawRoundRect(
                                color = shadowColor,
                                topLeft = Offset(0f, offsetY),
                                size = size,
                                cornerRadius = CornerRadius(100.dp.toPx())
                            )
                        }
                    },
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = if (isChecked) Green else Color.Transparent
                ),
                content = { }
            )
        }
    }
}