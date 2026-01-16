package com.chicken.chickenroad.util

import android.content.Context
import android.provider.Settings

class CheckDebugEnable(private val context: Context) {
    fun isUsbDebuggingOff(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver, Settings.Global.ADB_ENABLED, 0
        ) == 0
    }
}