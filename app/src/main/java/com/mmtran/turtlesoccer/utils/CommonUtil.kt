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
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
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

    fun renderTeamFlagName(context: Context?, team: Team?, binding: FragmentTeamFlagNameBinding?) {

        if (team == null) return

        val firebaseStorageLoader = FirebaseStorageLoader(context)
        val factor: Float = context!!.resources.displayMetrics.density

        if (team.teamTypeId.equals("CLUB")) {
            if (!team.logoFilename.isNullOrEmpty()) {
                binding!!.clubLogo.visibility = View.VISIBLE
                firebaseStorageLoader.loadImage(
                    context,
                    binding.clubLogo,
                    "club_logos/" + team.logoFilename
                )
            } else {
                binding!!.clubLogo.visibility = View.GONE
            }
            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
                binding.flag.visibility = View.VISIBLE
                binding.flag.layoutParams.width = (24 * factor).toInt()
                firebaseStorageLoader.loadImage(
                    context,
                    binding.flag,
                    "flags/" + team.nation!!.flagFilename
                )
            } else {
                binding.flag.visibility = View.GONE
            }
            binding.name.text = team.name
        } else {
            binding!!.clubLogo.visibility = View.GONE
            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
                binding.flag.visibility = View.VISIBLE
                binding.flag.layoutParams.width = (36 * factor).toInt()
                firebaseStorageLoader.loadImage(
                    context,
                    binding.flag,
                    "flags/" + team.nation!!.flagFilename
                )
            } else {
                binding.flag.visibility = View.GONE
            }
            binding.name.text = team.name
        }
    }

    fun renderChampionFlagName(context: Context?, champion: Champion?, binding: FragmentChampionFlagNameBinding?) {

        if (champion?.team != null && champion.team!!.isValid()) {
            binding!!.team.visibility = View.VISIBLE
            renderTeamFlagName(context, champion.team!!, binding.flagName)
            if (champion.titleCount != null && champion.titleCount!! > 0) {
                binding.titleCount.visibility = View.VISIBLE
                if (champion.titleCount!! == 1) {
                    binding.titleCount.text = context!!.getString(R.string.competition_one_title, champion.titleCount.toString())
                } else {
                    binding.titleCount.text = context!!.getString(R.string.competition_titles, champion.titleCount.toString())
                }
            } else {
                binding.titleCount.visibility = View.GONE
            }
        } else {
            binding!!.team.visibility = View.GONE
        }
    }

    fun renderTeamFlagCode(context: Context?, team: Team?, binding: FragmentTeamFlagCodeBinding?) {

        val firebaseStorageLoader = FirebaseStorageLoader(context)
        val factor: Float = context!!.resources.displayMetrics.density

        if (team?.nation == null) return

        if (team.teamTypeId.equals("CLUB")) {
            if (!team.logoFilename.isNullOrEmpty()) {
                binding!!.clubLogo.visibility = View.VISIBLE
                firebaseStorageLoader.loadImage(
                    context,
                    binding.clubLogo,
                    "club_logos/" + team.logoFilename
                )
            } else {
                binding!!.clubLogo.visibility = View.GONE
            }
            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
                binding.flag.visibility = View.VISIBLE
                binding.flag.layoutParams.width = (24 * factor).toInt()
                firebaseStorageLoader.loadImage(
                    context,
                    binding.flag,
                    "flags/" + team.nation!!.flagFilename
                )
            } else {
                binding.flag.visibility = View.GONE
            }
            binding.code.text = team.clubCode
        } else {
            binding!!.clubLogo.visibility = View.GONE
            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
                binding.flag.visibility = View.VISIBLE
                binding.flag.layoutParams.width = (36 * factor).toInt()
                firebaseStorageLoader.loadImage(
                    context,
                    binding.flag,
                    "flags/" + team.nation!!.flagFilename
                )
            } else {
                binding.flag.visibility = View.GONE
            }
            binding.code.text = team.code
        }
    }

    fun renderTeamFlagCodeCell(context: Context?, team: Team?, binding: FragmentTeamFlagCodeCellBinding?) {

        if (team != null && team.isValid()) {
            binding!!.cell.visibility = View.VISIBLE
            renderTeamFlagCode(context, team, binding.flagName)
        } else {
            binding!!.cell.visibility = View.GONE
        }
    }

    fun renderWrapLabelField(context: Context?, value: String?, binding: FragmentWrapLabelFieldBinding?, label: Int) {

        if (!value.isNullOrEmpty()) {
            binding!!.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            binding.field.visibility = View.VISIBLE
            binding.field.text = value
        } else {
            binding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelField(context: Context?, value: String?, binding: FragmentLabelFieldBinding?, label: Int) {

        if (!value.isNullOrEmpty()) {
            binding!!.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            binding.field.visibility = View.VISIBLE
            binding.field.text = value
        } else {
            binding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelTeam(context: Context?, team: Team?, binding: FragmentLabelTeamBinding, label: Int) {

        if (team != null && team.isValid()) {
            binding.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            binding.field.visibility = View.VISIBLE
            renderTeamFlagName(context, team, binding.flagName)
        } else {
            binding.container.visibility = View.GONE
        }
    }

    fun renderLabelTeam2(context: Context?, team: Team?, binding: FragmentLabelTeam2Binding?, label: Int) {

        if (team != null && team.isValid()) {
            binding!!.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            binding.field.visibility = View.VISIBLE
            renderTeamFlagName(context, team, binding.flagName)
        } else {
            binding!!.container.visibility = View.GONE
        }
    }

    fun renderLabelChampion(context: Context?, champion: Champion?, binding: FragmentLabelChampionBinding?, label: Int) {

        if (champion !== null && champion.team !== null && champion.team!!.isValid()) {
            binding!!.container.visibility = View.VISIBLE
            binding.label.visibility = View.VISIBLE
            binding.label.text = context!!.getString(label)
            binding.field.visibility = View.VISIBLE
            renderTeamFlagName(context, champion.team, binding.flagName.flagName)
            if (champion.titleCount != null && champion.titleCount!! > 0) {
                binding.flagName.titleCount.visibility = View.VISIBLE
                if (champion.titleCount == 1) {
                    binding.flagName.titleCount.text = context.getString(R.string.competition_one_title, champion.titleCount.toString())
                } else {
                    binding.flagName.titleCount.text = context.getString(R.string.competition_titles, champion.titleCount.toString())
                }
            } else {
                binding.flagName.titleCount.visibility = View.GONE
            }
        } else {
            binding!!.container.visibility = View.GONE
        }
    }

    fun renderTeamList(context: Context?, teamList: List<Team?>?, binding: FragmentLabelTeamListBinding?, label: Int, pluralLabel: Int) {

        if (teamList == null || teamList.isEmpty()) {
            binding!!.root.visibility = View.GONE
            return
        }

        binding!!.root.visibility = View.VISIBLE

        if (teamList.isNotEmpty()) {
            if (teamList.size == 1) {
                binding.label.visibility = View.VISIBLE
                binding.label.text = context!!.getString(label)
                binding.pluralLabel.visibility = View.GONE
            } else {
                binding.label.visibility = View.GONE
                binding.pluralLabel.visibility = View.VISIBLE
                binding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = binding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = TeamsAdapter(context, teamList)
            recyclerView.adapter = adapter
        } else {
            binding.label.visibility = View.GONE
            binding.pluralLabel.visibility = View.GONE
        }
    }

    fun renderChampionList(context: Context?, championList: List<Champion?>?, binding: FragmentLabelChampionListBinding?, label: Int, pluralLabel: Int) {

        if (championList == null) {
            binding!!.container.visibility = View.GONE
            return
        }

        binding!!.container.visibility = View.VISIBLE

        if (championList.isNotEmpty() && championList[0] !== null && championList[0]!!.team !== null && championList[0]!!.team!!.isValid()) {
            if (championList.size == 1) {
                binding.label.visibility = View.VISIBLE
                binding.label.text = context!!.getString(label)
                binding.pluralLabel.visibility = View.GONE
            } else {
                binding.label.visibility = View.GONE
                binding.pluralLabel.visibility = View.VISIBLE
                binding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = binding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = ChampionsAdapter(context, championList)
            recyclerView.adapter = adapter
        } else {
            binding.label.visibility = View.GONE
            binding.pluralLabel.visibility = View.GONE
        }
    }

    fun renderPlayerList(context: Context?, playerList: List<Player?>?, binding: FragmentLabelPlayerListBinding?, label: Int, pluralLabel: Int) {

        if (playerList == null || playerList.isEmpty()) {
            binding!!.root.visibility = View.GONE
            return
        }

        binding!!.root.visibility = View.VISIBLE

        if (playerList.isNotEmpty()) {
            if (playerList.size == 1) {
                binding.label.visibility = View.VISIBLE
                binding.label.text = context!!.getString(label)
                binding.pluralLabel.visibility = View.GONE
            } else {
                binding.label.visibility = View.GONE
                binding.pluralLabel.visibility = View.VISIBLE
                binding.pluralLabel.text = context!!.getString(pluralLabel)
            }
            val recyclerView: RecyclerView = binding.list
            recyclerView.layoutManager = LinearLayoutManager(context)
            addDivider(recyclerView, context, R.drawable.no_divider)
            val adapter = PlayersAdapter(context, playerList)
            recyclerView.adapter = adapter
        } else {
            binding.label.visibility = View.GONE
            binding.pluralLabel.visibility = View.GONE
        }
    }

    fun renderGoldenBall(context: Context?, playerList: List<Player?>?, goldenBallLabel: Int, goldenBallBinding: FragmentLabelPlayerBinding?,
                         silverBallBinding: FragmentLabelPlayerBinding?, bronzeBallBinding: FragmentLabelPlayerBinding?) {

        if (playerList != null && playerList.isNotEmpty()) {
            goldenBallBinding!!.container.visibility = View.VISIBLE
            renderPlayer(context, playerList[0], goldenBallBinding, goldenBallLabel)
            if (playerList.size > 1) {
                renderPlayer(context, playerList[1], silverBallBinding, R.string.silver_ball_label)
                silverBallBinding!!.container.visibility = View.VISIBLE
            } else {
                silverBallBinding!!.container.visibility = View.GONE
            }
            if (playerList.size > 2) {
                renderPlayer(context, playerList[2], bronzeBallBinding, R.string.bronze_ball_label)
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

    fun addDivider(recyclerView: RecyclerView, context: Context, _drawable: Int ) {

        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, _drawable)!!)
        recyclerView.addItemDecoration(divider)
    }
}
