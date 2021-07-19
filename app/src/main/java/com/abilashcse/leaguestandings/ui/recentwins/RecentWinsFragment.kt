package com.abilashcse.leaguestandings.ui.recentwins

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abilashcse.leaguestandings.MainActivity
import com.abilashcse.leaguestandings.R
import com.abilashcse.leaguestandings.data.api.RecentWinsRemoteDataSource
import com.abilashcse.leaguestandings.data.model.Table
import com.abilashcse.leaguestandings.databinding.RecentMainFragmentBinding
import com.abilashcse.leaguestandings.ui.recentwins.adapter.RecentWinTableAdapter
import com.abilashcse.leaguestandings.viewmodels.recentwins.RecentWinsViewModel
import com.abilashcse.logger.DLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecentWinsFragment : Fragment() {

    @Inject
    lateinit var recentWinsDataSource: RecentWinsRemoteDataSource

    companion object {
        fun newInstance() = RecentWinsFragment()
        const val LOG_TAG = "RecentWinsFragment"
    }

    private val viewModel: RecentWinsViewModel by viewModels()

    private lateinit var mainFragmentView: RecentMainFragmentBinding
    private lateinit var recentWinTableAdapter: RecentWinTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentView = RecentMainFragmentBinding.inflate(layoutInflater)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.setDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_700
                )
            )
        )
        with(mainFragmentView.recentTableList) {
            this.addItemDecoration(divider)
            this.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        mainFragmentView.recentHeader.headerLayout.visibility = View.INVISIBLE
        mainFragmentView.dividerLine.visibility = View.INVISIBLE
        return mainFragmentView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.table.observe(viewLifecycleOwner, tableObserver())
        viewModel.getRecentStatus(MainActivity.COMPETITION_ID)
    }

    private fun tableObserver(): (t: ArrayList<Table>) -> Unit =
        {
            DLog.dLog(LOG_TAG, "table size = ${it.size}")
            recentWinTableAdapter = RecentWinTableAdapter(it)
            mainFragmentView.message.text = viewModel.teams.value?.competition?.name ?: ""
            mainFragmentView.recentTableList.adapter = recentWinTableAdapter
            mainFragmentView.recentHeader.headerLayout.visibility = View.VISIBLE
            mainFragmentView.dividerLine.visibility = View.VISIBLE
            mainFragmentView.progressLoader.visibility = View.GONE
        }

}
