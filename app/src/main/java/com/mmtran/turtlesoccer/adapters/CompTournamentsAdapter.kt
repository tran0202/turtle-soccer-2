package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowCompTournamentBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Tournament

class CompTournamentsAdapter(context: Context?, tournamentList: List<Tournament?>) :
    RecyclerView.Adapter<CompTournamentsAdapter.ViewHolder>() {

    private val _context = context
    private val _tournamentList: List<Tournament?> = tournamentList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompTournamentBinding = RowCompTournamentBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tournament: Tournament = _tournamentList[position]!!
        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.tournamentLogoImageView,
            tournament.competition!!.logoPath + "/" + tournament.details!!.logoFilename
        )
        holder.shortYearTextView.text = tournament.shortYear
    }

    // total number of cells
    override fun getItemCount(): Int {
        return _tournamentList.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(binding: RowCompTournamentBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var tournamentLogoImageView: ImageView = binding.tournamentLogo
        var shortYearTextView: TextView = binding.shortYear

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _tournamentList, absoluteAdapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

//    // convenience method for getting data at click position
//    fun getItem(id: Int): Tournament? {
//        return _tournamentList[id]
//    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int)
    }
}
