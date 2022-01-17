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

        val match: Match = _matchList[position]!!
        renderMatch(holder.matchBinding, position)
        MatchUtil.renderLegDivider(match, holder.legDividerView)
        MatchUtil.renderLeg2Row(match, holder.leg2Binding)
        renderLeg2(holder.leg2Binding, position)
        MatchUtil.renderReplayDivider(match, holder.replayDividerView)
        MatchUtil.renderReplayRow(match, holder.replayBinding)
        renderReplay(holder.replayBinding, position)
    }

    private fun renderMatch(matchBinding: FragmentMatchBinding, position: Int) {

        val match: Match = _matchList[position]!!
        MatchUtil.setMatchMinHeight(_context, match, matchBinding)
        MatchUtil.renderLeg1Count(_context, match, matchBinding)
        MatchUtil.renderLeg1Date(match, matchBinding)
        MatchUtil.renderLeg1Time(match, matchBinding)
        MatchUtil.renderGroupName(match, matchBinding)
        MatchUtil.renderLeg1City(match, matchBinding)
        MatchUtil.renderLeg1HomeTeamName(_context, match, matchBinding)
        MatchUtil.renderLeg1HomeAwarded(match, matchBinding)
        MatchUtil.renderLeg1HomeDisqualified(match, matchBinding)
        MatchUtil.renderLeg1HomeWithdrew(match, matchBinding)
        MatchUtil.renderLeg1HomeTeamFlag(_context, match, matchBinding)
        MatchUtil.renderLeg1ScoreColumn(_context, match, matchBinding)
        MatchUtil.renderLeg1AwayTeamFlag(_context, match, matchBinding)
        MatchUtil.renderLeg1AwayTeamName(_context, match, matchBinding)
        MatchUtil.renderLeg1AwayAwarded(match, matchBinding)
        MatchUtil.renderLeg1AwayDisqualified(match, matchBinding)
        MatchUtil.renderLeg1AwayWithdrew(match, matchBinding)
        MatchUtil.renderLeg1AggregateScore(match, matchBinding)
        MatchUtil.renderLeg1ExtraTimeFootnote(_context, match, matchBinding)
    }

    private fun renderLeg2(matchBinding: FragmentMatchBinding, position: Int) {

        val match: Match = _matchList[position]!!
        if (!match.multipleLegs!!) return
        MatchUtil.setMatchMinHeight(_context, match, matchBinding)
        MatchUtil.renderLeg2Count(_context, match, matchBinding)
        MatchUtil.renderLeg2Date(match, matchBinding)
        MatchUtil.renderLeg2Time(match, matchBinding)
        MatchUtil.renderGroupName(match, matchBinding)
        MatchUtil.renderLeg2City(match, matchBinding)
        MatchUtil.renderLeg2HomeTeamName(_context, match, matchBinding)
        MatchUtil.renderLeg2HomeTeamFlag(_context, match, matchBinding)
        MatchUtil.renderLeg2ScoreColumn(_context, match, matchBinding)
        MatchUtil.renderLeg2AwayTeamFlag(_context, match, matchBinding)
        MatchUtil.renderLeg2AwayTeamName(_context, match, matchBinding)
        MatchUtil.renderLeg2AggregateScore(_context, match, matchBinding)
        MatchUtil.renderLeg2ExtraTimeFootnote(_context, match, matchBinding)
    }

    private fun renderReplay(matchBinding: FragmentMatchBinding, position: Int) {

        val match: Match = _matchList[position]!!
        if (!match.replayMatch!!) return
        MatchUtil.setMatchMinHeight(_context, match, matchBinding)
        MatchUtil.renderReplayCount(_context, match, matchBinding)
        MatchUtil.renderReplayDate(match, matchBinding)
        MatchUtil.renderReplayTime(match, matchBinding)
        MatchUtil.renderGroupName(match, matchBinding)
        MatchUtil.renderReplayCity(match, matchBinding)
        MatchUtil.renderReplayHomeTeamName(_context, match, matchBinding)
        MatchUtil.renderReplayHomeTeamFlag(_context, match, matchBinding)
        MatchUtil.renderReplayScoreColumn(_context, match, matchBinding)
        MatchUtil.renderReplayAwayTeamFlag(_context, match, matchBinding)
        MatchUtil.renderReplayAwayTeamName(_context, match, matchBinding)
        MatchUtil.renderReplayAggregateScore(match, matchBinding)
        MatchUtil.renderReplayExtraTimeFootnote(match, matchBinding)
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
        var replayDividerView: View = binding.replayDivider
        var replayBinding: FragmentMatchBinding = binding.replay

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _matchList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, matchList: List<Match?>, position: Int)
    }
}
