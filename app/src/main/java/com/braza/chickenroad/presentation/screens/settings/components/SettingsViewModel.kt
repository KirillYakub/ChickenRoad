package com.braza.chickenroad.presentation.screens.settings.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braza.chickenroad.domain.repository.SettingsRepos
import com.braza.chickenroad.util.CustomTabsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val customTabsHelper: CustomTabsHelper,
    private val settingsRepos: SettingsRepos
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isMusicOn = settingsRepos.readMusicData()
            val isSoundOn = settingsRepos.readSoundData()
            delay(1500)
            state = state.copy(
                isLoading = false,
                isMusicOn = isMusicOn,
                isSoundOn = isSoundOn
            )
        }
    }

    fun onCustomTabsOpenUrl(url: String) {
        customTabsHelper.openUrl(url)
    }

    fun updateMusicData(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            settingsRepos.writeMusicData(isEnabled)
            delay(1000)
            state = state.copy(isLoading = false, isMusicOn = isEnabled)
        }
    }

    fun updateSoundData(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            settingsRepos.writeSoundData(isEnabled)
            delay(1000)
            state = state.copy(isLoading = false, isSoundOn = isEnabled)
        }
    }
}