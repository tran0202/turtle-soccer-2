package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.databinding.RowMatchdayBinding
import com.mmtran.turtlesoccer.models.Matchday
import com.mmtran.turtlesoccer.utils.CommonUtil

class MatchdaysAdapter(context: Context?, matchdayList: List<Matchday?>) :
    RecyclerView.Adapter<MatchdaysAdapter.ViewHolder>() {

    private val _context = context
    private val _matchdayList: List<Matchday?> = matchdayList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var matchesAdapter: MatchesAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowMatchdayBinding = RowMatchdayBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.matchdayNameTextView.text = CommonUtil.renderDate(_matchdayList[position]!!.name)
        holder.matchdayNameTextView.paintFlags = holder.matchdayNameTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val recyclerView: RecyclerView = holder.matchListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        matchesAdapter = MatchesAdapter(_context, _matchdayList[position]!!.matches!!)
        recyclerView.adapter = matchesAdapter
    }

    override fun getItemCount(): Int {
        return _matchdayList.size
    }

    inner class ViewHolder internal constructor(binding: RowMatchdayBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var matchdayNameTextView: TextView = binding.matchdayName
        var matchListRecyclerView: RecyclerView = binding.matchList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _matchdayList, absoluteAdapterPosition)
        }

        init {
//            root.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, matchdayList: List<Matchday?>, position: Int)
    }
}
