package com.mmtran.turtlesoccer.utils

import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Team

object CompetitionUtil {

    fun getChampion(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        val championId = if (competition!!.currentChampions !== null) {
            competition!!.currentChampions!!
        } else {
            competition!!.lastChampions!!
        }

        val team = teamList.find { it!!.id.equals(championId) }
        if (team!= null) {
            val nation = nationList.find { it!!.id.equals(team.nationId) }
            if (nation != null) {
                team.nation = nation
            }
            if (competition.currentChampions !== null) {
                competition.currentChampionTeam = team
            } else {
                competition.lastChampionTeam = team
            }
        }
    }
}
