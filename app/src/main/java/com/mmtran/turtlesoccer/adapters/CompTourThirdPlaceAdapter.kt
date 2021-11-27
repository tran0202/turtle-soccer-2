package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagCodeCellBinding
import com.mmtran.turtlesoccer.databinding.RowTeamCodeBinding

import com.mmtran.turtlesoccer.models.Team
import com.mmtran.turtlesoccer.utils.CommonUtil

class CompTourThirdPlaceAdapter(context: Context?, teamList: List<Team?>) :
    RecyclerView.Adapter<CompTourThirdPlaceAdapter.ViewHolder>() {

    private val _context = context
    private var _teamList: List<Team?> = teamList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowTeamCodeBinding = RowTeamCodeBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        CommonUtil.renderTeamFlagCodeCell(_context, _teamList[position], holder.flagCodeCellBinding)
    }

    override fun getItemCount(): Int {
        return _teamList.size
    }

    inner class ViewHolder internal constructor(binding: RowTeamCodeBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var flagCodeCellBinding: FragmentTeamFlagCodeCellBinding = binding.teamCodeCell
    }
}
