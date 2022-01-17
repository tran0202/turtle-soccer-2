package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagBinding

import com.mmtran.turtlesoccer.databinding.RowRankingBinding
import com.mmtran.turtlesoccer.models.Ranking
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.RankingUtil

class RankingsAdapter(context: Context?, rankingList: List<Ranking?>) :
    RecyclerView.Adapter<RankingsAdapter.ViewHolder>() {

    private val _context = context
    private val _rankingList: List<Ranking?> = rankingList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowRankingBinding = RowRankingBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ranking: Ranking = _rankingList[position]!!
        if (position == 0) {
            holder.rankingDividerView.visibility = View.GONE
        } else {
            holder.rankingDividerView.visibility = View.VISIBLE
        }
        CommonUtil.renderTeamFlag(_context, ranking.team, holder.teamFlagBinding)
        holder.teamNameTextView.text = ranking.team?.name
        RankingUtil.renderNumber(_context, ranking.mp, holder.mpTextView)
        RankingUtil.renderNumber(_context, ranking.w, holder.wTextView)
        RankingUtil.renderNumber(_context, ranking.d, holder.dTextView)
        RankingUtil.renderNumber(_context, ranking.l, holder.lTextView)
        RankingUtil.renderNumber(_context, ranking.gf, holder.gfTextView)
        RankingUtil.renderNumber(_context, ranking.ga, holder.gaTextView)
        RankingUtil.renderNumber(_context, ranking.gd, holder.gdTextView, true)
        RankingUtil.renderNumber(_context, ranking.pts, holder.ptsTextView)
    }

    override fun getItemCount(): Int {
        return _rankingList.size
    }

    inner class ViewHolder internal constructor(binding: RowRankingBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var rankingDividerView: View = binding.rankingDivider
        var teamFlagBinding: FragmentTeamFlagBinding = binding.teamFlag
        var teamNameTextView: TextView = binding.teamName
        var mpTextView: TextView = binding.mp
        var wTextView: TextView = binding.w
        var dTextView: TextView = binding.d
        var lTextView: TextView = binding.l
        var gfTextView: TextView = binding.gf
        var gaTextView: TextView = binding.ga
        var gdTextView: TextView = binding.gd
        var ptsTextView: TextView = binding.pts

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _rankingList, absoluteAdapterPosition)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, rankingList: List<Ranking?>, position: Int)
    }
}
