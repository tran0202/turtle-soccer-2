package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding
import com.mmtran.turtlesoccer.databinding.RowCompetitionBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.utils.TeamUtil.renderFlagName

class CompetitionsAdapter(context: Context?, competitionList: List<Competition?>) :
    RecyclerView.Adapter<CompetitionsAdapter.ViewHolder>() {

    private val _context = context
    private var _competitionList: List<Competition?> = competitionList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var compTournamentsAdapter: CompTournamentsAdapter? = null
    private var compSuccessfulTeamsAdapter: CompSuccessfulTeamsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompetitionBinding = RowCompetitionBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.competitionTrophyImageView,
            _competitionList[position]!!.logoPath + "/" + _competitionList[position]!!.trophyFilename
        )

        holder.competitionNameTextView.text = _competitionList[position]!!.name
        holder.competitionTeamCountTextView.text = _context!!.getString(R.string.competition_team_count, _competitionList[position]!!.teamCount.toString())

        if (_competitionList[position]!!.currentChampions !== null) {
            holder.competitionCurrentChampionsLabelTextView.text = _context.getString(R.string.competition_current_champions_label)
            renderFlagName(_context, holder.fragmentFlagNameBinding, _competitionList[position]!!.currentChampions!!.team)
            holder.titleCountTextView.text = _context.getString(R.string.competition_title_count, _competitionList[position]!!.currentChampions!!.titleCount.toString())
        } else {
            holder.competitionCurrentChampionsLabelTextView.text = _context.getString(R.string.competition_last_champions_label)
            renderFlagName(_context, holder.fragmentFlagNameBinding, _competitionList[position]!!.lastChampions!!.team)
            holder.titleCountTextView.text = _context.getString(R.string.competition_title_count, _competitionList[position]!!.lastChampions!!.titleCount.toString())
        }

        holder.mostSuccessfulTeamLabelTextView.text = _context.getString(R.string.competition_most_successful_team_label)

        val recyclerView1: RecyclerView = holder.compSuccessfulTeamListRecyclerView
        recyclerView1.layoutManager = LinearLayoutManager(_context)
        compSuccessfulTeamsAdapter = CompSuccessfulTeamsAdapter(_context, _competitionList[position]!!.mostSuccessfulTeams!!)
        recyclerView1.adapter = compSuccessfulTeamsAdapter

        holder.descriptionTextView.text = if (_competitionList[position]!!.descriptions!!.isNotEmpty()) _competitionList[position]!!.descriptions!![0] else ""

        val recyclerView2 = holder.compTournamentListRecyclerView
        val numberOfColumns = 5
        recyclerView2.layoutManager = GridLayoutManager(_context, numberOfColumns)
        compTournamentsAdapter = CompTournamentsAdapter(_context, _competitionList[position]!!.tournamentList!!)
        recyclerView2.adapter = compTournamentsAdapter
    }

    override fun getItemCount(): Int {
        return _competitionList.size
    }

    inner class ViewHolder internal constructor(binding: RowCompetitionBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var competitionTrophyImageView: ImageView = binding.competitionTrophy
        var competitionNameTextView: TextView = binding.competitionName
        var competitionTeamCountTextView: TextView = binding.competitionTeamCount
        var competitionCurrentChampionsLabelTextView: TextView = binding.competitionCurrentChampionsLabel
        var fragmentFlagNameBinding: FragmentFlagNameBinding = binding.flagName
        var titleCountTextView: TextView = binding.titleCount
        var mostSuccessfulTeamLabelTextView: TextView = binding.mostSuccessfulTeamLabel
        var compSuccessfulTeamListRecyclerView: RecyclerView = binding.compSuccessfulTeamList
        var descriptionTextView: TextView = binding.description
        var compTournamentListRecyclerView: RecyclerView = binding.compTournamentList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, absoluteAdapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): Competition? {
        return _competitionList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
