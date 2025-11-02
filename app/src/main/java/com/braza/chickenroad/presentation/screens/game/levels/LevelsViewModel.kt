package com.braza.chickenroad.presentation.screens.game.levels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.domain.repository.MaxOpenedLevelRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(
    private val maxOpenedLevelRepos: MaxOpenedLevelRepos
) : ViewModel() {

    var maxOpenedLevel by mutableIntStateOf(1)
        private set
    var isLoading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            maxOpenedLevel = maxOpenedLevelRepos.readMaxOpenedLevel()
            delay(2000)
            isLoading = false
        }
    }
}