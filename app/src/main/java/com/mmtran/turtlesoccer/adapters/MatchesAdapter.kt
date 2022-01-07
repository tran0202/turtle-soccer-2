package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentMatchBinding

import com.mmtran.turtlesoccer.databinding.RowMatchBinding
import com.mmtran.turtlesoccer.models.Match
import com.mmtran.turtlesoccer.utils.MatchUtil

class MatchesAdapter(context: Context?, matchList: List<Match?>) :
    RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    private val _context = context
    private val _matchList: List<Match?> = matchList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowMatchBinding = RowMatchBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        renderMatch(holder.matchBinding, position)
        MatchUtil.renderLegDivider(_matchList[position]!!, holder.legDividerView)
        MatchUtil.renderLeg2Row(_matchList[position]!!, holder.leg2Binding)
        renderLeg2(holder.leg2Binding, position)
    }

    private fun renderMatch(matchBinding: FragmentMatchBinding, position: Int) {

        MatchUtil.setMatchMinHeight(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1Count(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1Date(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1Time(_matchList[position]!!, matchBinding)
        MatchUtil.renderGroupName(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1City(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1HomeTeamName(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1HomeTeamFlag(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1ScoreColumn(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1AwayTeamFlag(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1AwayTeamName(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1AggregateScore(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg1ExtraTimeFootnote(_context, _matchList[position]!!, matchBinding)
    }

    private fun renderLeg2(matchBinding: FragmentMatchBinding, position: Int) {

        if (!_matchList[position]!!.multipleLegs!!) return
        MatchUtil.setMatchMinHeight(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2Count(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2Date(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2Time(_matchList[position]!!, matchBinding)
        MatchUtil.renderGroupName(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2City(_matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2HomeTeamName(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2HomeTeamFlag(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2ScoreColumn(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2AwayTeamFlag(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2AwayTeamName(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2AggregateScore(_context, _matchList[position]!!, matchBinding)
        MatchUtil.renderLeg2ExtraTimeFootnote(_context, _matchList[position]!!, matchBinding)
    }

    override fun getItemCount(): Int {
        return _matchList.size
    }

    inner class ViewHolder internal constructor(binding: RowMatchBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var matchBinding: FragmentMatchBinding = binding.match
        var legDividerView: View = binding.legDivider
        var leg2Binding: FragmentMatchBinding = binding.leg2

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _matchList, absoluteAdapterPosition)
        }

        init {
//            root.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, matchList: List<Match?>, position: Int)
    }
}
