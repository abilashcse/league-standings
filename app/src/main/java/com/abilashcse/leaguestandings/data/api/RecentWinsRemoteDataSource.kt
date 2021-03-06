package com.abilashcse.leaguestandings.data.api

import com.abilashcse.leaguestandings.MainActivity
import com.abilashcse.leaguestandings.data.model.recentwins.RecentWinsDataSource
import com.abilashcse.logger.DLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RecentWinsRemoteDataSource @Inject constructor(private val service: FootballAPIServices) :
    RecentWinsDataSource {
    private var getTeamsCall: Call<TeamsResponse>?= null
    private var getMatchesCall: Call<MatchResponse>?= null

    override fun getTeams(competitionId: Int, callback: APICallback<TeamsResponse>) {
        DLog.dLog("getTeams for competitionId $competitionId")
        getTeamsCall = service.teams(MainActivity.API_TOKEN,competitionId)
        getTeamsCall?.enqueue(object : Callback<TeamsResponse> {
            override fun onResponse(call: Call<TeamsResponse>, response: Response<TeamsResponse>) {
                response.body()?.let {
                    DLog.dLog("getTeams onResponse body$competitionId")
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

            override fun onFailure(call: Call<TeamsResponse>, t: Throwable) {
                DLog.dLog("onFailure for competitionId $competitionId")
                t.message?.let { callback.onError(it) }
            }

        })
    }

    override fun getMatches(
        competitionId: Int,
        fromDate: String,
        toDate: String,
        callback: APICallback<MatchResponse>
    ) {
        getMatchesCall = service.matches(MainActivity.API_TOKEN,competitionId,
            fromDate, toDate)
        getMatchesCall?.enqueue(object : Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                response.body()?.let {
                    DLog.dLog("getTeams onResponse body$competitionId")
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Invalid Response")
                    }
                }
            }
            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                DLog.dLog("onFailure for competitionId $competitionId")
                t.message?.let { callback.onError(it) }
            }

        })
    }


}
