package com.abilashcse.leaguestandings.data.api

import com.abilashcse.leaguestandings.MainActivity
import com.abilashcse.leaguestandings.data.model.standings.StandingDataSource
import com.abilashcse.logger.DLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class StandingsRemoteDataSource @Inject constructor(private val service: FootballAPIServices) :
    StandingDataSource {
    private var call: Call<StandingsResponse>?= null

    override fun getStandings(competitionId: Int, callback: APICallback<StandingsResponse>) {
        DLog.dLog("getStandings for competitionId $competitionId")
        call = service.standings(MainActivity.API_TOKEN,competitionId)
        call?.enqueue(object :Callback<StandingsResponse> {
            override fun onResponse(
                call: Call<StandingsResponse>,
                response: Response<StandingsResponse>
            ) {
                DLog.dLog("getStandings onResponse $competitionId")
                response.body()?.let {
                    DLog.dLog("getStandings onResponse body$competitionId")
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Invalid Response")
                    }
                } ?: run {
                    val errorJSON = JSONObject(response.errorBody()!!.string())
                    callback.onError("No response ${errorJSON["message"]}")
                }
            }

            override fun onFailure(call: Call<StandingsResponse>, t: Throwable) {
                DLog.dLog("onFailure for competitionId $competitionId")
                t.message?.let { callback.onError(it) }
            }

        })

    }


}
