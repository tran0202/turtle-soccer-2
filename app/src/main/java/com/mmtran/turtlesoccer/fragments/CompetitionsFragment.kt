package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CompetitionsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompetitionsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import kotlin.random.Random

class CompetitionsFragment : Fragment() {

    private var nationListViewModel: AssociationListViewModel? = null
    private var teamListViewModel: TeamListViewModel? = null
    private var tournamentListViewModel: TournamentListViewModel? = null
    private var competitionListViewModel: CompetitionListViewModel? = null
    private var nationList: List<Nation?>? = emptyList()
    private var teamList: List<Team?>? = emptyList()
    private var tournamentList: List<Tournament?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()

    private var binding: FragmentCompetitionsBinding? = null
    private var competitionsAdapter: CompetitionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val dataLoader = FirestoreLoader()
        nationListViewModel = ViewModelProvider(this).get(
            AssociationListViewModel::class.java
        )
        dataLoader.getActiveNations(nationListViewModel!!)

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

        binding = FragmentCompetitionsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        ActionBarUtil.buildActionBar(layoutInflater, actionBar, R.layout.toolbar_competitions)
        actionBar!!.setTitle(R.string.toolbar_competitions)

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

        if (competitionList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || tournamentList.isNullOrEmpty()) return
        
        for (competition: Competition? in competitionList!!) {
            CompetitionUtil.getChampion(competition, nationList, teamList)
            val tourList = tournamentList!!.filter { it!!.competitionId == competition!!.id }
            competition!!.tournamentList = createRandomTournamentList(tourList, competition)
        }

        val recyclerView: RecyclerView = binding!!.competitionList
        recyclerView.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_gray_4
            )!!
        )
        recyclerView.addItemDecoration(divider)
        competitionsAdapter = CompetitionsAdapter(context, competitionList!!)
        recyclerView.adapter = competitionsAdapter
    }

    private fun createRandomTournamentList(tourList: List<Tournament?>, competition: Competition) : List<Tournament?> {
        var result = emptyList<Tournament?>()
        var temp = tourList
        val len = tourList.size
        if (len <= 5) {
            for (i in 0 until len) {
                temp[i]!!.competition = competition
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
                rTournament!!.competition = competition
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
}
