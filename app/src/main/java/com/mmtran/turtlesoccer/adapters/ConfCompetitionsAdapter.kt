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
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowConfCompetitionBinding = RowConfCompetitionBinding.inflate(mInflater, parent, false)
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
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): Competition? {
        return _competitionList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
