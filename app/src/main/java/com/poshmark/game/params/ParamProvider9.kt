package com.poshmark.game.params

import android.content.Context
import com.poshmark.device.DevicePropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParamProvider9(
    private val repo: DevicePropertiesRepository
) : PlayerParamProvider {

    override suspend fun getParam(context: Context): Pair<String, String> = withContext(Dispatchers.IO) {
        val deviceProps = repo.get()
        val value9 = deviceProps.getS30()
        "l8iiul3jo49qw79" to value9
    }
}