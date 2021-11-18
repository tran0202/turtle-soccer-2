package com.mmtran.turtlesoccer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.EXTRA_TOURNAMENT
import com.mmtran.turtlesoccer.adapters.TournamentsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTournamentsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil.buildActionBar
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

@SuppressLint("UseRequireInsteadOfGet")
class TournamentsFragment : Fragment(), TournamentsAdapter.ItemClickListener {

    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()

    private var binding: FragmentTournamentsBinding? = null
    private var tournament: Tournament? = null
    private var latestTournamentsList: List<Tournament?>? = emptyList()
    private var tournamentsAdapter: TournamentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tournament = if (arguments != null) arguments!!.getSerializable(EXTRA_TOURNAMENT) as Tournament else null

        binding = FragmentTournamentsBinding.inflate(inflater, container,false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        buildActionBar(layoutInflater, actionBar, R.layout.toolbar_tournaments)
        actionBar!!.setTitle(R.string.toolbar_tournaments)

        (activity as MainActivity).nationListViewModel!!.nationList.observe(
            viewLifecycleOwner,
            { nationList_: List<Nation?>? ->
                nationList = nationList_
                tournamentsObserver()
            })
        (activity as MainActivity).teamListViewModel!!.teamList.observe(
            viewLifecycleOwner,
            { teamList_: List<Team?>? ->
                teamList = teamList_
                tournamentsObserver()
            })
        (activity as MainActivity).tournamentListViewModel!!.tournamentList.observe(
            viewLifecycleOwner,
            { tournamentList_: List<Tournament?>? ->
                tournamentList = tournamentList_
                tournamentsObserver()
            })
        (activity as MainActivity).competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                tournamentsObserver()
            })
    }

    private fun tournamentsObserver() {

        if (isRenderTournamentList()) {
            binding!!.tournamentList.visibility = View.VISIBLE
            renderTournamentList()
            binding!!.tournament.visibility = View.GONE
        } else {
            binding!!.tournamentList.visibility = View.GONE
            binding!!.tournament.visibility = View.VISIBLE
            renderTournament()
        }

    }

    private fun renderTournament() {

    }

    private fun renderTournamentList() {

        if (competitionList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return

        for (competition: Competition? in competitionList!!) {
            val tourList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
            if (tourList.isNotEmpty()) {
                tourList.sortedBy { tournament -> tournament!!.details!!.startDate }
                val tournament = tourList[tourList.size - 1]
                tournament!!.competition = competition
                TournamentUtil.processFinalStandings(tournament, nationList, teamList)
                latestTournamentsList = latestTournamentsList?.plus(tournament)
            }
        }

        val recyclerView: RecyclerView = binding!!.tournamentList
        recyclerView.layoutManager = LinearLayoutManager(context)
        CommonUtil.addDivider(recyclerView, requireContext())
        tournamentsAdapter = TournamentsAdapter(context, latestTournamentsList!!)
        tournamentsAdapter!!.setClickListener(this)
        recyclerView.adapter = tournamentsAdapter
    }

    private fun isRenderTournamentList(): Boolean {
        return tournament == null
    }

    override fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int) {

        val args = Bundle()
        args.putSerializable(EXTRA_TOURNAMENT, tournamentList[position]!!)
        val navController = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_tournaments, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
