package com.gamegos.adventure.bay.paradise.f.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class GamePreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("egg_sweeper_prefs", Context.MODE_PRIVATE)

    var musicEnabled: Boolean
        get() = prefs.getBoolean(KEY_MUSIC, true)
        set(value) = prefs.edit { putBoolean(KEY_MUSIC, value) }

    var maxUnlockedLevel: Int
        get() = prefs.getInt(KEY_MAX_LEVEL, 1)
        set(value) = prefs.edit { putInt(KEY_MAX_LEVEL, value) }

    fun getBestTime(level: Int): Long =
        prefs.getLong("${KEY_BEST_TIME_PREFIX}$level", -1L)

    fun saveBestTime(level: Int, timeMs: Long) {
        val current = getBestTime(level)
        if (current == -1L || timeMs < current) {
            prefs.edit { putLong("${KEY_BEST_TIME_PREFIX}$level", timeMs) }
        }
    }

    fun getLeaderboardEntries(): List<LeaderboardEntry> {
        val entries = mutableListOf<LeaderboardEntry>()
        for (level in 1..30) {
            val time = getBestTime(level)
            if (time >= 0) {
                entries.add(LeaderboardEntry(level, time))
            }
        }
        return entries.sortedBy { it.level }
    }

    companion object {
        private const val KEY_MUSIC = "music_enabled"
        private const val KEY_MAX_LEVEL = "max_unlocked_level"
        private const val KEY_BEST_TIME_PREFIX = "best_time_level_"
    }
}

data class LeaderboardEntry(val level: Int, val bestTimeMs: Long)
