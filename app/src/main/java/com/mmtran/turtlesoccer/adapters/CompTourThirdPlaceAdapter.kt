package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameNarrowBinding
import com.mmtran.turtlesoccer.databinding.RowThirdPlaceBinding

import com.mmtran.turtlesoccer.models.Team
import com.mmtran.turtlesoccer.utils.TeamUtil

class CompTourThirdPlaceAdapter(context: Context?, teamList: List<Team?>) :
    RecyclerView.Adapter<CompTourThirdPlaceAdapter.ViewHolder>() {

    private val _context = context
    private var _teamList: List<Team?> = teamList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowThirdPlaceBinding = RowThirdPlaceBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        TeamUtil.renderFlagNameNarrow(_context, holder.thirdPlaceFlagNameBinding, _teamList[position])
    }

    override fun getItemCount(): Int {
        return _teamList.size
    }

    inner class ViewHolder internal constructor(binding: RowThirdPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var thirdPlaceFlagNameBinding: FragmentFlagNameNarrowBinding = binding.thirdPlaceFlagName
    }
}
