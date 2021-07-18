package com.abilashcse.leaguestandings.data.model

import java.util.*


data class Area(val id:Int, val name:String)
data class Competition(val id: Int, val area: Area, val name: String, val lastUpdated: Date)
data class Team (val id: Int, val name: String, val crestUrl: String)
data class Table (val position: Int, val team: Team, val playedGames: Int, val won:Int,
                  val draw: Int, val lost: Int, val points: Int, val goalsFor: Int,
                  val goalsAgainst: Int, val goalDifference: Int)
data class Standing (val stage: String, val table: List<Table>)
