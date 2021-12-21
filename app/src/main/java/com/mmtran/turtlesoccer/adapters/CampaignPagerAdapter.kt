package com.mmtran.turtlesoccer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmtran.turtlesoccer.fragments.*
import com.mmtran.turtlesoccer.models.Tournament

class CampaignPagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var tournament: Tournament? = null

    fun setTournament(tournament: Tournament) {
        this.tournament = tournament
    }

    override fun createFragment(position: Int): Fragment {
        if (tournament == null || tournament!!.campaigns.isNullOrEmpty() || position < 0 || position >= tournament!!.campaigns!!.size) {
            return Fragment()
        }
        return CampaignFragment.newInstance(tournament!!.campaigns?.get(position))
    }

    override fun getItemCount(): Int {
        return if (tournament != null && !tournament!!.campaigns.isNullOrEmpty()) tournament!!.campaigns!!.size else 0
    }

    companion object {
        fun newInstance(
            fragmentManager: FragmentManager?,
            lifecycle: Lifecycle?
        ): CampaignPagerAdapter {
            return CampaignPagerAdapter(fragmentManager, lifecycle)
        }
    }
}
