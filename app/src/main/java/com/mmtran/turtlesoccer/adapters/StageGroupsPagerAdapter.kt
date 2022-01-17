package com.mmtran.turtlesoccer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmtran.turtlesoccer.fragments.*
import com.mmtran.turtlesoccer.models.Campaign
import com.mmtran.turtlesoccer.models.Stage

class StageGroupsPagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var campaign: Campaign? = null

    fun setCampaign(campaign: Campaign) {
        this.campaign = campaign
    }

    override fun createFragment(position: Int): Fragment {
        if (campaign == null || getRoundRobinStages().isNullOrEmpty()
            || position < 0 || position >= getRoundRobinStages()!!.size) {
            return Fragment()
        }
        return StageGroupsFragment.newInstance(getRoundRobinStages()?.get(position))
    }

    override fun getItemCount(): Int {
        return if (campaign != null && !getRoundRobinStages().isNullOrEmpty()) getRoundRobinStages()!!.size else 0
    }

    private fun getRoundRobinStages(): List<Stage?>? {
        if (campaign == null) return emptyList()
        return if (!campaign!!.multipleLeagues!!) campaign!!.getRoundRobinStages() else campaign!!.leagueStages
    }

    companion object {
        fun newInstance(
            fragmentManager: FragmentManager?,
            lifecycle: Lifecycle?
        ): StageGroupsPagerAdapter {
            return StageGroupsPagerAdapter(fragmentManager, lifecycle)
        }
    }
}
