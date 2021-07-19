package com.abilashcse.leaguestandings.data.api

import com.abilashcse.leaguestandings.data.model.*

data class StandingsResponse(val competition: Competition, val standings: List<Standing>)

data class TeamsResponse(val count: Int, val competition: Competition, val season: Season, val teams: List<Team>)

data class MatchResponse(val count: Int, val competition: Competition, val matches: List<Match>)
