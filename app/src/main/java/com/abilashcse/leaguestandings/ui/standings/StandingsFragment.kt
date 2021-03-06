package com.abilashcse.leaguestandings.ui.standings

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilashcse.leaguestandings.MainActivity
import com.abilashcse.leaguestandings.R
import com.abilashcse.leaguestandings.data.api.StandingsRemoteDataSource
import com.abilashcse.leaguestandings.data.api.StandingsResponse
import com.abilashcse.leaguestandings.databinding.StandingsMainFragmentBinding
import com.abilashcse.leaguestandings.ui.recentwins.RecentWinsFragment
import com.abilashcse.leaguestandings.ui.standings.adapter.StandingsTableAdapter
import com.abilashcse.leaguestandings.viewmodels.standings.StandingsViewModel
import com.abilashcse.logger.DLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    @Inject
    lateinit var standingDataSource: StandingsRemoteDataSource

    companion object {
        fun newInstance() = StandingsFragment()
        const val LOG_TAG = "StandingsFragment"
    }

    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var mainFragmentView: StandingsMainFragmentBinding
    private lateinit var standingsAdapter: StandingsTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentView = StandingsMainFragmentBinding.inflate(layoutInflater)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.setDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_700
                )
            )
        )
        with(mainFragmentView.tableList) {
            this.addItemDecoration(divider)
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        mainFragmentView.header.headerLayout.visibility = View.INVISIBLE
        mainFragmentView.dividerLine.visibility = View.INVISIBLE
        return mainFragmentView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.standings.observe(viewLifecycleOwner, standingsObserver())
        viewModel.onMessageError.observe(viewLifecycleOwner, errorObserver())
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver())
        viewModel.getStandings(MainActivity.COMPETITION_ID)
    }

    private fun standingsObserver(): (t: StandingsResponse) -> Unit = {
        DLog.dLog(LOG_TAG, "competition.name = ${it.competition.name}")
        mainFragmentView.message.text = it.competition.name
        mainFragmentView.header.seasonName.text = it.standings[0].stage.replace("_", " ")
        DLog.dLog(LOG_TAG, "Standings size = ${it.standings.size}")
        DLog.dLog(LOG_TAG, "Standings table size = ${it.standings[0].table.size}")
        standingsAdapter = StandingsTableAdapter(it.standings[0].table)
        mainFragmentView.tableList.adapter = standingsAdapter
        mainFragmentView.header.headerLayout.visibility = View.VISIBLE
        mainFragmentView.dividerLine.visibility = View.VISIBLE
        mainFragmentView.progressLoader.visibility = View.GONE
    }
    private fun errorObserver(): (t: Any) -> Unit = {
        mainFragmentView.progressLoader.visibility = View.GONE
        val errorMessage:String = it as String
        DLog.error(RecentWinsFragment.LOG_TAG,"Error... $errorMessage")
        Toast.makeText(context, "Error while fetching data... $errorMessage", Toast.LENGTH_LONG).show()
    }

    private fun emptyListObserver(): (t: Boolean) -> Unit = {
        mainFragmentView.progressLoader.visibility = View.GONE
        Toast.makeText(context, "Empty data...", Toast.LENGTH_LONG).show()
    }

}
