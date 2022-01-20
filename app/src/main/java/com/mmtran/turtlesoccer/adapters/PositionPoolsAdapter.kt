package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowPositionPoolsBinding

import com.mmtran.turtlesoccer.models.PositionPool

class PositionPoolsAdapter(context: Context?, positionPoolList: List<PositionPool?>) :
    RecyclerView.Adapter<PositionPoolsAdapter.ViewHolder>() {

    private val _context = context
    private val _positionPoolList: List<PositionPool?> = positionPoolList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var poolsAdapter: PoolsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowPositionPoolsBinding = RowPositionPoolsBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val positionPool: PositionPool = _positionPoolList[position]!!

        val recyclerView: RecyclerView = holder.poolListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        poolsAdapter = PoolsAdapter(_context, positionPool.pools!!)
        recyclerView.adapter = poolsAdapter
    }

    override fun getItemCount(): Int {
        return _positionPoolList.size
    }

    inner class ViewHolder internal constructor(binding: RowPositionPoolsBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var poolListRecyclerView: RecyclerView = binding.poolList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _positionPoolList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, positionPoolList: List<PositionPool?>, position: Int)
    }
}
