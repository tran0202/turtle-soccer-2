package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R

import com.mmtran.turtlesoccer.databinding.RowPathBinding
import com.mmtran.turtlesoccer.models.Path

class PathsAdapter(context: Context?, pathList: List<Path?>) :
    RecyclerView.Adapter<PathsAdapter.ViewHolder>() {

    private val _context = context
    private val _pathList: List<Path?> = pathList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var matchdaysAdapter: MatchdaysAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowPathBinding = RowPathBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val path: Path = _pathList[position]!!
        holder.pathNameTextView.visibility = if (path.hidePathName!!) View.GONE else View.VISIBLE
        holder.pathNameTextView.text = _context!!.getString(R.string.path_header, path.name)

        val recyclerView: RecyclerView = holder.matchdayListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        matchdaysAdapter = MatchdaysAdapter(_context, path.matchdayList!!)
        recyclerView.adapter = matchdaysAdapter
    }

    override fun getItemCount(): Int {
        return _pathList.size
    }

    inner class ViewHolder internal constructor(binding: RowPathBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var pathNameTextView: TextView = binding.pathName
        var matchdayListRecyclerView: RecyclerView = binding.matchdayList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _pathList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, pathList: List<Path?>, position: Int)
    }
}
