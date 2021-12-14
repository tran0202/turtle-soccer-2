package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.databinding.FragmentHomeBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.TournamentsAdapter
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil.buildActionBar
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class HomeFragment : Fragment(), TournamentsAdapter.ItemClickListener {

    private var homeViewModel: HomeViewModel? = null

    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()

    private var binding: FragmentHomeBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null
    private var currentTournamentsList: List<Tournament?>? = emptyList()
    private var tournamentsAdapter: TournamentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(modelClass = HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container,false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        buildActionBar(layoutInflater, actionBar, R.layout.toolbar_home)
        actionBar!!.setTitle(R.string.toolbar_home)

        firebaseStorageLoader = FirebaseStorageLoader(context)
        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.logoHome,
            "logos/Turtle_Soccer_logo.png"
        )
        firebaseStorageLoader!!.loadImage(activity, binding!!.heroImage, "soccer_ts1475731972.jpg")

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

        renderTournamentList()
    }

    private fun renderTournamentList() {

        if (competitionList.isNullOrEmpty() || tournamentList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (competition: Competition? in competitionList!!) {
            val tourList = tournamentList!!.filter { it!!.competitionId == competition!!.id && it.active!! }
            if (tourList.isNotEmpty()) {
                tourList.sortedBy { tournament -> tournament!!.details!!.startDate }
                val tournament = tourList[0]
                tournament!!.competition = competition
                TournamentUtil.processFinalStandings(tournament, nationList, teamList)
                currentTournamentsList = currentTournamentsList?.plus(tournament)
            }
        }

        val recyclerView: RecyclerView = binding!!.tournamentList
        recyclerView.layoutManager = LinearLayoutManager(context)
        CommonUtil.addDivider(recyclerView, requireContext(), R.drawable.divider_gray_5)
        tournamentsAdapter = TournamentsAdapter(context, currentTournamentsList!!)
        tournamentsAdapter!!.setClickListener(this)
        recyclerView.adapter = tournamentsAdapter
    }

    override fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int) {

        TournamentUtil.browseToTournament(context as MainActivity, tournamentList[position]!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
