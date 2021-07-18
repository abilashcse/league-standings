package com.abilashcse.leaguestandings.data.api

import com.abilashcse.leaguestandings.data.model.Competition
import com.abilashcse.leaguestandings.data.model.Standing

data class StandingsResponse(val competition: Competition, val standings: List<Standing>)
