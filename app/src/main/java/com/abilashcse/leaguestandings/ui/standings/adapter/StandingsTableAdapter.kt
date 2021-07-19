package com.abilashcse.leaguestandings.ui.standings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abilashcse.leaguestandings.data.model.Table
import com.abilashcse.leaguestandings.databinding.TeamCardBinding
import com.abilashcse.leaguestandings.utils.loadSvg
import com.abilashcse.logger.DLog


class StandingsTableAdapter(private val items: List<Table>) :
    RecyclerView.Adapter<StandingsTableAdapter.StandingsViewHolder>() {

    inner class StandingsViewHolder(val binding: TeamCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        val binding = TeamCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StandingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {

        with (holder) {
            with (items[position]) {
                binding.positionNumber.text = this.position.toString()
                DLog.dLog(LOG_TAG, "Position $position Load ${this.team.crestUrl} ")
                this.team.crestUrl?.let { binding.crustImage.loadSvg(it) }
                binding.teamName.text = this.team.name
                binding.matchPlayed.text = this.playedGames.toString()
                binding.matchWon.text = this.won.toString()
                binding.matchLost.text = this.lost.toString()
                binding.matchDraw.text = this.draw.toString()
                binding.points.text = this.points.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }



    companion object {
        const val LOG_TAG = "StandingsTableAdapter"
    }
}


