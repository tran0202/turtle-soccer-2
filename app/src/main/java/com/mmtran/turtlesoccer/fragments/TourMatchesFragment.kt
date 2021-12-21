package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CampaignPagerAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourMatchesBinding
import com.mmtran.turtlesoccer.models.TourMatchesViewModel
import com.mmtran.turtlesoccer.models.Tournament

class TourMatchesFragment(tour: Tournament?) : Fragment() {

    private var tourMatchesViewModel: TourMatchesViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourMatchesBinding? = null
    private var campaignViewPager: ViewPager2? = null
    private var campaignPagerAdapter: CampaignPagerAdapter? = null

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

        if (tournament!!.campaigns!!.isNotEmpty()) {
            if (tournament!!.campaigns!!.size > 1) {
                binding!!.multipleCampaigns.visibility = View.VISIBLE
                binding!!.singleCampaign.visibility = View.GONE

                campaignViewPager = binding!!.campaignViewPager
                campaignPagerAdapter = CampaignPagerAdapter.newInstance(parentFragmentManager, lifecycle)
                campaignPagerAdapter!!.setTournament(tournament!!)
                campaignViewPager!!.adapter = campaignPagerAdapter

                TabLayoutMediator(binding!!.campaignTabLayout, campaignViewPager!!) {
                    tab: TabLayout.Tab, position: Int ->
                    tab.text = tournament!!.campaigns!![position]!!.name
                }.attach()
            } else {
                binding!!.multipleCampaigns.visibility = View.GONE
                binding!!.singleCampaign.visibility = View.VISIBLE

                loadCampaign(CampaignFragment.newInstance(tournament!!.campaigns!![0]))
            }
        } else {
            binding!!.multipleCampaigns.visibility = View.GONE
            binding!!.singleCampaign.visibility = View.GONE
        }
    }

    private fun loadCampaign(fragment: Fragment) {
        val fm: FragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.single_campaign, fragment)
        fragmentTransaction.commit()
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
