package com.abilashcse.logger

import android.util.Log

/**
 * Centralised logging methods.
 */
object DLog {
    const val LOG_LEVEL_VERBOSE = 0
    const val LOG_LEVEL_INFO = 1
    const val LOG_LEVEL_DEBUG = 2
    const val LOG_LEVEL_WARNING = 3
    const val LOG_LEVEL_ERROR = 4
    private const val mLoggingIsEnabled = true
    private const val mLogLevel = LOG_LEVEL_DEBUG
    private const val mLogTagBase = "LS_"
    private fun loggingIsEnabled(): Boolean {
        return mLoggingIsEnabled
    }

    private fun logTagBase(): String {
        return mLogTagBase
    }

    /** @param message : Message string to log
     */
    fun dLog(message: String?) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_DEBUG) {
            Log.d(logTagBase(), message!!)
        }
    }

    /** @param errorMessage : Message string to log
     */
    fun error(logTag: String, errorMessage: String?) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_ERROR) {
            Log.e(logTagBase() + logTag, errorMessage!!)
        }
    }

    /**
     * @param logTag : Log tag for Log() method
     * @param e : Any exception
     */
    fun dLog(logTag: String, e: Exception) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_ERROR) {
            var stackTrace = ""
            for (element in e.stackTrace) {
                stackTrace += """
                    $element
                    
                    """.trimIndent()
            }
            Log.e(
                logTagBase() + logTag,
                """
                    Exception: 
                    $e
                    Message: ${e.message}
                    Trace: $stackTrace
                    """.trimIndent()
            )
        }
    }

    /**
     * @param logTag : Log tag for Log() method
     * @param e : Any exception
     */
    fun error(logTag: String, e: Exception) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_ERROR) {
            var stackTrace = ""
            for (element in e.stackTrace) {
                stackTrace += """
                    $element
                    
                    """.trimIndent()
            }
            Log.e(
                logTagBase() + logTag,
                """
                    Exception: 
                    $e
                    Message: ${e.message}
                    Trace: $stackTrace
                    """.trimIndent()
            )
        }
    }

    /**
     * @param logTag : Log tag for Log() method
     * @param e : Any exception
     * @param message
     */
    fun error(logTag: String, message: String, e: Exception) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_ERROR) {
            var stackTrace = ""
            for (element in e.stackTrace) {
                stackTrace += """
                    $element
                    
                    """.trimIndent()
            }
            Log.e(
                logTagBase() + logTag,
                """
                    App Message:$message
                    Exception: 
                    $e
                    Message: ${e.message}
                    Trace: $stackTrace
                    """.trimIndent()
            )
        }
    }

    /**
     * @param logTag
     * @param message
     */
    fun dLog(logTag: String, message: String?) {
        if (loggingIsEnabled() && mLogLevel <= LOG_LEVEL_DEBUG) {
            Log.d(logTagBase() + logTag, message!!)
        }
    }
}
