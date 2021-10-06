package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CompetitionsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompetitionsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.ActionBarUtil

class CompetitionsFragment : Fragment() {

    private var nationListViewModel: AssociationListViewModel? = null
    private var clubListViewModel: ClubListViewModel? = null
    private var competitionListViewModel: CompetitionListViewModel? = null
    private var nationList: List<Nation?>? = emptyList()
    private var clubList: List<Club?>? = emptyList()
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

        clubListViewModel = ViewModelProvider(this).get(
            ClubListViewModel::class.java
        )
        dataLoader.getClubs(clubListViewModel!!)

        competitionListViewModel = ViewModelProvider(this).get(
            CompetitionListViewModel::class.java
        )
        dataLoader.getCompetitions(competitionListViewModel!!)

        binding = FragmentCompetitionsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        competitionsAdapter = CompetitionsAdapter(requireContext())
        binding!!.competitionList.adapter = competitionsAdapter

        return root
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
                joinChampionNation()
            })
        clubListViewModel!!.clubList.observe(
            viewLifecycleOwner,
            { clubList_: List<Club?>? ->
                clubList = clubList_
                joinChampionNation()
            })
        competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                joinChampionNation()
            })
    }

    private fun joinChampionNation() {
        if (competitionList.isNullOrEmpty() || nationList.isNullOrEmpty() || clubList.isNullOrEmpty()) return
        for (competition: Competition? in competitionList!!) {
            var championId = if (competition!!.currentChampions !== null) {
                competition!!.currentChampions!!;
            } else {
                competition!!.lastChampions!!;
            }
            if (competition.isClubCompetition()) {
                val club = clubList!!.find { it!!.id.equals(championId) }
                if (club!= null) {
                    val nation = nationList!!.find { it!!.id.equals(club.nationId) }
                    if (nation != null) {
                        club.nation = nation
                    }
                    competition.currentChampionClub = club
                }
            } else {
                val nation = nationList!!.find { it!!.id.equals(championId) }
                if (nation!= null) {
                    competition.currentChampionNation = nation
                }
            }
        }
        competitionsAdapter!!.setData(competitionList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
