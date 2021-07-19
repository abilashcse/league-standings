package com.abilashcse.leaguestandings.ui.recentwins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abilashcse.leaguestandings.data.model.Table
import com.abilashcse.leaguestandings.databinding.RecentTeamCardBinding
import com.abilashcse.leaguestandings.databinding.TeamCardBinding
import com.abilashcse.leaguestandings.utils.loadSvg
import com.abilashcse.logger.DLog


class RecentWinTableAdapter(private val items: List<Table>) :
    RecyclerView.Adapter<RecentWinTableAdapter.RecentWinViewHolder>() {

    inner class RecentWinViewHolder(val binding: RecentTeamCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentWinViewHolder {
        val binding = RecentTeamCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentWinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentWinViewHolder, position: Int) {

        with (holder) {
            with (items[position]) {
                binding.recentPositionNumber.text = this.position.toString()
                DLog.dLog(LOG_TAG, "Position $position Load ${this.team.crestUrl} ")
                this.team.crestUrl?.let { binding.recentCrustImage.loadSvg(it) }
                binding.recentPositionNumber.text = (position+1).toString()
                binding.recentTeamName.text = this.team.name
                binding.recentMatchWon.text = this.won.toString()
                binding.recentMatchLost.text = this.lost.toString()
                binding.recentMatchDraw.text = this.draw.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        const val LOG_TAG = "RecentWinTableAdapter"
    }
}


