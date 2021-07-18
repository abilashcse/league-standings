package com.abilashcse.leaguestandings

import android.app.Application
import com.abilashcse.logger.DLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LSApplication: Application() {
    private val LOG_TAG by lazy { "LSApplication" }
    override fun onCreate() {
        super.onCreate()
        DLog.dLog(LOG_TAG, "onCreate application")
    }
}
