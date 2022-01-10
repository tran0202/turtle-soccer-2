package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.RowLeagueBinding

import com.mmtran.turtlesoccer.models.League

class LeaguesAdapter(context: Context?, leagueList: List<League?>) :
    RecyclerView.Adapter<LeaguesAdapter.ViewHolder>() {

    private val _context = context
    private val _leagueList: List<League?> = leagueList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var matchesAdapter: MatchesAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowLeagueBinding = RowLeagueBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.leagueNameTextView.visibility = if (_leagueList[position]!!.hideLeagueName!!) View.GONE else View.VISIBLE
        holder.leagueNameTextView.text = _leagueList[position]!!.name
        holder.leagueNameTextView.setTextColor(ContextCompat.getColor(_context!!, _leagueList[position]!!.getLeagueColor()))

        val recyclerView: RecyclerView = holder.matchListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        matchesAdapter = MatchesAdapter(_context, _leagueList[position]!!.matches!!)
        recyclerView.adapter = matchesAdapter
    }

    override fun getItemCount(): Int {
        return _leagueList.size
    }

    inner class ViewHolder internal constructor(binding: RowLeagueBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var leagueNameTextView: TextView = binding.leagueName
        var matchListRecyclerView: RecyclerView = binding.matchList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _leagueList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, leagueList: List<League?>, position: Int)
    }
}
