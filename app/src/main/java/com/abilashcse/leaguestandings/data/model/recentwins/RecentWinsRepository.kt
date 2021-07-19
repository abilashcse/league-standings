package com.abilashcse.leaguestandings.data.model.recentwins

import com.abilashcse.leaguestandings.data.api.APICallback
import com.abilashcse.leaguestandings.data.api.MatchResponse
import com.abilashcse.leaguestandings.data.api.TeamsResponse
import com.abilashcse.logger.DLog
import java.text.SimpleDateFormat
import java.util.*

class RecentWinsRepository (private val recentWinsDataSource: RecentWinsDataSource){
    fun getTeams(competitionId: Int, callback: APICallback<TeamsResponse>) {
        recentWinsDataSource.getTeams(competitionId, callback)
    }
    fun getMatches(competitionId: Int, fromDate: Date, toDate:Date, callback: APICallback<MatchResponse> ) {

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormatter.format(fromDate)
        val dateFrom = dateFormatter.format(fromDate)
        val dateTo = dateFormatter.format(toDate)
        DLog.dLog("getDateRange Last updated  toDate = $dateTo")
        DLog.dLog("getDateRange Last updated  fromDate = $dateFrom")

        recentWinsDataSource.getMatches(competitionId, fromDate = dateFrom, toDate = dateTo, callback)
    }
}
