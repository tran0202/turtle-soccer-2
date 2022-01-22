package com.mmtran.turtlesoccer.utils

import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Team

object TeamUtil {

    fun processTeam(t: Team?, nationList: List<Nation?>?, teamList: List<Team?>?): Team? {
        if (t == null) return null

        val team = getTeam(t.id, nationList, teamList)
        if (team != null) {
            if (t.parentTeam != null) {
                val pTeam = getTeam(t.parentTeam!!.id, nationList, teamList)
                team.parentTeam = pTeam
            }
        }
        return team
    }

    fun getTeam(id: String?, nationList: List<Nation?>?, teamList: List<Team?>?): Team? {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return null

        val team = teamList.find { it!!.id.equals(id) }
        if (team?.nation != null) {
            val nation = nationList.find { it!!.id.equals(team.nation!!.id) }
            if (nation != null) {
                team.nation = nation
            }
        }
        return team
    }
}
