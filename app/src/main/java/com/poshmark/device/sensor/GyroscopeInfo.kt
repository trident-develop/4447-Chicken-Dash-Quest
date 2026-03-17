package com.poshmark.device.sensor

import com.poshmark.device.core.DeviceMotionResult
import com.poshmark.device.core.Info

class GyroscopeInfo : Info {
    override suspend fun collect(vararg args: Any?): String {
        return try {
            val deviceMotionResult = args[0] as DeviceMotionResult
            val score = deviceMotionResult.gyroScore ?: "undefined"
            "GYRO[$score]"
        } catch (e: Throwable) {
            "GYRO[undefined]"
        }
    }
}