package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.databinding.RowConfCompetitionBinding
import com.mmtran.turtlesoccer.models.Competition

class ConfCompetitionsAdapter(context: Context?, competitionList: List<Competition?>) :
    RecyclerView.Adapter<ConfCompetitionsAdapter.ViewHolder>() {

    private val _context = context
    private val _competitionList: List<Competition?> = competitionList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowConfCompetitionBinding = RowConfCompetitionBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.competitionNameTextView.text = _competitionList[position]!!.name
    }

    override fun getItemCount(): Int {
        return _competitionList.size
    }

    inner class ViewHolder internal constructor(binding: RowConfCompetitionBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var competitionNameTextView: TextView = binding.competitionName

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _competitionList, absoluteAdapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int)
    }
}
