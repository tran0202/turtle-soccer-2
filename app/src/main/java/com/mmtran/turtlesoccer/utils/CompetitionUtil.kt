package com.mmtran.turtlesoccer.utils

import com.mmtran.turtlesoccer.models.Champion
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Team

object CompetitionUtil {

    fun getMostSuccessfulTeams(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.mostSuccessfulTeams.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (champion: Champion? in competition.mostSuccessfulTeams!!) {

            val team = teamList.find { it!!.id.equals(champion!!.teamId) }
            if (team!= null) {
                val nation = nationList.find { it!!.id.equals(team.nationId) }
                if (nation != null) {
                    team.nation = nation
                }
                champion!!.team = team
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

        val team = teamList.find { it!!.id.equals(championId) }
        if (team!= null) {
            val nation = nationList.find { it!!.id.equals(team.nationId) }
            if (nation != null) {
                team.nation = nation
            }
            if (competition!!.currentChampions !== null) {
                competition!!.currentChampions!!.team = team
            } else {
                competition!!.lastChampions!!.team = team
            }
        }
    }
}
