package com.braza.chickenroad.presentation.screens.game.game_process.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.R
import com.braza.chickenroad.domain.model.GameElementsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    val gameElementsList = mutableStateListOf<GameElementsModel>()
    var elementToSearch by mutableStateOf<Int?>(null)
        private set
    var gameProcessState by mutableStateOf(GameProcessState())
        private set

    init {
        gameLoadStart()
    }

    fun gameProcessStart() {
        gameProcessState = gameProcessState.copy(
            isGameStart = true
        )
    }

    fun gameLoadStart() {
        gameProcessState = gameProcessState.copy(
            isLoading = true,
            isVictory = null,
            isGameStart = false
        )
        viewModelScope.launch {
            createGameField()
        }
    }

    private suspend fun createGameField() {
        elementToSearch = elementList.random()
        val correctElementCountOnField = elementsPairPossibleCountRange.random() * 2
        repeat(correctElementCountOnField) { id ->
            gameElementsList.add(
                GameElementsModel(
                    id = id,
                    res = elementToSearch!!,
                    isCorrect = true,
                    isClicked = false
                )
            )
        }
        repeat(ELEMENTS_COUNT - correctElementCountOnField) { id ->
            val correctId = id + correctElementCountOnField
            val element = elementList.filter { element ->
                element != elementToSearch!!
            }.random()
            gameElementsList.add(
                GameElementsModel(
                    id = correctId,
                    res = element,
                    isCorrect = false,
                    isClicked = false
                )
            )
        }
        gameElementsList.shuffle()
        delay(1800)
        gameProcessState = gameProcessState.copy(
            isLoading = false
        )
    }

    fun onElementClick(elementUniqueId: Int) {
        val elementIdInList = gameElementsList.firstOrNull { element ->
            element.id == elementUniqueId
        }?.id
        if(elementIdInList != null) {
            gameElementsList[elementIdInList] = gameElementsList[elementIdInList].copy(
                isClicked = true
            )
        }
    }

    private companion object {
        const val ELEMENTS_COUNT = 12
        val elementsPairPossibleCountRange = 1..3
        val elementList = listOf(
            R.drawable.element_1,
            R.drawable.element_2,
            R.drawable.element_3,
            R.drawable.element_4,
            R.drawable.element_5,
            R.drawable.element_6,
            R.drawable.element_7,
            R.drawable.element_8,
            R.drawable.element_9,
            R.drawable.element_10,
            R.drawable.element_11,
            R.drawable.element_12
        )
    }
}