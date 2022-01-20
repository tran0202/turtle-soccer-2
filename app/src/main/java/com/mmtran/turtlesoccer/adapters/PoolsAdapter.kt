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
import com.mmtran.turtlesoccer.databinding.RowPoolBinding

import com.mmtran.turtlesoccer.models.Pool
import com.mmtran.turtlesoccer.utils.RankingUtil

class PoolsAdapter(context: Context?, poolList: List<Pool?>) :
    RecyclerView.Adapter<PoolsAdapter.ViewHolder>() {

    private val _context = context
    private val _poolList: List<Pool?> = poolList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var rankingsAdapter: RankingsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowPoolBinding = RowPoolBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pool: Pool = _poolList[position]!!
        setRankingBackground(holder.root, pool)
        RankingUtil.renderNumber(_context, pool.position, holder.positionTextView)

        val recyclerView: RecyclerView = holder.rankingListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        rankingsAdapter = RankingsAdapter(_context, pool.rankings!!)
        recyclerView.adapter = rankingsAdapter
    }

    private fun setRankingBackground(view: View, pool: Pool) {
        when (pool.position) {
            1 -> if (pool.highlighted!!) view.setBackgroundColor(ContextCompat.getColor(_context!!, R.color.gold))
            2 -> if (pool.highlighted!!) view.setBackgroundColor(ContextCompat.getColor(_context!!, R.color.silver))
            3 -> if (pool.highlighted!!) view.setBackgroundColor(ContextCompat.getColor(_context!!, R.color.bronze))
            4 -> if (pool.highlighted!!) view.setBackgroundColor(ContextCompat.getColor(_context!!, R.color.sky_blue))
            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return _poolList.size
    }

    inner class ViewHolder internal constructor(binding: RowPoolBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var positionTextView: TextView = binding.position
        var rankingListRecyclerView: RecyclerView = binding.rankingList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _poolList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, poolList: List<Pool?>, position: Int)
    }
}
