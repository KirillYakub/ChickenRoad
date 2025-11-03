package com.braza.chickenroad.data.repository

import android.content.Context
import android.media.MediaPlayer
import com.braza.chickenroad.R
import com.braza.chickenroad.domain.repository.PlayerSoundsRepos

class PlayerSoundsReposImpl(private val context: Context) : PlayerSoundsRepos {

    private var soundPlayer: MediaPlayer? = null
    private var clickPlayer: MediaPlayer? = null

    private fun ensureSoundPlayer() {
        if (soundPlayer == null) {
            soundPlayer = MediaPlayer.create(context, R.raw.music_sound).apply {
                isLooping = true
                setVolume(1f, 1f)
            }
        }
    }

    private fun ensureClickPlayer() {
        if(clickPlayer == null) {
            clickPlayer = MediaPlayer.create(context, R.raw.click_sound).apply {
                setVolume(1f, 1f)
            }
        }
    }

    override fun startPlayer() {
        ensureSoundPlayer()
        if (soundPlayer?.isPlaying == false) {
            soundPlayer?.start()
        }
    }

    override fun stopPlayer() {
        if (soundPlayer?.isPlaying == true) {
            soundPlayer?.pause()
        }
    }

    override fun clickStart() {
        ensureClickPlayer()
        if (clickPlayer?.isPlaying == false) {
            clickPlayer?.start()
        }
    }
}