package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentChampionFlagNameBinding
import com.mmtran.turtlesoccer.databinding.RowChampionBinding
import com.mmtran.turtlesoccer.models.Team
import com.mmtran.turtlesoccer.utils.CommonUtil

class ChampionsAdapter(context: Context?, championList: List<Team?>) :
    RecyclerView.Adapter<ChampionsAdapter.ViewHolder>() {

    private val _context = context
    private val _championList: List<Team?> = championList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowChampionBinding = RowChampionBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (_championList[position] != null) {
            holder.teamLinearLayout.visibility = View.VISIBLE
            CommonUtil.renderChampionFlagName(_context, _championList[position]!!, holder.fragmentChampionFlagNameBinding)
        } else {
            holder.teamLinearLayout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return _championList.size
    }

    inner class ViewHolder internal constructor(binding: RowChampionBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var teamLinearLayout: LinearLayout = binding.team
        var fragmentChampionFlagNameBinding: FragmentChampionFlagNameBinding = binding.flagName
    }
}
