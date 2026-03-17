package com.poshmark.device.core

fun interface Info {
    suspend fun collect(vararg args: Any?): String
}