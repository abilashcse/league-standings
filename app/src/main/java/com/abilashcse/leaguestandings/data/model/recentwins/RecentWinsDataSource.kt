package com.abilashcse.leaguestandings.data.model.recentwins

import com.abilashcse.leaguestandings.data.api.APICallback
import com.abilashcse.leaguestandings.data.api.MatchResponse
import com.abilashcse.leaguestandings.data.api.StandingsResponse
import com.abilashcse.leaguestandings.data.api.TeamsResponse

interface RecentWinsDataSource {
    fun getTeams(competitionId: Int, callback: APICallback<TeamsResponse>)
    fun getMatches(competitionId: Int, fromDate: String, toDate:String, callback: APICallback<MatchResponse>)
}
