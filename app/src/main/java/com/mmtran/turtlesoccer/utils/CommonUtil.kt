package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.ChampionsAdapter
import com.mmtran.turtlesoccer.adapters.PlayersAdapter
import com.mmtran.turtlesoccer.adapters.TeamsAdapter
import com.mmtran.turtlesoccer.databinding.*
import com.mmtran.turtlesoccer.models.Champion
import com.mmtran.turtlesoccer.models.Player
import com.mmtran.turtlesoccer.models.Team

object CommonUtil {

    fun renderField(value: String?, field: TextView) {

        if (!value.isNullOrEmpty()) {
            field.visibility = View.VISIBLE
            field.text = value
        } else {
            field.visibility = View.GONE
        }
    }

    fun renderTeamFlagCodeCell(context: Context?, team: Team?, binding: FragmentTeamFlagCodeCellBinding?) {

        if (team != null && team.isValid()) {
            binding!!.cell.visibility = View.VISIBLE
            TeamUtil.renderFlagCode(context, team, binding!!.flagName)
        } else {
            binding!!.cell.visibility = View.GONE
        }
    }

    fun renderWrapLabelField(context: Context?, value: String?, fragmentWrapLabelFieldBinding: FragmentWrapLabelFieldBinding?, label: Int) {

        if (!value.isNullOrEmpty()) {
            fragmentWrapLabelFieldBinding!!.container.visibility = View.VISIBLE
            fragmentWrapLabelFieldBinding.label.visibility = View.VISIBLE
            fragmentWrapLabelFieldBinding.label.text = context!!.getString(label)
            fragmentWrapLabelFieldBinding.field.visibility = View.VISIBLE
            fragmentWrapLabelFieldBinding.field.text = value
        } else {
            fragmentWrapLabelFieldBinding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelField(context: Context?, value: String?, fragmentLabelFieldBinding: FragmentLabelFieldBinding?, label: Int) {

        if (!value.isNullOrEmpty()) {
            fragmentLabelFieldBinding!!.container.visibility = View.VISIBLE
            fragmentLabelFieldBinding.label.visibility = View.VISIBLE
            fragmentLabelFieldBinding.label.text = context!!.getString(label)
            fragmentLabelFieldBinding.field.visibility = View.VISIBLE
            fragmentLabelFieldBinding.field.text = value
        } else {
            fragmentLabelFieldBinding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelTeam(context: Context?, team: Team?, fragmentLabelTeamBinding: FragmentLabelTeamBinding, label: Int) {

        if (team != null && team.isValid()) {
            fragmentLabelTeamBinding.container.visibility = View.VISIBLE
            fragmentLabelTeamBinding.label.visibility = View.VISIBLE
            fragmentLabelTeamBinding.label.text = context!!.getString(label)
            fragmentLabelTeamBinding.field.visibility = View.VISIBLE
            TeamUtil.renderFlagName(context, fragmentLabelTeamBinding.flagName, team)
        } else {
            fragmentLabelTeamBinding.container.visibility = View.GONE
        }
    }

    fun renderLabelTeam2Lines(context: Context?, team: Team?, fragmentLabelTeam2LinesBinding: FragmentLabelTeam2LinesBinding?, label: Int) {

        if (team != null && team.isValid()) {
            fragmentLabelTeam2LinesBinding!!.container.visibility = View.VISIBLE
            fragmentLabelTeam2LinesBinding.label.visibility = View.VISIBLE
            fragmentLabelTeam2LinesBinding.label.text = context!!.getString(label)
            fragmentLabelTeam2LinesBinding.field.visibility = View.VISIBLE
            TeamUtil.renderFlagName(context, fragmentLabelTeam2LinesBinding.flagName, team)
        } else {
            fragmentLabelTeam2LinesBinding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelTeamTitleCount(context: Context?, champion: Champion?, fragmentLabelTeamTitleCountBinding: FragmentLabelTeamTitleCountBinding?, label: Int) {

        if (champion !== null && champion.team !== null) {
            fragmentLabelTeamTitleCountBinding!!.container.visibility = View.VISIBLE
            fragmentLabelTeamTitleCountBinding.label.visibility = View.VISIBLE
            fragmentLabelTeamTitleCountBinding.label.text = context!!.getString(label)
            fragmentLabelTeamTitleCountBinding.field.visibility = View.VISIBLE
            TeamUtil.renderFlagName(context, fragmentLabelTeamTitleCountBinding.flagName, champion.team)
            if (champion.titleCount != null) {
                fragmentLabelTeamTitleCountBinding.titleCount.text = context.getString(R.string.competition_title_count, champion.titleCount.toString())
            } else {
                fragmentLabelTeamTitleCountBinding.titleCount.visibility = View.GONE
            }
        } else {
            fragmentLabelTeamTitleCountBinding!!.container.visibility = View.GONE
        }
    }

    fun renderGoldenBall(context: Context?, players: List<Player?>?, goldenBallBinding: FragmentLabelPlayerBinding?,
                         silverBallBinding: FragmentLabelPlayerBinding?, bronzeBallBinding: FragmentLabelPlayerBinding?) {

        if (players != null && players.isNotEmpty()) {
            goldenBallBinding!!.container.visibility = View.VISIBLE
            renderPlayer(context, players[0], goldenBallBinding, R.string.golden_ball_label)
            if (players.size > 1) {
                renderPlayer(context, players[1], silverBallBinding, R.string.silver_ball_label)
                silverBallBinding!!.container.visibility = View.VISIBLE
            } else {
                silverBallBinding!!.container.visibility = View.GONE
            }
            if (players.size > 2) {
                renderPlayer(context, players[2], bronzeBallBinding, R.string.bronze_ball_label)
                bronzeBallBinding!!.container.visibility = View.VISIBLE
            } else {
                bronzeBallBinding!!.container.visibility = View.GONE
            }
        } else {
            goldenBallBinding!!.container.visibility = View.GONE
            silverBallBinding!!.container.visibility = View.GONE
            bronzeBallBinding!!.container.visibility = View.GONE
        }
    }

    private fun renderPlayer(context: Context?, player: Player?, binding: FragmentLabelPlayerBinding?, label: Int) {

        if (player != null) {
            binding!!.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            PlayerUtil.renderPlayerFlagName(context, binding.flagName, player)
        } else {
            binding!!.container.visibility = View.GONE
        }
    }

    fun renderPlayerDivider(players: List<Player?>?, divider: View?) {

        if (players != null && players.isNotEmpty()) {
            divider!!.visibility = View.VISIBLE
        } else {
            divider!!.visibility = View.GONE
        }
    }

    fun renderTeamList(context: Context?, teamList: List<Team?>?, fragmentLabelListBinding: FragmentLabelListBinding?, label: Int) {

        if (teamList == null) {
            fragmentLabelListBinding!!.container.visibility = View.GONE
            return
        }

        fragmentLabelListBinding!!.container.visibility = View.VISIBLE
        fragmentLabelListBinding.pluralLabel.visibility = View.GONE

        if (teamList.isNotEmpty()) {
            fragmentLabelListBinding.label.visibility = View.VISIBLE
            fragmentLabelListBinding.label.text = context!!.getString(label)
            val recyclerView: RecyclerView = fragmentLabelListBinding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = TeamsAdapter(context, teamList)
            recyclerView.adapter = adapter
        } else {
            fragmentLabelListBinding.label.visibility = View.GONE
        }
    }

    fun renderTeamListWithPlural(context: Context?, teamList: List<Team?>?, fragmentLabelListBinding: FragmentLabelListBinding?, label: Int, pluralLabel: Int) {

        if (teamList == null) {
            fragmentLabelListBinding!!.container.visibility = View.GONE
            return
        }

        fragmentLabelListBinding!!.container.visibility = View.VISIBLE

        if (teamList.isNotEmpty()) {
            if (teamList.size == 1) {
                fragmentLabelListBinding.label.visibility = View.VISIBLE
                fragmentLabelListBinding.label.text = context!!.getString(label)
                fragmentLabelListBinding.pluralLabel.visibility = View.GONE
            } else {
                fragmentLabelListBinding.label.visibility = View.GONE
                fragmentLabelListBinding.pluralLabel.visibility = View.VISIBLE
                fragmentLabelListBinding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = fragmentLabelListBinding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = TeamsAdapter(context, teamList)
            recyclerView.adapter = adapter
        } else {
            fragmentLabelListBinding.label.visibility = View.GONE
            fragmentLabelListBinding.pluralLabel.visibility = View.GONE
        }
    }

    fun renderPlayerListWithPlural(context: Context?, players: List<Player?>?, fragmentLabelListBinding: FragmentLabelListBinding?, label: Int, pluralLabel: Int) {

        if (players == null) {
            fragmentLabelListBinding!!.container.visibility = View.GONE
            return
        }

        fragmentLabelListBinding!!.container.visibility = View.VISIBLE

        if (players.isNotEmpty()) {
            if (players.size == 1) {
                fragmentLabelListBinding.label.visibility = View.VISIBLE
                fragmentLabelListBinding.label.text = context!!.getString(label)
                fragmentLabelListBinding.pluralLabel.visibility = View.GONE
            } else {
                fragmentLabelListBinding.label.visibility = View.GONE
                fragmentLabelListBinding.pluralLabel.visibility = View.VISIBLE
                fragmentLabelListBinding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = fragmentLabelListBinding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = PlayersAdapter(context, players)
            recyclerView.adapter = adapter
        } else {
            fragmentLabelListBinding.label.visibility = View.GONE
            fragmentLabelListBinding.pluralLabel.visibility = View.GONE
        }
    }

    fun renderChampionList(context: Context?, championList: List<Champion?>?, fragmentLabelListBinding: FragmentLabelList2LinesBinding?, label: Int, pluralLabel: Int) {

        if (championList == null) {
            fragmentLabelListBinding!!.container.visibility = View.GONE
            return
        }

        fragmentLabelListBinding!!.container.visibility = View.VISIBLE

        if (championList.isNotEmpty()) {
            if (championList.size == 1) {
                fragmentLabelListBinding.label.visibility = View.VISIBLE
                fragmentLabelListBinding.label.text = context!!.getString(label)
                fragmentLabelListBinding.pluralLabel.visibility = View.GONE
            } else {
                fragmentLabelListBinding.label.visibility = View.GONE
                fragmentLabelListBinding.pluralLabel.visibility = View.VISIBLE
                fragmentLabelListBinding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = fragmentLabelListBinding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = ChampionsAdapter(context, championList)
            recyclerView.adapter = adapter
        } else {
            fragmentLabelListBinding.label.visibility = View.GONE
            fragmentLabelListBinding.pluralLabel.visibility = View.GONE
        }
    }

    fun addDivider(recyclerView: RecyclerView, context: Context, _drawable: Int ) {

        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, _drawable)!!)
        recyclerView.addItemDecoration(divider)
    }
}
