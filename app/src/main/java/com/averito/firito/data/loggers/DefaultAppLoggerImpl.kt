package com.averito.firito.data.loggers

import android.util.Log
import com.averito.firito.core.utils.AppLogger

class DefaultAppLoggerImpl() : AppLogger {
    private val defaultTag = "DefaultAppLogger"

    override fun debug(message: String) {
        Log.d(defaultTag, message)
    }

    override fun info(message: String) {
        Log.i(defaultTag, message)
    }

    override fun warn(message: String) {
        Log.w(defaultTag, message)
    }

    override fun error(message: String) {
        Log.e(defaultTag, message)
    }

}