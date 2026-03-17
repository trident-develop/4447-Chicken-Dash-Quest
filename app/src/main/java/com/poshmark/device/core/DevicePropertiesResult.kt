package com.poshmark.device.core

import android.content.Context
import com.poshmark.device.sensor.AccelerometerInfo
import com.poshmark.device.sensor.AudioVolumeInfo
import com.poshmark.device.sensor.GyroscopeInfo
import com.poshmark.device.sensor.LightSensorInfo
import com.poshmark.device.sensor.MagnetometerInfo
import com.poshmark.device.sensor.ProximitySensorInfo
import com.poshmark.device.system.AccessibilityInfo
import com.poshmark.device.system.BuildInfo
import com.poshmark.device.system.InstallSourceInfo

class DevicePropertiesResult private constructor(
    // x5 - Sensors
    private val gyroscope: String,
    private val accelerometer: String,
    private val lightSensor: String,
    private val audioVolume: String,
    private val magnetometer: String,
    private val proximity: String,

    // x10 - BUILD
    private val buildInfo: String,

    // s30 - INSTALL, A11Y
    private val installSource: String,
    private val accessibility: String
) {

    companion object {
        /**
         * Створює DevicePropertiesResult та збирає всі дані.
         * ВАЖЛИВО:
         * - не викликати на main thread
         * - не забудьте замінити "com.trident.sample.device_properties" пакедж на свій
         * - не забудьте додати ACCESS_WIFI_STATE пермішн в маніфест
         * - IpScore викликати окремо лише один раз за встановлення застосунку
         */
        suspend fun create(context: Context): DevicePropertiesResult {
            val motionResult = DeviceMotion.getResult(context)
            return DevicePropertiesResult(
                // x5 - Sensors
                gyroscope = GyroscopeInfo().collect(motionResult),
                accelerometer = AccelerometerInfo().collect(motionResult),
                lightSensor = LightSensorInfo().collect(motionResult),
                audioVolume = AudioVolumeInfo().collect(context),
                magnetometer = MagnetometerInfo().collect(motionResult),
                proximity = ProximitySensorInfo().collect(motionResult),

                // x10 - BUILD
                buildInfo = BuildInfo().collect(context),

                // s30 - INSTALL, A11Y
                installSource = InstallSourceInfo().collect(context),
                accessibility = AccessibilityInfo().collect(context)
            )
        }
    }

    /**
     * x5 - Sensors
     * @return "GYRO[value];ACC[value];LIGHT[value];MAGN[value];PROXIMITY[value]"
     */
    fun getX5(): String = listOf(gyroscope, accelerometer, lightSensor, magnetometer, proximity)
        .filter { it.isNotBlank() }
        .joinToString(";")

    /**
     * x10 - BUILD
     * @return "BUILD[value]"
     */
    fun getX10(): String = buildInfo

    /**
     * s30 - INSTALL, A11Y
     * @return "INSTALL[value];A11Y[value]"
     */
    fun getS30(): String = listOf(installSource, accessibility)
        .filter { it.isNotBlank() }
        .joinToString(";")
}