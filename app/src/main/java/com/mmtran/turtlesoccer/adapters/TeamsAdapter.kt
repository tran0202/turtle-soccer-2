package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagNameBinding
import com.mmtran.turtlesoccer.databinding.RowTeamBinding
import com.mmtran.turtlesoccer.models.Team
import com.mmtran.turtlesoccer.utils.TeamUtil.renderFlagName

class TeamsAdapter(context: Context?, teamList: List<Team?>) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    private val _context = context
    private val _teamList: List<Team?> = teamList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowTeamBinding = RowTeamBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (_teamList[position] != null) {
            holder.teamLinearLayout.visibility = View.VISIBLE
            renderFlagName(_context, holder.fragmentFlagNameBinding, _teamList[position])
        } else {
            holder.teamLinearLayout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return _teamList.size
    }

    inner class ViewHolder internal constructor(binding: RowTeamBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var teamLinearLayout: LinearLayout = binding.team
        var fragmentFlagNameBinding: FragmentTeamFlagNameBinding = binding.flagName
    }
}
