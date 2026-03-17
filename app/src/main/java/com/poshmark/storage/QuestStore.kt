package com.poshmark.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.questStore: DataStore<Preferences> by preferencesDataStore("quest_prefs")