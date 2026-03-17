package com.poshmark.device.sensor

import com.poshmark.device.core.DeviceMotionResult
import com.poshmark.device.core.Info

class AccelerometerInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as DeviceMotionResult
            val score = deviceMotionResult.accelScore ?: "undefined"
            "ACC[$score]"
        } catch (e: Throwable) {
            "ACC[undefined]"
        }
    }
}