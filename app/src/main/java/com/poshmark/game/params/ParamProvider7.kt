package com.poshmark.game.params

import android.content.Context
import com.poshmark.device.DevicePropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParamProvider7(
    private val repo: DevicePropertiesRepository
) : PlayerParamProvider {

    override suspend fun getParam(context: Context): Pair<String, String> = withContext(Dispatchers.IO) {
        val deviceProps = repo.get()
        val value7 = deviceProps.getX5()
        "mc28e6uh8um25eb" to value7
    }
}