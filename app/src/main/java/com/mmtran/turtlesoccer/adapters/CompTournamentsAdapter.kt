package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowCompTournamentBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Tournament

class CompTournamentsAdapter(context: Context?, tournamentList: List<Tournament?>) :
    RecyclerView.Adapter<CompTournamentsAdapter.ViewHolder>() {

    private val _context = context
    private val _tournamentList: List<Tournament?> = tournamentList
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompTournamentBinding = RowCompTournamentBinding.inflate(mInflater, parent, false)
        return ViewHolder(binding)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.tournamentLogoImageView,
            "wc_logos/" + _tournamentList[position]!!.details!!.logoFilename
        )
    }

    // total number of cells
    override fun getItemCount(): Int {
        return _tournamentList.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(binding: RowCompTournamentBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var tournamentLogoImageView: ImageView = binding.tournamentLogos

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Tournament? {
        return _tournamentList[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
