package com.poshmark.game.params

import android.content.Context
import com.poshmark.device.DevicePropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParamProvider8(
    private val repo: DevicePropertiesRepository
) : PlayerParamProvider {

    override suspend fun getParam(context: Context): Pair<String, String> = withContext(Dispatchers.IO) {
        val deviceProps = repo.get()
        val value8 = deviceProps.getX10()
        "vtk0i5qkq1iy98b" to value8
    }
}