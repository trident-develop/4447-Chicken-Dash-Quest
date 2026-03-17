package com.poshmark.game.params

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParamProvider6 : PlayerParamProvider {

    override suspend fun getParam(context: Context): Pair<String, String> = withContext(Dispatchers.IO) {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        val value6 = pi.firstInstallTime.toString()
        "yi1qk0nsnrso8" to value6
    }
}