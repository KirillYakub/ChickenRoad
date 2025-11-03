package com.braza.chickenroad.presentation.screens.game.game_process.components

data class GameProcessState(
    val time: Int = 0,
    val maxTime: Int = 0,
    val correctItemsCount: Int = 0,
    val clickedItemsCount: Int = 0,
    val isLoading: Boolean = false,
    val isPause: Boolean = false,
    val isVictory: Boolean? = null,
    val isGameStart: Boolean = false
)
