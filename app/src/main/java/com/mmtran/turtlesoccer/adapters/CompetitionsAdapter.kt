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
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding
import com.mmtran.turtlesoccer.databinding.RowCompetitionBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition

class CompetitionsAdapter(context: Context?, competitionList: List<Competition?>) :
    RecyclerView.Adapter<CompetitionsAdapter.ViewHolder>() {

    private val _context = context
    private var _competitionList: List<Competition?> = competitionList
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private var compTournamentsAdapter: CompTournamentsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompetitionBinding = RowCompetitionBinding.inflate(mInflater, parent, false)
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
            holder.competitionCurrentChampionsLabelTextView.text = _context!!.getString(R.string.competition_current_champions_label);
        } else {
            holder.competitionCurrentChampionsLabelTextView.text = _context!!.getString(R.string.competition_last_champions_label);
        }

        if (_competitionList[position]!!.isClubCompetition()) {
            firebaseStorageLoader!!.loadImage(
                _context,
                holder.fragmentFlagNameBinding.clubLogo,
                "club_logos/" + _competitionList[position]!!.currentChampionClub!!.logoFilename
            )
            firebaseStorageLoader!!.loadImage(
                _context,
                holder.fragmentFlagNameBinding.flag,
                "flags/" + _competitionList[position]!!.currentChampionClub!!.nation!!.flagFilename
            )
            holder.fragmentFlagNameBinding.name.text = _competitionList[position]!!.currentChampionClub!!.name
        } else {
            holder.fragmentFlagNameBinding.clubLogo.visibility = View.GONE
            firebaseStorageLoader!!.loadImage(
                _context,
                holder.fragmentFlagNameBinding.flag,
                "flags/" + _competitionList[position]!!.currentChampionNation!!.flagFilename
            )
            holder.fragmentFlagNameBinding.name.text = _competitionList[position]!!.currentChampionNation!!.name
        }

        holder.descriptionTextView.text = if (_competitionList[position]!!.descriptions!!.isNotEmpty()) _competitionList[position]!!.descriptions!![0] else ""

        val recyclerView: RecyclerView = holder.compTournamentListRecyclerView
        val numberOfColumns = 5
        recyclerView.layoutManager = GridLayoutManager(_context, numberOfColumns)
        compTournamentsAdapter = CompTournamentsAdapter(_context, _competitionList[position]!!.tournamentList!!)
        recyclerView.adapter = compTournamentsAdapter
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
        var descriptionTextView: TextView = binding.description
        var compTournamentListRecyclerView: RecyclerView = binding.compTournamentList

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): Competition? {
        return _competitionList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
