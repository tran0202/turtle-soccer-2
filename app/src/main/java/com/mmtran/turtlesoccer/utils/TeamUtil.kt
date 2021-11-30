package com.mmtran.turtlesoccer.utils

import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Team

object TeamUtil {

    fun getTeam(id: String?, nationList: List<Nation?>?, teamList: List<Team?>?): Team? {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return null

        val team = teamList.find { it!!.id.equals(id) }
        if (team!= null) {
            val nation = nationList.find { it!!.id.equals(team.nationId) }
            if (nation != null) {
                team.nation = nation
            }
        }
        return team
    }
}
