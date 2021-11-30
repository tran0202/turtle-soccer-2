package com.mmtran.turtlesoccer.utils

import android.app.Activity
import android.os.Bundle
import androidx.navigation.Navigation
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
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

    fun browseToCompetition(context: Activity, competition: Competition?) {
        val args = Bundle()
        args.putSerializable(EXTRA_COMPETITION, competition)
        val navController = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_competitions, args)
    }
}
