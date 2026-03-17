package com.poshmark.device

import android.content.Context
import com.poshmark.device.core.DevicePropertiesResult
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DevicePropertiesRepository(
    private val context: Context
) {
    private val mutex = Mutex()
    private var cached: DevicePropertiesResult? = null

    suspend fun get(): DevicePropertiesResult {
        cached?.let { return it }

        return mutex.withLock {
            cached?.let { return it }

            val result = DevicePropertiesResult.create(context)
            cached = result
            result
        }
    }
}