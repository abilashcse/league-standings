package com.abilashcse.leaguestandings

import android.app.Application
import com.abilashcse.logger.DLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LSApplication: Application() {
    companion object {
        const val LOG_TAG=  "LSApplication"
    }
    override fun onCreate() {
        super.onCreate()
        DLog.dLog(LOG_TAG, "onCreate application")
    }
}
