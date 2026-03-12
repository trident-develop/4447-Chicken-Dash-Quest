package com.poshmark.audio

import android.content.Context
import android.media.MediaPlayer
import com.poshmark.R
import com.poshmark.storage.GamePreferences

class MusicManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val prefs = GamePreferences(context)

    val isMusicEnabled: Boolean get() = prefs.musicEnabled

    fun start() {
        if (!prefs.musicEnabled) return
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.game_music).apply {
                isLooping = true
                setVolume(0.5f, 0.5f)
            }
        }
        mediaPlayer?.takeIf { !it.isPlaying }?.start()
    }

    fun pause() {
        mediaPlayer?.takeIf { it.isPlaying }?.pause()
    }

    fun resume() {
        if (prefs.musicEnabled) {
            mediaPlayer?.start() ?: start()
        }
    }

    fun toggle(): Boolean {
        val newState = !prefs.musicEnabled
        prefs.musicEnabled = newState
        if (newState) {
            start()
        } else {
            pause()
        }
        return newState
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
