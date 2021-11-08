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

        if (team == null) return

        val firebaseStorageLoader = FirebaseStorageLoader(context)

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

    fun renderFlagNameNarrow(context: Context?, binding: FragmentFlagNameNarrowBinding?, team: Team?) {

        val firebaseStorageLoader = FirebaseStorageLoader(context)

        if (team?.nation == null) return

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
                firebaseStorageLoader.loadImage(
                    context,
                    binding.flag,
                    "flags/" + team.nation!!.flagFilename
                )
                binding.flag.layoutParams.width = (18 * factor).toInt()
            } else {
                binding.flag.visibility = View.GONE
            }
            binding.code.text = team.clubCode
        } else {
            binding!!.clubLogo.visibility = View.GONE
            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
                binding.flag.visibility = View.VISIBLE
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
