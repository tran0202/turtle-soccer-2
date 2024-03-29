package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentLabelTeam2Binding
import com.mmtran.turtlesoccer.databinding.FragmentWrapLabelFieldBinding

import com.mmtran.turtlesoccer.databinding.RowTournamentBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class TournamentsAdapter(context: Context?, tournamentList: List<Tournament?>) :
    RecyclerView.Adapter<TournamentsAdapter.ViewHolder>() {

    private val _context = context
    private var _tournamentList: List<Tournament?> = tournamentList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowTournamentBinding = RowTournamentBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tournament: Tournament = _tournamentList[position]!!
        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.tournamentLogoImageView,
            tournament.competition?.logoPath + "/" + tournament.details!!.logoFilename
        )

        holder.tournamentNameTextView.text = tournament.name

        val tournamentDates = TournamentUtil.renderDates(_context, tournament)
        CommonUtil.renderField(tournamentDates, holder.tournamentDatesTextView)

        val competitionDates = TournamentUtil.renderQualifyingCompetitionDates(_context, tournament)
        CommonUtil.renderField(competitionDates, holder.competitionDatesTextView)

        val finalDates = TournamentUtil.renderLeagueFinalDates(_context, tournament)
        CommonUtil.renderField(finalDates, holder.finalDatesTextView)

        val teamCount = TournamentUtil.renderTeamCount(_context, tournament)
        CommonUtil.renderWrapLabelField(_context, teamCount, holder.teamCountFragmentWrapLabelFieldBinding, R.string.tournament_team_count_label)

        val totalTeamCount = TournamentUtil.renderTotalTeamCount(_context, tournament)
        CommonUtil.renderWrapLabelField(_context, totalTeamCount, holder.totalTeamCountFragmentWrapLabelFieldBinding, R.string.tournament_team_count_label)

        val totalPlusTransferTeamCount = TournamentUtil.renderTotalPlusTransferTeamCount(_context, tournament)
        CommonUtil.renderWrapLabelField(_context, totalPlusTransferTeamCount, holder.totalPlusTransferTeamCountFragmentWrapLabelFieldBinding, R.string.tournament_team_count_label)

        CommonUtil.renderLabelTeam2(_context, tournament.tournamentChampionsTeam(),
            holder.championsFragmentLabelTeam2Binding, R.string.champions_label)
    }

    override fun getItemCount(): Int {
        return _tournamentList.size
    }

    inner class ViewHolder internal constructor(binding: RowTournamentBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var tournamentLogoImageView: ImageView = binding.tournamentLogo
        var tournamentNameTextView: TextView = binding.tournamentName
        var tournamentDatesTextView: TextView = binding.tournamentDates
        var competitionDatesTextView: TextView = binding.competitionDates
        var finalDatesTextView: TextView = binding.finalDates
        var teamCountFragmentWrapLabelFieldBinding: FragmentWrapLabelFieldBinding = binding.teamCount
        var totalTeamCountFragmentWrapLabelFieldBinding: FragmentWrapLabelFieldBinding = binding.totalTeamCount
        var totalPlusTransferTeamCountFragmentWrapLabelFieldBinding: FragmentWrapLabelFieldBinding = binding.totalPlusTransferTeamCount
        var championsFragmentLabelTeam2Binding: FragmentLabelTeam2Binding = binding.champions

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _tournamentList, absoluteAdapterPosition)
        }

        init {
            tournamentLogoImageView.setOnClickListener(this)
            tournamentNameTextView.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int)
    }
}
