package com.braza.chickenroad.presentation.screens.game.game_process.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.R
import com.braza.chickenroad.domain.model.GameElementsModel
import com.braza.chickenroad.domain.repository.MaxOpenedLevelRepos
import com.braza.chickenroad.domain.repository.RatingRepos
import com.braza.chickenroad.util.Constants.DECREASE_PER_LEVEL
import com.braza.chickenroad.util.Constants.GAME_LEVEL_ARG
import com.braza.chickenroad.util.Constants.LEVEL_DEFAULT_TIME
import com.braza.chickenroad.util.Constants.MAX_LEVEL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val maxOpenedLevelRepos: MaxOpenedLevelRepos,
    private val ratingRepos: RatingRepos,
    savesStateHandle: SavedStateHandle
) : ViewModel() {

    val gameElementsList = mutableStateListOf<GameElementsModel>()
    var elementToSearch by mutableStateOf<Int?>(null)
        private set
    var gameProcessState by mutableStateOf(GameProcessState())
        private set

    private var clickJob: Job? = null
    private var levelsJob: Job? = null
    var currentLevel: Int = 1
        private set

    init {
        currentLevel = savesStateHandle.get<Int>(GAME_LEVEL_ARG) ?: 1
        gameLoadStart()
    }

    fun onPauseChange(isPause: Boolean) {
        gameProcessState = gameProcessState.copy(
            isPause = isPause
        )
    }

    fun gameProcessStart() {
        viewModelScope.launch {
            gameProcessState = gameProcessState.copy(
                isGameStart = true,
                isLoading = true
            )
            delay(1200)
            gameElementsList.shuffle()
            timerStart()
            gameProcessState = gameProcessState.copy(
                isLoading = false
            )
        }
    }

    fun gameLoadStart(withNextLevelClick: Boolean = false) {
        viewModelScope.launch {
            if(withNextLevelClick)
                currentLevel++
            gameProcessState = gameProcessState.copy(
                isLoading = true,
                isVictory = null,
                isGameStart = false,
                time = 0,
                maxTime = LEVEL_DEFAULT_TIME - currentLevel * DECREASE_PER_LEVEL,
                clickedItemsCount = 0
            )
            createGameField()
        }
    }

    fun onItemClickCheck(item: GameElementsModel) {
        if(gameProcessState.isGameStart && !item.isClicked) {
            val itemId = gameElementsList.indexOfFirst { it.id == item.id }
            if (itemId != -1 && (levelsJob == null || !levelsJob!!.isActive)) {
                levelsJob = viewModelScope.launch {
                    gameElementsList[itemId] = gameElementsList[itemId].copy(
                        isClicked = true
                    )
                    onItemClickAction(itemId)
                }
            }
        }
    }

    private suspend fun onItemClickAction(itemId: Int) {
        if(gameElementsList[itemId].isCorrect) {
            gameProcessState = gameProcessState.copy(
                clickedItemsCount = gameProcessState.clickedItemsCount + 1
            )
            if(gameProcessState.clickedItemsCount == gameProcessState.correctItemsCount) {
                gameProcessState = gameProcessState.copy(
                    isLoading = true
                )
                clickJob?.cancel()
                updateLevel()
                updateBestTime()
                delay(1000)
                gameProcessState = gameProcessState.copy(
                    isVictory = true,
                    isLoading = false
                )
            }
        }
        else {
            delay(800)
            gameElementsList[itemId] = gameElementsList[itemId].copy(
                isClicked = false
            )
        }
    }

    private suspend fun updateLevel() {
        val maxOpenedLevel = maxOpenedLevelRepos.readMaxOpenedLevel()
        if(currentLevel == maxOpenedLevel && currentLevel != MAX_LEVEL) {
            maxOpenedLevelRepos.saveMaxOpenedLevel(currentLevel + 1)
        }
    }

    private suspend fun updateBestTime() {
        val ratingTime = ratingRepos.readRatingTime()
        if(ratingTime == null || gameProcessState.time < ratingTime) {
            ratingRepos.saveRatingTime(gameProcessState.time)
        }
    }

    private suspend fun createGameField() {
        gameElementsList.clear()
        elementToSearch = elementList.random()
        gameProcessState = gameProcessState.copy(
            correctItemsCount = correctElementPossibleCounts.random()
        )
        repeat(gameProcessState.correctItemsCount) { id ->
            gameElementsList.add(
                GameElementsModel(
                    id = id,
                    res = elementToSearch!!,
                    isCorrect = true,
                    isClicked = false
                )
            )
        }
        repeat(ELEMENTS_COUNT - gameProcessState.correctItemsCount) { id ->
            val correctId = id + gameProcessState.correctItemsCount
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

    private fun timerStart() {
        clickJob = viewModelScope.launch {
            while (gameProcessState.time < gameProcessState.maxTime
                && gameProcessState.isVictory != true
                && isActive
            ) {
                delay(1000)
                if(!gameProcessState.isPause) {
                    gameProcessState = gameProcessState.copy(
                        time = gameProcessState.time + 1
                    )
                }
            }
            if(gameProcessState.time == gameProcessState.maxTime
                && gameProcessState.clickedItemsCount != gameProcessState.correctItemsCount) {
                gameProcessState = gameProcessState.copy(
                    isVictory = false
                )
            }
        }
    }
    private companion object {
        const val ELEMENTS_COUNT = 12
        val correctElementPossibleCounts = listOf(2, 4, 6)
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