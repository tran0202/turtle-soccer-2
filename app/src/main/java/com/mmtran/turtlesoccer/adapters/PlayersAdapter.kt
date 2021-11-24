package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowPlayerBinding
import com.mmtran.turtlesoccer.databinding.RowTeamBinding
import com.mmtran.turtlesoccer.models.Player
import com.mmtran.turtlesoccer.models.Team
import com.mmtran.turtlesoccer.utils.TeamUtil.renderFlagName

class PlayersAdapter(context: Context?, playerList: List<Player?>) :
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    private val _context = context
    private val _playerList: List<Player?> = playerList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowPlayerBinding = RowPlayerBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (_playerList[position] != null) {
            holder.playerLinearLayout.visibility = View.VISIBLE
//            renderFlagName(_context, holder.fragmentFlagNameBinding, _playerList[position])
        } else {
            holder.playerLinearLayout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return _playerList.size
    }

    inner class ViewHolder internal constructor(binding: RowPlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var playerLinearLayout: LinearLayout = binding.player
//        var fragmentFlagNameBinding: FragmentFlagNameBinding = binding.flagName
    }
}