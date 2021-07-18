package com.abilashcse.leaguestandings.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abilashcse.leaguestandings.data.api.StandingsRemoteDataSource
import com.abilashcse.leaguestandings.databinding.StandingsMainFragmentBinding
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
    private lateinit var mainFragmentView: StandingsMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentView = StandingsMainFragmentBinding.inflate(layoutInflater)

        return mainFragmentView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.standings.observe(viewLifecycleOwner, {
            DLog.dLog("competition.name = ${it.competition.name}")
            mainFragmentView.message.text = it.competition.name
            mainFragmentView.header.seasonName.text = it.standings[0].stage.replace("_", " ")
            DLog.dLog("Standings size = ${it.standings.size}")
            DLog.dLog("Standings size = ${it.standings.size}")
        })
        viewModel.getStandings(2013)
    }

}
