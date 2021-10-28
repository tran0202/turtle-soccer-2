package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameNarrowBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Team

object TeamUtil {

    fun renderFlagName(context: Context?, binding: FragmentFlagNameBinding?, team: Team?) {

        val firebaseStorageLoader = FirebaseStorageLoader(context)

        if (team!!.teamTypeId.equals("CLUB")) {
            binding!!.clubLogo.visibility = View.VISIBLE
            firebaseStorageLoader.loadImage(
                context,
                binding.clubLogo,
                "club_logos/" + team.logoFilename
            )
            firebaseStorageLoader.loadImage(
                context,
                binding.flag,
                "flags/" + team.nation!!.flagFilename
            )
            binding.name.text = team.name
        } else {
            binding!!.clubLogo.visibility = View.GONE
            firebaseStorageLoader.loadImage(
                context,
                binding.flag,
                "flags/" + team.nation!!.flagFilename
            )
            binding.name.text = team.name
        }
    }

    fun renderFlagNameNarrow(context: Context?, binding: FragmentFlagNameNarrowBinding?, team: Team?) {

        val firebaseStorageLoader = FirebaseStorageLoader(context)

        if (team!!.teamTypeId.equals("CLUB")) {
            binding!!.clubLogo.visibility = View.VISIBLE
            firebaseStorageLoader.loadImage(
                context,
                binding.clubLogo,
                "club_logos/" + team.logoFilename
            )
            firebaseStorageLoader.loadImage(
                context,
                binding.flag,
                "flags/" + team.nation!!.flagFilename
            )
            binding.code.text = team.clubCode
        } else {
            binding!!.clubLogo.visibility = View.GONE
            firebaseStorageLoader.loadImage(
                context,
                binding.flag,
                "flags/" + team.nation!!.flagFilename
            )
            binding.code.text = team.code
        }
    }

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
