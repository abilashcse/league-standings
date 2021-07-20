package com.abilashcse.leaguestandings.viewmodels.recentwins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abilashcse.leaguestandings.data.api.APICallback
import com.abilashcse.leaguestandings.data.api.MatchResponse
import com.abilashcse.leaguestandings.data.api.TeamsResponse
import com.abilashcse.leaguestandings.data.model.DateRange
import com.abilashcse.leaguestandings.data.model.Season
import com.abilashcse.leaguestandings.data.model.Table
import com.abilashcse.leaguestandings.data.model.Winner
import com.abilashcse.leaguestandings.data.model.recentwins.RecentWinsRepository
import com.abilashcse.leaguestandings.viewmodels.LSBaseViewModel
import com.abilashcse.logger.DLog
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RecentWinsViewModel @Inject constructor(var repository: RecentWinsRepository) :
    LSBaseViewModel() {
    private val _teams = MutableLiveData<TeamsResponse>()
    val teams: LiveData<TeamsResponse> = _teams

    private val _matches = MutableLiveData<MatchResponse>()
    val matches: LiveData<MatchResponse> = _matches

    private val _table = MutableLiveData<ArrayList<Table>>()
    val table:LiveData<ArrayList<Table>> = _table

    fun getRecentStatus(competitionId: Int) {

        _isViewLoading.value = true
        repository.getTeams(competitionId, object : APICallback<TeamsResponse> {

            override fun onSuccess(data: TeamsResponse) {
                if (data.teams.isEmpty()) {
                    DLog.dLog("Empty Data")
                    _isEmptyList.value = true
                } else {
                    _teams.value = data
                    DLog.dLog("Last updated = ${data.competition.lastUpdated}")
                    val (fromDate, toDate) = getDateRange(data.season)
                    repository.getMatches(competitionId, fromDate, toDate, object: APICallback<MatchResponse> {
                        override fun onSuccess(data: MatchResponse) {
                            _isViewLoading.value = false
                            if (data.count == 0 ){
                                DLog.dLog("Empty Data count = 0")
                                _isEmptyList.value = true
                            } else {
                                _matches.value = data
                                val tableList:ArrayList<Table> = ArrayList()
                                for (team in teams.value?.teams!!) {
                                    tableList += Table(0, team, 0,0,0,0,0,0,0,0)
                                }
                                processMatchResults(data, tableList)
                                _table.value = tableList
                            }
                        }
                        override fun onError(error: String) {
                            _isViewLoading.value = false
                            _onMessageError.value = error
                        }

                    })
                }
            }

            override fun onError(error: String) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }
        })
    }

    private fun processMatchResults(
        data: MatchResponse,
        tableList: ArrayList<Table>
    ) {
        for (matches in data.matches) {
            DLog.dLog("Match won by ${matches.score.winner}")
            when (matches.score.winner) {
                Winner.HOME_TEAM -> {
                    DLog.dLog("Won by home team")
                    //Find the position of array list with id teamId
                    updateWin(tableList, matches.homeTeam.id)
                    updateLoss(tableList, matches.awayTeam.id)
                }
                Winner.AWAY_TEAM -> {
                    DLog.dLog("Won by away team")
                    updateWin(tableList, matches.awayTeam.id)
                    updateLoss(tableList, matches.homeTeam.id)
                }
                else -> {
                    DLog.dLog("Game is drawn")
                    updateDraw(tableList, matches.homeTeam.id)
                    updateDraw(tableList, matches.awayTeam.id)
                }
            }
        }

        //If the number of won games is same for more than 1 team
        //Sort the teams by less number of loss.
        tableList.sortBy { it.lost}
        tableList.sortByDescending { it.won}
    }

    private fun updateWin(
        tableList: ArrayList<Table>,
        teamId: Int
    ) {
        for (table in tableList) {
            if (table.team.id == teamId) {
                table.won += 1
            }
        }
    }
    private fun updateLoss(
        tableList: ArrayList<Table>,
        teamId: Int
    ) {
        for (table in tableList) {
            if (table.team.id == teamId) {
                table.lost += 1
            }
        }
    }
    private fun updateDraw(
        tableList: ArrayList<Table>,
        teamId: Int
    ) {
        for (table in tableList) {
            if (table.team.id == teamId) {
                table.draw += 1
            }
        }
    }

    /**
     * This function returns from and to date to find the last wins in 30 days.
     * If the season is ongoing then the toDate is today
     * Else toDate will the last day of the season
     * From date is find by subtracting 30 days from toDate
     */
    internal fun getDateRange(season: Season): DateRange {
        val dateNow = Date()
        var toDate = dateNow
        if (season.endDate < dateNow) {
            toDate = season.endDate
        }
        val calendar = Calendar.getInstance()
        calendar.time = toDate
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val fromDate = calendar.time
        return DateRange(from = fromDate, to = toDate)
    }

}
