package com.braza.chickenroad.presentation.screens.rating

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.TextWithBorderComponent
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_MEDIUM
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_SMALL
import com.braza.chickenroad.util.Constants.PADDING_CALCULATION_DIVISOR_TINY

@Composable
fun RatingContent(
    leadersList: SnapshotStateList<Pair<String, String>>,
    paddingValues: PaddingValues,
    innerPaddingDp: Dp
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
            var spaceBetweenItemsDp by remember { mutableStateOf(0.dp) }

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
                verticalArrangement = Arrangement.spacedBy(spaceBetweenItemsDp)
            ) {
                leadersList.forEachIndexed { id, leaders ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${id + 1}. ${leaders.first}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.futura_pt)),
                            modifier = Modifier.onGloballyPositioned {
                                with(density) {
                                    spaceBetweenItemsDp = it.size.height.toDp()
                                }
                            }
                        )
                        Text(
                            text = leaders.second,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.futura_pt))
                        )
                    }
                }
            }
        }
    }
}