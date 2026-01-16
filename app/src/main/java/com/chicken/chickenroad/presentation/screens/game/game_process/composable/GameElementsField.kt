package com.chicken.chickenroad.presentation.screens.game.game_process.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.chicken.chickenroad.R
import com.chicken.chickenroad.domain.model.GameElementsModel
import com.chicken.chickenroad.presentation.screens.game.game_process.components.GameViewModel
import com.chicken.chickenroad.util.createCustomClickEffect

@Composable
fun ColumnScope.GameElementsField(
    model: GameViewModel,
    onItemClick: (GameElementsModel) -> Unit
) {
    val density = LocalDensity.current
    var innerElementSize by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(5f),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        model.gameElementsList.chunked(3).forEach { itemsRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsRow.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .createCustomClickEffect { onItemClick(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.game_elements_bg),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.onGloballyPositioned {
                                val boxSize = with(density) {
                                    it.size.width.toDp()
                                }
                                innerElementSize = (boxSize / 1.5.dp).dp
                            }
                        )
                        Image(
                            painter = painterResource(id =
                                if(model.gameProcessState.isGameStart && !item.isClicked)
                                    R.drawable.closed_item
                                else
                                    item.res
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(innerElementSize)
                        )
                    }
                }
            }
        }
    }
}