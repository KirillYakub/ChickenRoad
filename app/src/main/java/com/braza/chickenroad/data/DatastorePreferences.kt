package com.braza.chickenroad.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.braza.chickenroad.domain.repository.FirstInputRepos
import com.braza.chickenroad.domain.repository.MaxOpenedLevelRepos
import com.braza.chickenroad.domain.repository.SettingsRepos
import com.braza.chickenroad.util.Constants.APP_DATASTORE_PREFERENCES
import com.braza.chickenroad.util.Constants.FIRST_INPUT_DATA_KEY
import com.braza.chickenroad.util.Constants.MAX_LEVEL_DATA_KEY
import com.braza.chickenroad.util.Constants.MUSIC_DATA_KEY
import com.braza.chickenroad.util.Constants.SOUND_DATA_KEY
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_DATASTORE_PREFERENCES
)

class DatastorePreferences(
    private val context: Context
) : SettingsRepos, FirstInputRepos, MaxOpenedLevelRepos {

    private object PreferencesKey {
        val musicDataKey = booleanPreferencesKey(name = MUSIC_DATA_KEY)
        val soundDataKey = booleanPreferencesKey(name = SOUND_DATA_KEY)
        val firstInputKey = booleanPreferencesKey(name = FIRST_INPUT_DATA_KEY)
        val maxLevelKey = intPreferencesKey(name = MAX_LEVEL_DATA_KEY)
    }

    override suspend fun writeMusicData(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.musicDataKey] = isEnabled
        }
    }

    override suspend fun readMusicData(): Boolean {
        try {
            val preferences = context.dataStore.data.first()
            return preferences[PreferencesKey.musicDataKey] ?: true
        } catch (_: NoSuchElementException) {
            return true
        }
    }

    override suspend fun writeSoundData(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.soundDataKey] = isEnabled
        }
    }

    override suspend fun readSoundData(): Boolean {
        try {
            val preferences = context.dataStore.data.first()
            return preferences[PreferencesKey.soundDataKey] ?: true
        } catch (_: NoSuchElementException) {
            return true
        }
    }

    override suspend fun saveFirstInput(isFirstInput: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.firstInputKey] = isFirstInput
        }
    }

    override suspend fun readFirstInput(): Boolean {
        try {
            val preferences = context.dataStore.data.first()
            return preferences[PreferencesKey.firstInputKey] ?: true
        } catch (_: NoSuchElementException) {
            return true
        }
    }

    override suspend fun saveMaxOpenedLevel(level: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.maxLevelKey] = level
        }
    }

    override suspend fun readMaxOpenedLevel(): Int {
        try {
            val preferences = context.dataStore.data.first()
            return preferences[PreferencesKey.maxLevelKey] ?: 1
        } catch (_: NoSuchElementException) {
            return 1
        }
    }
}