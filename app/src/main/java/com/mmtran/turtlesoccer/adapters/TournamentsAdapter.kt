package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagNameBinding

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

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.tournamentLogoImageView,
            _tournamentList[position]!!.competition!!.logoPath + "/" + _tournamentList[position]!!.details!!.logoFilename
        )

        holder.tournamentNameTextView.text = _tournamentList[position]!!.name

        val tournamentDates = TournamentUtil.renderDates(_context, _tournamentList[position])
        CommonUtil.renderField(tournamentDates, holder.tournamentDatesTextView)

        val competitionDates = TournamentUtil.renderQualifyingCompetitionDates(_context, _tournamentList[position])
        CommonUtil.renderField(competitionDates, holder.competitionDatesTextView)

        val finalDates = TournamentUtil.renderLeagueFinalDates(_context, _tournamentList[position])
        CommonUtil.renderField(finalDates, holder.finalDatesTextView)

        val teamCount = TournamentUtil.renderTeamCount(_context, _tournamentList[position])
        CommonUtil.renderLabelField(teamCount, holder.teamCountLabelTextView, holder.teamCountTextView)

        val totalTeamCount = TournamentUtil.renderTotalTeamCount(_context, _tournamentList[position])
        CommonUtil.renderLabelField(totalTeamCount, holder.totalTeamCountLabelTextView, holder.totalTeamCountTextView)

        val totalPlusTransferTeamCount = TournamentUtil.renderTotalPlusTransferTeamCount(_context, _tournamentList[position])
        CommonUtil.renderLabelField(totalPlusTransferTeamCount, holder.totalPlusTransferTeamCountLabelTextView, holder.totalPlusTransferTeamCountTextView)

        TournamentUtil.renderFinalStandings(_context, _tournamentList[position]!!.finalStandings!!.championTeam,
            holder.championsLabelTextView, holder.championsLinearLayout, holder.championsFlagNameBinding)
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
        var teamCountLabelTextView: TextView = binding.teamCountLabel
        var teamCountTextView: TextView = binding.teamCount
        var totalTeamCountLabelTextView: TextView = binding.totalTeamCountLabel
        var totalTeamCountTextView: TextView = binding.totalTeamCount
        var totalPlusTransferTeamCountLabelTextView: TextView = binding.totalPlusTransferTeamCountLabel
        var totalPlusTransferTeamCountTextView: TextView = binding.totalPlusTransferTeamCount
        var championsLabelTextView: TextView = binding.championsLabel
        var championsLinearLayout: LinearLayout = binding.champions
        var championsFlagNameBinding: FragmentTeamFlagNameBinding = binding.championsFlagName

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
