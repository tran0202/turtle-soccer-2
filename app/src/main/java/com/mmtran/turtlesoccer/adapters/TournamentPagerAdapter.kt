package com.mmtran.turtlesoccer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmtran.turtlesoccer.fragments.*
import com.mmtran.turtlesoccer.models.Tournament

class TournamentPagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var tournament: Tournament? = null

    fun setTournament(tournament: Tournament) {
        this.tournament = tournament
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_ABOUT -> TourAboutFragment.newInstance(tournament)
            PAGE_MATCHES -> TourMatchesFragment.newInstance(tournament)
            PAGE_GROUPS -> TourGroupsFragment.newInstance(tournament)
            PAGE_FINAL_STANDINGS -> TourFinalStandingsFragment.newInstance(tournament)
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int {
        return PAGE_COUNT
    }

    companion object {
        const val PAGE_ABOUT = 0
        const val PAGE_MATCHES = 1
        const val PAGE_GROUPS = 2
        const val PAGE_FINAL_STANDINGS = 3
        const val PAGE_COUNT = 4
        fun newInstance(
            fragmentManager: FragmentManager?,
            lifecycle: Lifecycle?
        ): TournamentPagerAdapter {
            return TournamentPagerAdapter(fragmentManager, lifecycle)
        }
    }
}
