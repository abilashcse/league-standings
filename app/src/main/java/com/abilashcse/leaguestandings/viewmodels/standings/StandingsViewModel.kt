package com.abilashcse.leaguestandings.viewmodels.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abilashcse.leaguestandings.data.api.APICallback
import com.abilashcse.leaguestandings.data.api.StandingsResponse
import com.abilashcse.leaguestandings.data.model.Standing
import com.abilashcse.leaguestandings.data.model.StandingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//https://api.football-data.org/v2/competitions/2013/standings
@HiltViewModel
class StandingsViewModel @Inject constructor(var repository: StandingsRepository): ViewModel() {
    private val _teams = MutableLiveData<List<Standing>>().apply { value = emptyList() }
    val teams: LiveData<List<Standing>> = _teams

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun getStandings(competitionId: Int) {
        _isViewLoading.value = true
        repository.getStandings(competitionId, object : APICallback<StandingsResponse> {

            override fun onSuccess(data: StandingsResponse) {
                _isViewLoading.value = false
                if (data.standings.isEmpty()) {
                    _isEmptyList.value = true

                } else {
                    _teams.value = data.standings
                }
            }

            override fun onError(error: String) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }
        })
    }
}
