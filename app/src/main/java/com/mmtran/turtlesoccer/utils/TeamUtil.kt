package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import com.mmtran.turtlesoccer.databinding.FragmentFlagNameBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Team

object TeamUtil {

    fun renderFlagName(context: Context?, binding: FragmentFlagNameBinding?, team: Team?) {

        val firebaseStorageLoader = FirebaseStorageLoader(context)

        if (team!!.teamTypeId.equals("CLUB")) {
            firebaseStorageLoader.loadImage(
                context,
                binding!!.clubLogo,
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
}
