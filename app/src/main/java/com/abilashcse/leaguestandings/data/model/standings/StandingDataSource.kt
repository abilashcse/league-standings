package com.abilashcse.leaguestandings.data.model.standings

import com.abilashcse.leaguestandings.data.api.APICallback
import com.abilashcse.leaguestandings.data.api.StandingsResponse

interface StandingDataSource {
    fun getStandings(competitionId: Int, callback: APICallback<StandingsResponse>)
}
