package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mmtran.turtlesoccer.adapters.StageMatchesPagerAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourMatchesBinding
import com.mmtran.turtlesoccer.models.TourMatchesViewModel
import com.mmtran.turtlesoccer.models.Tournament

class TourMatchesFragment(tour: Tournament?) : Fragment() {

    private var tourMatchesViewModel: TourMatchesViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourMatchesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourMatchesViewModel = ViewModelProvider(this).get(modelClass = TourMatchesViewModel::class.java)
        tourMatchesViewModel!!.setTournament(tournament!!)

        binding = FragmentTourMatchesBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stageMatchesViewPager: ViewPager2 = binding!!.stageMatchesViewPager
        val stageMatchesPagerAdapter: StageMatchesPagerAdapter = StageMatchesPagerAdapter.newInstance(childFragmentManager, lifecycle)
        stageMatchesPagerAdapter.setCampaign(tournament!!.currentCampaign!!)
        stageMatchesViewPager.adapter = stageMatchesPagerAdapter

        TabLayoutMediator(binding!!.stageTabLayout, stageMatchesViewPager) {
                tab: TabLayout.Tab, position: Int ->
            tab.text = if (tournament!!.currentCampaign!!.stages!![position] != null) tournament!!.currentCampaign!!.stages!![position]!!.name else ""
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(tour: Tournament?): Fragment {
            return TourMatchesFragment(tour)
        }
    }
}
