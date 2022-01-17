package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.databinding.*
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class CompetitionsAdapter(context: Context?, competitionList: List<Competition?>) :
    RecyclerView.Adapter<CompetitionsAdapter.ViewHolder>(), CompTournamentsAdapter.ItemClickListener {

    private val _context = context
    private var _competitionList: List<Competition?> = competitionList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var compTournamentsAdapter: CompTournamentsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompetitionBinding = RowCompetitionBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val competition: Competition = _competitionList[position]!!
        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.competitionTrophyImageView,
            competition.logoPath + "/" + competition.trophyFilename
        )

        holder.competitionNameTextView.text = competition.name

        val teamCount = CompetitionUtil.renderTeamCount(competition)
        CommonUtil.renderWrapLabelField(_context, teamCount, holder.competitionCountFragmentWrapLabelFieldBinding, R.string.tournament_team_count_label)

        CommonUtil.renderLabelChampion(_context, competition.currentChampions,
            holder.currentChampionsFragmentLabelChampionBinding, R.string.competition_current_champions_label)

        CommonUtil.renderLabelChampion(_context, competition.lastChampions,
            holder.lastChampionsFragmentLabelChampionBinding, R.string.competition_last_champions_label)

        CommonUtil.renderChampionList(_context, competition.mostSuccessfulTeams!!,
            holder.mostSuccessfulTeamFragmentLabelChampionListBinding, R.string.competition_most_successful_team_label, R.string.competition_most_successful_teams_label)

        holder.descriptionTextView.text = if (competition.descriptions!!.isNotEmpty()) competition.descriptions!![0] else ""

        val recyclerView2 = holder.compTournamentListRecyclerView
        val numberOfColumns = 5
        recyclerView2.layoutManager = GridLayoutManager(_context, numberOfColumns)
        compTournamentsAdapter = CompTournamentsAdapter(_context, competition.tournamentList!!)
        compTournamentsAdapter!!.setClickListener(this)
        recyclerView2.adapter = compTournamentsAdapter
    }

    override fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int) {

        TournamentUtil.browseToFinalTournament(_context as MainActivity, tournamentList[position]!!)
    }

    override fun getItemCount(): Int {
        return _competitionList.size
    }

    inner class ViewHolder internal constructor(binding: RowCompetitionBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var competitionTrophyImageView: ImageView = binding.competitionTrophy
        var competitionNameTextView: TextView = binding.competitionName
        var competitionCountFragmentWrapLabelFieldBinding: FragmentWrapLabelFieldBinding = binding.competitionTeamCount
        var currentChampionsFragmentLabelChampionBinding: FragmentLabelChampionBinding = binding.currentChampions
        var lastChampionsFragmentLabelChampionBinding: FragmentLabelChampionBinding = binding.lastChampions
        var mostSuccessfulTeamFragmentLabelChampionListBinding: FragmentLabelChampionListBinding = binding.mostSuccessfulTeams
        var descriptionTextView: TextView = binding.description
        var compTournamentListRecyclerView: RecyclerView = binding.compTournamentList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _competitionList, absoluteAdapterPosition)
        }

        init {
            competitionTrophyImageView.setOnClickListener(this)
            competitionNameTextView.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int)
    }
}
