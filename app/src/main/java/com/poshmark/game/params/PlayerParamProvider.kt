package com.poshmark.game.params

import android.content.Context

interface PlayerParamProvider {
    suspend fun getParam(context: Context): Pair<String, String>?
}