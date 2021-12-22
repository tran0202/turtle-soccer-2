package com.mmtran.turtlesoccer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmtran.turtlesoccer.fragments.*
import com.mmtran.turtlesoccer.models.Campaign

class StagePagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var campaign: Campaign? = null

    fun setCampaign(campaign: Campaign) {
        this.campaign = campaign
    }

    override fun createFragment(position: Int): Fragment {
        if (campaign == null || campaign!!.stages.isNullOrEmpty() || position < 0 || position >= campaign!!.stages!!.size) {
            return Fragment()
        }
        return StageFragment.newInstance(campaign!!.stages?.get(position))
    }

    override fun getItemCount(): Int {
        return if (campaign != null && !campaign!!.stages.isNullOrEmpty()) campaign!!.stages!!.size else 0
    }

    companion object {
        fun newInstance(
            fragmentManager: FragmentManager?,
            lifecycle: Lifecycle?
        ): StagePagerAdapter {
            return StagePagerAdapter(fragmentManager, lifecycle)
        }
    }
}
