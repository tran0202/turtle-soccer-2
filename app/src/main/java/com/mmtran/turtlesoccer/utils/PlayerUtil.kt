package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import com.mmtran.turtlesoccer.databinding.FragmentPlayerFlagNameBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Player

object PlayerUtil {

    fun renderPlayerFlagName(context: Context?, binding: FragmentPlayerFlagNameBinding?, player: Player?) {

        if (player == null) return

        val firebaseStorageLoader = FirebaseStorageLoader(context)
        val factor: Float = context!!.resources.displayMetrics.density

        if (!player.isMultipleClubs()) {
            binding!!.singleClubLogo.visibility = View.VISIBLE
            if (player.club != null && player.club!!.isValid() && !player.club!!.logoFilename.isNullOrEmpty()) {
                binding.clubLogo.visibility = View.VISIBLE
                firebaseStorageLoader.loadImage(
                    context,
                    binding.clubLogo,
                    "club_logos/" + player.club!!.logoFilename
                )
            } else {
                binding.clubLogo.visibility = View.GONE
            }
            binding.multipleClubLogo.visibility = View.GONE
        } else {
            binding!!.singleClubLogo.visibility = View.GONE
            binding.multipleClubLogo.visibility = View.VISIBLE
            if (player.club != null && player.club!!.isValid() && !player.club!!.logoFilename.isNullOrEmpty()) {
                binding.clubLogo1.visibility = View.VISIBLE
                firebaseStorageLoader.loadImage(
                    context,
                    binding.clubLogo1,
                    "club_logos/" + player.club!!.logoFilename
                )
            } else {
                binding.clubLogo1.visibility = View.GONE
            }
            if (player.club2 != null && player.club2!!.isValid() && !player.club2!!.logoFilename.isNullOrEmpty()) {
                binding.clubLogo2.visibility = View.VISIBLE
                firebaseStorageLoader.loadImage(
                    context,
                    binding.clubLogo2,
                    "club_logos/" + player.club2!!.logoFilename
                )
            } else {
                binding.clubLogo2.visibility = View.GONE
            }
        }

        if (player.team != null && player.team!!.isValid() && player.team!!.nation != null
            && !player.team!!.nation!!.flagFilename.isNullOrEmpty()) {
            binding.flag.visibility = View.VISIBLE
            firebaseStorageLoader.loadImage(
                context,
                binding.flag,
                "flags/" + player.team!!.nation!!.flagFilename
            )
            if (player.isClubPlayer()) {
                binding.flag.layoutParams.width = (24 * factor).toInt()
            } else {
                binding.flag.layoutParams.width = (36 * factor).toInt()
            }
        } else {
            binding.flag.visibility = View.GONE
        }

        binding.name.text = player.name
    }

    fun getGoalsAssistsMinutes(player: Player?): String {

        if (player?.goals == null) return ""

        return "(" + player.goals!!.toString() + ")"
//        if (player.assists == null && player.minutes == null) {
//            return "(" + player.goals!!.toString() + "g)"
//        }
//
//        if (player.assists != null) {
//            return if (player.minutes != null) {
//                "(" + player.goals!!.toString() + "g, " + player.assists!!.toString() + "a, " + player.minutes!!.toString() + "m)"
//            } else {
//                "(" + player.goals!!.toString() + "g, " + player.assists!!.toString() + "a)"
//            }
//        }
//
//        return "(" + player.goals!!.toString() + "g, " + player.minutes!!.toString() + "m)"
    }
}
