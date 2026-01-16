package com.chicken.chickenroad.presentation.sound

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.chickenroad.domain.repository.PlayerSoundsRepos
import com.chicken.chickenroad.domain.repository.SettingsRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClickViewModel @Inject constructor(
    private val playerSoundsRepos: PlayerSoundsRepos,
    private val settingsRepos: SettingsRepos
): ViewModel() {

    fun startClick() {
        viewModelScope.launch {
            if (settingsRepos.readSoundData()) {
                playerSoundsRepos.clickStart()
            }
        }
    }
}