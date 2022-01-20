package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.databinding.RowRoundRankingsBinding
import com.mmtran.turtlesoccer.models.RoundRanking

class RoundRankingsAdapter(context: Context?, roundRankingList: List<RoundRanking?>) :
    RecyclerView.Adapter<RoundRankingsAdapter.ViewHolder>() {

    private val _context = context
    private val _roundRankingList: List<RoundRanking?> = roundRankingList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var positionPoolsAdapter: PositionPoolsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowRoundRankingsBinding = RowRoundRankingsBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val roundRanking: RoundRanking = _roundRankingList[position]!!
        holder.roundNameTextView.visibility = if (roundRanking.showRoundRankingName()) View.VISIBLE else View.GONE
        holder.roundNameTextView.text = roundRanking.name

        if (roundRanking.processed!!) {
            holder.positionPoolListRecyclerView.visibility = View.VISIBLE
            val recyclerView: RecyclerView = holder.positionPoolListRecyclerView
            recyclerView.layoutManager = GridLayoutManager(_context, 1)
            positionPoolsAdapter = PositionPoolsAdapter(_context, roundRanking.positionPools!!)
            recyclerView.adapter = positionPoolsAdapter
        } else {
            holder.positionPoolListRecyclerView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return _roundRankingList.size
    }

    inner class ViewHolder internal constructor(binding: RowRoundRankingsBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var roundNameTextView: TextView = binding.roundName
        var positionPoolListRecyclerView: RecyclerView = binding.positionPoolList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _roundRankingList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, roundRankingList: List<RoundRanking?>, position: Int)
    }
}
