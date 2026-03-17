package com.poshmark.device.sensor

import com.poshmark.device.core.DeviceMotionResult
import com.poshmark.device.core.Info

class MagnetometerInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as DeviceMotionResult
            val score = deviceMotionResult.magScore ?: "undefined"
            "MAGN[$score]"
        } catch (e: Throwable) {
            "MAGN[undefined]"
        }
    }
}