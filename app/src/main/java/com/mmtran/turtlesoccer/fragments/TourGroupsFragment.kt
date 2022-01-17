package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.StageGroupsPagerAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourGroupsBinding
import com.mmtran.turtlesoccer.models.Campaign
import com.mmtran.turtlesoccer.models.TourGroupsViewModel
import com.mmtran.turtlesoccer.models.Tournament

class TourGroupsFragment(tour: Tournament?) : Fragment() {

    private var tourGroupsViewModel: TourGroupsViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourGroupsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourGroupsViewModel = ViewModelProvider(this).get(modelClass = TourGroupsViewModel::class.java)
        tourGroupsViewModel!!.setTournament(tournament!!)

        binding = FragmentTourGroupsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentCampaign: Campaign = tournament!!.currentCampaign!!
        val roundRobinStages = if (!currentCampaign.multipleLeagues!!) currentCampaign.getRoundRobinStages() else currentCampaign.leagueStages!!
        if (roundRobinStages.isEmpty()) return
        if (roundRobinStages.size == 1) {
            binding!!.singleStage.visibility = View.VISIBLE
            val ft: FragmentTransaction = childFragmentManager.beginTransaction()
            ft.replace(R.id.single_stage, StageGroupsFragment.newInstance(roundRobinStages[0]))
            ft.commit()
            binding!!.stageTabLayout.visibility = View.GONE
            binding!!.stageGroupsViewPager.visibility = View.GONE
        } else {
            binding!!.singleStage.visibility = View.GONE

            val stageGroupsViewPager: ViewPager2 = binding!!.stageGroupsViewPager
            val stageGroupsPagerAdapter: StageGroupsPagerAdapter = StageGroupsPagerAdapter.newInstance(childFragmentManager, lifecycle)
            stageGroupsPagerAdapter.setCampaign(currentCampaign)
            stageGroupsViewPager.adapter = stageGroupsPagerAdapter

            TabLayoutMediator(binding!!.stageTabLayout, stageGroupsViewPager) {
                    tab: TabLayout.Tab, position: Int ->
                tab.text = if (roundRobinStages[position] != null) roundRobinStages[position]!!.name else ""
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(tour: Tournament?): Fragment {
            return TourGroupsFragment(tour)
        }
    }
}
