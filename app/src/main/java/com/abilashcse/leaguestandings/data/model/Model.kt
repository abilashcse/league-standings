package com.abilashcse.leaguestandings.data.model

import java.util.*

data class Area(val id:Int, val name:String)
data class Competition(val id: Int, val area: Area, val name: String, val lastUpdated: Date)
data class Team (val id: Int, val name: String, val crestUrl: String?)
data class Table (val position: Int, var team: Team, val playedGames: Int, var won:Int,
                  var draw: Int, var lost: Int, val points: Int, val goalsFor: Int,
                  val goalsAgainst: Int, val goalDifference: Int)
data class Standing (val stage: String, val table: List<Table>)

enum class Winner {
    HOME_TEAM,
    AWAY_TEAM
}
data class Season(val startDate:Date, val endDate: Date)
data class Score(val winner: Winner?)
data class Match(val id: Int, val score: Score, val homeTeam: Team, val awayTeam: Team)

data class DateRange(val from: Date, val to: Date)
