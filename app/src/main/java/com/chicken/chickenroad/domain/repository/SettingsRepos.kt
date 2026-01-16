package com.chicken.chickenroad.domain.repository

interface SettingsRepos {
    suspend fun writeMusicData(isEnabled: Boolean)
    suspend fun writeSoundData(isEnabled: Boolean)
    suspend fun readMusicData(): Boolean
    suspend fun readSoundData(): Boolean
}