package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding

import com.mmtran.turtlesoccer.databinding.RowTournamentBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.TeamUtil
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
        if (!tournamentDates.isNullOrEmpty()) {
            holder.tournamentDatesTextView.visibility = View.VISIBLE
            holder.tournamentDatesTextView.text = tournamentDates
        } else {
            holder.tournamentDatesTextView.visibility = View.GONE
        }

        val competitionDates = TournamentUtil.renderQualifyingCompetitionDates(_context, _tournamentList[position])
        if (!competitionDates.isNullOrEmpty()) {
            holder.competitionDatesTextView.visibility = View.VISIBLE
            holder.competitionDatesTextView.text = competitionDates
        } else {
            holder.competitionDatesTextView.visibility = View.GONE
        }

        val finalDates = TournamentUtil.renderLeagueFinalDates(_context, _tournamentList[position])
        if (!finalDates.isNullOrEmpty()) {
            holder.finalDatesTextView.visibility = View.VISIBLE
            holder.finalDatesTextView.text = finalDates
        } else {
            holder.finalDatesTextView.visibility = View.GONE
        }

        if (_tournamentList[position]!!.details!!.teamCount != null) {
            holder.tournamentTeamCountTextView.visibility = View.VISIBLE
            holder.tournamentTeamCountTextView.text = _context!!.getString(R.string.tournament_team_count, _tournamentList[position]!!.details!!.teamCount.toString())
        } else {
            holder.tournamentTeamCountTextView.visibility = View.GONE
        }

        if (_tournamentList[position]!!.details!!.totalTeamCount != null && _tournamentList[position]!!.details!!.totalTransferTeamCount == null) {
            holder.totalTeamCountTextView.visibility = View.VISIBLE
            holder.totalTeamCountTextView.text = _context!!.getString(R.string.tournament_team_count, _tournamentList[position]!!.details!!.totalTeamCount.toString())
        } else {
            holder.totalTeamCountTextView.visibility = View.GONE
        }

        val totalPlusTransferTeamCount = TournamentUtil.renderTotalPlusTransferTeamCount(_context, _tournamentList[position])
        if (!totalPlusTransferTeamCount.isNullOrEmpty()) {
            holder.totalPlusTransferTeamCountTextView.visibility = View.VISIBLE
            holder.totalPlusTransferTeamCountTextView.text = totalPlusTransferTeamCount
        } else {
            holder.totalPlusTransferTeamCountTextView.visibility = View.GONE
        }

        if (_tournamentList[position]!!.finalStandings!!.championTeam != null) {
            holder.championsLabelTextView.visibility = View.VISIBLE
            holder.championsLinearLayout.visibility = View.VISIBLE
            TeamUtil.renderFlagName(
                _context,
                holder.fragmentFlagNameBinding,
                _tournamentList[position]!!.finalStandings!!.championTeam
            )
        } else {
            holder.championsLabelTextView.visibility = View.GONE
            holder.championsLinearLayout.visibility = View.GONE
        }
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
        var tournamentTeamCountTextView: TextView = binding.tournamentTeamCount
        var totalTeamCountTextView: TextView = binding.totalTeamCount
        var totalPlusTransferTeamCountTextView: TextView = binding.totalPlusTransferTeamCount
        var championsLabelTextView: TextView = binding.championsLabel
        var championsLinearLayout: LinearLayout = binding.champions
        var fragmentFlagNameBinding: FragmentFlagNameBinding = binding.flagName

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
