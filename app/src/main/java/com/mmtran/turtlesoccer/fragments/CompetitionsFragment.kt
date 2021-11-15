package com.mmtran.turtlesoccer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.CompetitionPagerAdapter
import com.mmtran.turtlesoccer.adapters.CompetitionsAdapter
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
import com.mmtran.turtlesoccer.databinding.FragmentCompetitionsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil
import kotlin.random.Random

@SuppressLint("UseRequireInsteadOfGet")
class CompetitionsFragment : Fragment(), CompetitionsAdapter.ItemClickListener {

    private var nationListViewModel: NationListViewModel? = null
    private var teamListViewModel: TeamListViewModel? = null
    private var tournamentListViewModel: TournamentListViewModel? = null
    private var competitionListViewModel: CompetitionListViewModel? = null
    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()

    private var binding: FragmentCompetitionsBinding? = null
    private var competitionsAdapter: CompetitionsAdapter? = null
    private var competition: Competition? = null
    private var compViewPager: ViewPager2? = null
    private var competitionPagerAdapter: CompetitionPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val dataLoader = FirestoreLoader()
        nationListViewModel = ViewModelProvider(this).get(
            NationListViewModel::class.java
        )
        dataLoader.getNations(nationListViewModel!!)

        teamListViewModel = ViewModelProvider(this).get(
            TeamListViewModel::class.java
        )
        dataLoader.getTeams(teamListViewModel!!)

        tournamentListViewModel = ViewModelProvider(this).get(
            TournamentListViewModel::class.java
        )
        dataLoader.getTournaments(tournamentListViewModel!!)

        competitionListViewModel = ViewModelProvider(this).get(
            CompetitionListViewModel::class.java
        )
        dataLoader.getCompetitions(competitionListViewModel!!)

        competition = if (arguments != null) arguments!!.getSerializable(EXTRA_COMPETITION) as Competition else null

        binding = FragmentCompetitionsBinding.inflate(inflater, container, false)

        compViewPager = binding!!.competitionViewPager

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

        nationListViewModel!!.nationList.observe(
            viewLifecycleOwner,
            { nationList_: List<Nation?>? ->
                nationList = nationList_
                competitionsObserver()
            })
        teamListViewModel!!.teamList.observe(
            viewLifecycleOwner,
            { teamList_: List<Team?>? ->
                teamList = teamList_
                competitionsObserver()
            })
        tournamentListViewModel!!.tournamentList.observe(
            viewLifecycleOwner,
            { tournamentList_: List<Tournament?>? ->
                tournamentList = tournamentList_
                competitionsObserver()
            })
        competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                competitionsObserver()
            })
    }

    private fun competitionsObserver() {

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

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return

        CompetitionUtil.getChampion(competition, nationList, teamList)
        CompetitionUtil.getMostSuccessfulTeams(competition, nationList, teamList)

        tournamentList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
        competition!!.tournamentList = tournamentList
        TournamentUtil.attachCompetition(tournamentList, competition)

        competition!!.tournamentList = competition!!.tournamentList!!.reversed()
        TournamentUtil.processTournament(competition!!.tournamentList!!)

        TournamentUtil.processFinalStandings(competition, nationList, teamList)

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

        if (competitionList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return

        for (competition: Competition? in competitionList!!) {
            CompetitionUtil.getChampion(competition, nationList, teamList)
            CompetitionUtil.getMostSuccessfulTeams(competition, nationList, teamList)
            val tourList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
            TournamentUtil.attachCompetition(tourList, competition)
            competition!!.tournamentList = createRandomTournamentList(tourList)
        }

        val recyclerView: RecyclerView = binding!!.competitionList
        recyclerView.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_gray_5
            )!!
        )
        recyclerView.addItemDecoration(divider)
        competitionsAdapter = CompetitionsAdapter(context, competitionList!!)
        competitionsAdapter!!.setClickListener(this)
        recyclerView.adapter = competitionsAdapter
    }

    private fun isRenderCompetitionList(): Boolean {
        return competition == null
    }

    override fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int) {

        val args = Bundle()
        args.putSerializable(EXTRA_COMPETITION, competitionList[position]!!)
        val navController = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_competitions, args)
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
//            var j = 5
//            if (j > len) {
//                j = len
//            }
//            for (i in j-5 until j) {
//                temp[i]!!.competition = competition
//                result = result + temp[i]
//            }
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
