package com.mmtran.turtlesoccer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.CompetitionPagerAdapter
import com.mmtran.turtlesoccer.adapters.CompetitionsAdapter
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
import com.mmtran.turtlesoccer.databinding.FragmentCompetitionsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil
import kotlin.random.Random

@SuppressLint("UseRequireInsteadOfGet")
class CompetitionsFragment : Fragment(), CompetitionsAdapter.ItemClickListener {

    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()
    private var campaignList: List<Campaign?>? = emptyList()

    private var binding: FragmentCompetitionsBinding? = null
    private var competition: Competition? = null
    private var compViewPager: ViewPager2? = null
    private var competitionPagerAdapter: CompetitionPagerAdapter? = null
    private var competitionsAdapter: CompetitionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        competition = if (arguments != null) arguments!!.getSerializable(EXTRA_COMPETITION) as Competition else null

        binding = FragmentCompetitionsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        ActionBarUtil.buildActionBar(layoutInflater, actionBar, R.layout.toolbar_competitions)
        if (isRenderCompetitionList()) {
            actionBar!!.setTitle(R.string.toolbar_competitions)
        } else {
            actionBar!!.title = competition!!.name
        }

        (activity as MainActivity).nationListViewModel!!.nationList.observe(
            viewLifecycleOwner,
            { nationList_: List<Nation?>? ->
                nationList = nationList_
                competitionsObserver()
            })
        (activity as MainActivity).teamListViewModel!!.teamList.observe(
            viewLifecycleOwner,
            { teamList_: List<Team?>? ->
                teamList = teamList_
                competitionsObserver()
            })
        (activity as MainActivity).tournamentListViewModel!!.tournamentList.observe(
            viewLifecycleOwner,
            { tournamentList_: List<Tournament?>? ->
                tournamentList = tournamentList_
                competitionsObserver()
            })
        (activity as MainActivity).competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                competitionsObserver()
            })
        (activity as MainActivity).campaignListViewModel!!.campaignList.observe(
            viewLifecycleOwner,
            { campaignList_: List<Campaign?>? ->
                campaignList = campaignList_
                competitionsObserver()
            })
    }

    private fun competitionsObserver() {

        if (competitionList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()
            || tournamentList.isNullOrEmpty() || campaignList.isNullOrEmpty()) return

        if (isRenderCompetitionList()) {
            binding!!.competitionList.visibility = View.VISIBLE
            renderCompetitionList()
            binding!!.competition.visibility = View.GONE
        } else {
            binding!!.competitionList.visibility = View.GONE
            binding!!.competition.visibility = View.VISIBLE
            renderCompetition()
        }
    }

    private fun renderCompetition() {

        CompetitionUtil.processCompetition(competition, tournamentList, campaignList, nationList, teamList)

        compViewPager = binding!!.competitionViewPager
        competitionPagerAdapter = CompetitionPagerAdapter.newInstance(parentFragmentManager, lifecycle)
        competitionPagerAdapter!!.setCompetition(competition!!)
        compViewPager!!.adapter = competitionPagerAdapter

        TabLayoutMediator(
            binding!!.competitionTabLayout, compViewPager!!
        ) { tab: TabLayout.Tab, position: Int ->
            tab.setText(
                TAB_RES[position]
            )
        }.attach()

    }

    private fun renderCompetitionList() {

        for (competition: Competition? in competitionList!!) {
            CompetitionUtil.processCompetition(competition, tournamentList, campaignList, nationList, teamList)
            competition!!.tournamentList = createRandomTournamentList(competition.tournamentList!!)
        }

        val recyclerView: RecyclerView = binding!!.competitionList
        recyclerView.layoutManager = LinearLayoutManager(context)
        CommonUtil.addDivider(recyclerView, requireContext(), R.drawable.divider_gray_5)
        competitionsAdapter = CompetitionsAdapter(context, competitionList!!)
        competitionsAdapter!!.setClickListener(this)
        recyclerView.adapter = competitionsAdapter
    }

    private fun isRenderCompetitionList(): Boolean {
        return competition == null
    }

    override fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int) {

        CompetitionUtil.browseToCompetition(context as MainActivity, competitionList[position]!!)
    }

    private fun createRandomTournamentList(tourList: List<Tournament?>) : List<Tournament?> {

        var result = emptyList<Tournament?>()
        var temp = tourList
        val len = tourList.size
        if (len <= 5) {
            for (i in 0 until len) {
                result = result + temp[i]
            }
        } else {
            for (i in 1..5) {
                val rIndex = Random.nextInt(temp.size)
                val rTournament = temp[rIndex]
                result = result + rTournament
                temp = temp - rTournament
            }
        }
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val TAB_RES =
            intArrayOf(R.string.competition_about, R.string.competition_results, R.string.competition_all_time_standings)
    }
}
