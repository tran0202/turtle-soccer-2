package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowGroupBinding

import com.mmtran.turtlesoccer.models.Group

class GroupsAdapter(context: Context?, groupList: List<Group?>) :
    RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

    private val _context = context
    private val _groupList: List<Group?> = groupList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null
    private var poolsAdapter: PoolsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowGroupBinding = RowGroupBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val group: Group = _groupList[position]!!
        holder.groupNameTextView.visibility = if (group.hideGroupName!!) View.GONE else View.VISIBLE
        holder.groupNameTextView.text = group.name

        val recyclerView: RecyclerView = holder.poolListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        poolsAdapter = PoolsAdapter(_context, group.pools!!)
        recyclerView.adapter = poolsAdapter
    }

    override fun getItemCount(): Int {
        return _groupList.size
    }

    inner class ViewHolder internal constructor(binding: RowGroupBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var groupNameTextView: TextView = binding.groupName
        var poolListRecyclerView: RecyclerView = binding.poolList

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _groupList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, groupList: List<Group?>, position: Int)
    }
}
