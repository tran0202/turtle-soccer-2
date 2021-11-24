package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import com.mmtran.turtlesoccer.databinding.FragmentPlayerFlagNameBinding
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagNameBinding
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagNameNarrowBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Nation
import com.mmtran.turtlesoccer.models.Player
import com.mmtran.turtlesoccer.models.Team

object PlayerUtil {

    fun renderPlayerFlagName(context: Context?, binding: FragmentPlayerFlagNameBinding?, player: Player?) {

        if (player == null) return

        val firebaseStorageLoader = FirebaseStorageLoader(context)

        binding!!.name.text = player.name

//        if (team.teamTypeId.equals("CLUB")) {
//            if (!team.logoFilename.isNullOrEmpty()) {
//                binding!!.clubLogo.visibility = View.VISIBLE
//                firebaseStorageLoader.loadImage(
//                    context,
//                    binding.clubLogo,
//                    "club_logos/" + team.logoFilename
//                )
//            } else {
//                binding!!.clubLogo.visibility = View.GONE
//            }
//            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
//                binding.flag.visibility = View.VISIBLE
//                firebaseStorageLoader.loadImage(
//                    context,
//                    binding.flag,
//                    "flags/" + team.nation!!.flagFilename
//                )
//            } else {
//                binding.flag.visibility = View.GONE
//            }
//            binding.name.text = team.name
//        } else {
//            binding!!.clubLogo.visibility = View.GONE
//            if (team.nation != null && !team.nation!!.flagFilename.isNullOrEmpty()) {
//                binding.flag.visibility = View.VISIBLE
//                firebaseStorageLoader.loadImage(
//                    context,
//                    binding.flag,
//                    "flags/" + team.nation!!.flagFilename
//                )
//            } else {
//                binding.flag.visibility = View.GONE
//            }
//            binding.name.text = team.name
//        }
    }
}
