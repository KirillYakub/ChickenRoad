package com.chicken.chickenroad.presentation.sound

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chicken.chickenroad.domain.repository.PlayerSoundsRepos
import com.chicken.chickenroad.domain.repository.SettingsRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoundViewModel @Inject constructor(
    private val playerSoundsRepos: PlayerSoundsRepos,
    private val settingsRepos: SettingsRepos
): ViewModel() {

    fun onPlayerActivityLifecycleAction() {
        viewModelScope.launch(Dispatchers.IO) {
            if (settingsRepos.readMusicData())
                playerSoundsRepos.startPlayer()
            else
                playerSoundsRepos.stopPlayer()
        }
    }

    fun onPlayerSettingsAction(isEnabled: Boolean) {
        if(isEnabled)
            playerSoundsRepos.startPlayer()
        else
            playerSoundsRepos.stopPlayer()
    }
}