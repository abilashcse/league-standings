package com.abilashcse.leaguestandings.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FootballAPIServices {
    @GET("competitions/{id}/standings")
    fun standings(@Header("X-Auth-Token") token: String, @Path("id") competitionId: Int): Call<StandingsResponse>
}
