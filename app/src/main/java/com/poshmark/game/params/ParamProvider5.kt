package com.poshmark.game.params

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ParamProvider5 : PlayerParamProvider {

    override suspend fun getParam(context: Context): Pair<String, String> = withContext(Dispatchers.IO) {
        val value5 = Firebase.analytics.appInstanceId.await().toString()
        "5qeichjmrkyhpn" to value5
    }
}