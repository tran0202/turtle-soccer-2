package com.mmtran.turtlesoccer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmtran.turtlesoccer.fragments.CompAboutFragment
import com.mmtran.turtlesoccer.fragments.CompAllTimeStandingsFragment
import com.mmtran.turtlesoccer.fragments.CompResultsFragment
import com.mmtran.turtlesoccer.models.Competition

class CompetitionPagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var competition: Competition? = null

    fun setCompetition(competition: Competition) {
        this.competition = competition
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_ABOUT -> CompAboutFragment.newInstance(competition)
            PAGE_RESULTS -> CompResultsFragment.newInstance(competition)
            PAGE_ALL_TIME_STANDINGS -> CompAllTimeStandingsFragment.newInstance(competition)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    companion object {
        const val PAGE_ABOUT = 0
        const val PAGE_RESULTS = 1
        const val PAGE_ALL_TIME_STANDINGS = 2
        const val PAGE_COUNT = 3
        fun newInstance(
            fragmentManager: FragmentManager?,
            lifecycle: Lifecycle?
        ): CompetitionPagerAdapter {
            return CompetitionPagerAdapter(fragmentManager, lifecycle)
        }
    }
}
