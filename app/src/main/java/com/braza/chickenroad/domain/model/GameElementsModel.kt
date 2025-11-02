package com.braza.chickenroad.domain.model

import androidx.annotation.DrawableRes

data class GameElementsModel(
    val id: Int,
    @DrawableRes
    val res: Int,
    val isCorrect: Boolean,
    val isClicked: Boolean
)