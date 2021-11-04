package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding
import com.mmtran.turtlesoccer.databinding.RowCompSuccessfulTeamBinding
import com.mmtran.turtlesoccer.models.Champion
import com.mmtran.turtlesoccer.utils.TeamUtil.renderFlagName

class CompSuccessfulTeamsAdapter(context: Context?, championList: List<Champion?>) :
    RecyclerView.Adapter<CompSuccessfulTeamsAdapter.ViewHolder>() {

    private val _context = context
    private val _championList: List<Champion?> = championList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCompSuccessfulTeamBinding = RowCompSuccessfulTeamBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        renderFlagName(_context, holder.fragmentFlagNameBinding, _championList[position]!!.team)
        holder.titleCountTextView.text = _context!!.getString(R.string.competition_title_count, _championList[position]!!.titleCount.toString())
    }

    override fun getItemCount(): Int {
        return _championList.size
    }

    inner class ViewHolder internal constructor(binding: RowCompSuccessfulTeamBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var fragmentFlagNameBinding: FragmentFlagNameBinding = binding.flagName
        var titleCountTextView: TextView = binding.titleCount
    }
}
