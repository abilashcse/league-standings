package com.abilashcse.leaguestandings.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballAPIServices {
    @GET("competitions/{id}/standings")
    fun standings(@Header("X-Auth-Token") token: String, @Path("id") competitionId: Int): Call<StandingsResponse>

    @GET("/v2/competitions/{id}/teams")
    fun teams(@Header("X-Auth-Token") token: String, @Path("id") competitionId: Int): Call<TeamsResponse>

    @GET("/v2/competitions/{id}/matches")
    fun matches(@Header("X-Auth-Token") token: String, @Path("id") competitionId: Int,
    @Query("dateFrom") fromDate:String, @Query("dateTo") toDate:String ): Call<MatchResponse>
}
