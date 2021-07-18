package com.abilashcse.leaguestandings.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.abilashcse.leaguestandings.R
import com.abilashcse.leaguestandings.data.api.StandingsRemoteDataSource
import com.abilashcse.leaguestandings.viewmodels.standings.StandingsViewModel
import com.abilashcse.logger.DLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StandingsFragment : Fragment() {
    val LOG_TAG = "StandingsFragment"
    @Inject lateinit var standingDataSource: StandingsRemoteDataSource
    companion object {
        fun newInstance() = StandingsFragment()
    }

    private val viewModel: StandingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.teams.observe(viewLifecycleOwner, {
            DLog.dLog("Standings size = ${it.size}")
        })
        viewModel.getStandings(2013)
    }

}
