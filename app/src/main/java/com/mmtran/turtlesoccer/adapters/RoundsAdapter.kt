package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.databinding.RowRoundBinding
import com.mmtran.turtlesoccer.models.Round

class RoundsAdapter(context: Context?, roundList: List<Round?>) :
    RecyclerView.Adapter<RoundsAdapter.ViewHolder>() {

    private val _context = context
    private val _roundList: List<Round?> = roundList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var pathsAdapter: PathsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowRoundBinding = RowRoundBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val round: Round = _roundList[position]!!
        holder.roundNameTextView.visibility = if (round.hideRoundName!!) View.GONE else View.VISIBLE
        holder.roundNameTextView.text = round.name

        val recyclerView: RecyclerView = holder.pathListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        pathsAdapter = PathsAdapter(_context, round.paths!!)
        recyclerView.adapter = pathsAdapter
    }

    override fun getItemCount(): Int {
        return _roundList.size
    }

    inner class ViewHolder internal constructor(binding: RowRoundBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var roundNameTextView: TextView = binding.roundName
        var pathListRecyclerView: RecyclerView = binding.pathList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _roundList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, roundList: List<Round?>, position: Int)
    }
}
