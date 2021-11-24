package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagNameBinding
import com.mmtran.turtlesoccer.models.*

object CompetitionUtil {

    fun getMostSuccessfulTeams(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.mostSuccessfulTeams.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (champion: Champion? in competition.mostSuccessfulTeams!!) {

            val team = TeamUtil.getTeam(champion!!.teamId, nationList, teamList)
            if (team != null) {
                champion.team = team
            }
        }
    }

    fun getChampion(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        var championId: String? = null
        if (competition!!.currentChampions !== null) {
            championId = competition!!.currentChampions!!.teamId
        } else if (competition!!.lastChampions !== null) {
            championId = competition!!.lastChampions!!.teamId
        }
        if (championId.isNullOrEmpty()) return

        val team = TeamUtil.getTeam(championId, nationList, teamList)
        if (team!= null) {
            if (competition!!.currentChampions !== null) {
                competition!!.currentChampions!!.team = team
            } else {
                competition!!.lastChampions!!.team = team
            }
        }
    }

    fun renderTeamCount(competition: Competition?): String {

        if (competition!!.teamCount == null) return ""

        return competition.teamCount.toString()
    }

    fun renderChampions(context: Context?, champion: Champion?, label: TextView, field: LinearLayout, flagNameBinding: FragmentTeamFlagNameBinding, titleCount: TextView) {

        if (champion !== null && champion.team !== null) {
            label.visibility = View.VISIBLE
            field.visibility = View.VISIBLE
            TeamUtil.renderFlagName(context!!, flagNameBinding, champion.team)
            if (champion.titleCount != null) {
                titleCount.text = context.getString(R.string.competition_title_count, champion.titleCount.toString())
            } else {
                titleCount.visibility = View.GONE
            }
        } else {
            label.visibility = View.GONE
            field.visibility = View.GONE
        }
    }
}
