package com.braza.chickenroad.presentation.screens.game.game_process.components

data class GameProcessState(
    val isLoading: Boolean = false,
    val isPause: Boolean = false,
    val isVictory: Boolean? = null,
    val isGameStart: Boolean = false
)
