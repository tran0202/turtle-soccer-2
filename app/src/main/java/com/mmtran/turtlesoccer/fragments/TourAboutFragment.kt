package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.TeamsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourAboutBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.TourAboutViewModel
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class TourAboutFragment(tour: Tournament?) : Fragment() {

    private var tourAboutViewModel: TourAboutViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourAboutBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null
    private var hostsAdapter: TeamsAdapter? = null
    private var finalHostsAdapter: TeamsAdapter? = null
    private var tournamentThirdPlaceAdapter: TeamsAdapter? = null
    private var fairPlayAdapter: TeamsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourAboutViewModel = ViewModelProvider(this).get(modelClass = TourAboutViewModel::class.java)
        tourAboutViewModel!!.setTournament(tournament!!)

        binding = FragmentTourAboutBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseStorageLoader = FirebaseStorageLoader(requireContext())

        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.tournamentLogo,
            tournament!!.competition!!.logoPath + "/" + tournament!!.details!!.logoFilename
        )

        TournamentUtil.renderHostLabel(tournament!!.details!!.host, binding!!.hostLabel, binding!!.hostsLabel)
        val hostRecyclerView: RecyclerView = binding!!.hostList
        hostRecyclerView.layoutManager = LinearLayoutManager(context)
        hostsAdapter = TeamsAdapter(context, tournament!!.details!!.hostTeam!!)
        hostRecyclerView.adapter = hostsAdapter

        val tournamentDates = TournamentUtil.renderDates(context, tournament)
        CommonUtil.renderLabelField(tournamentDates, binding!!.tournamentDatesLabel, binding!!.tournamentDates)

        val qualifyingDates = TournamentUtil.renderQualifyingDates(context, tournament)
        CommonUtil.renderLabelField(qualifyingDates, binding!!.qualifyingDatesLabel, binding!!.qualifyingDates)

        val competitionDates = TournamentUtil.renderCompetitionDates(context, tournament)
        CommonUtil.renderLabelField(competitionDates, binding!!.competitionDatesLabel, binding!!.competitionDates)

        val leagueDates = TournamentUtil.renderLeagueDates(context, tournament)
        CommonUtil.renderLabelField(leagueDates, binding!!.leagueDatesLabel, binding!!.leagueDates)

        val finalDates = TournamentUtil.renderFinalDates(context, tournament)
        CommonUtil.renderLabelField(finalDates, binding!!.finalDatesLabel, binding!!.finalDates)

        val relegationDates = TournamentUtil.renderRelegationDates(context, tournament)
        CommonUtil.renderLabelField(relegationDates, binding!!.relegationDatesLabel, binding!!.relegationDates)

        val teamCount = TournamentUtil.renderTeamCount(context, tournament)
        CommonUtil.renderLabelField(teamCount, binding!!.teamCountLabel, binding!!.teamCount)

        val competitionTeamCount = TournamentUtil.renderCompetitionTeamCount(context, tournament)
        CommonUtil.renderLabelField(competitionTeamCount, binding!!.competitionTeamCountLabel, binding!!.competitionTeamCount)

        val competitionPlusTransferTeamCount = TournamentUtil.renderCompetitionPlusTransferTeamCount(context, tournament)
        CommonUtil.renderLabelField(competitionPlusTransferTeamCount, binding!!.competitionPlusTransferTeamCountLabel, binding!!.competitionPlusTransferTeamCount)

        val totalTeamCount = TournamentUtil.renderTotalTeamCount(context, tournament)
        CommonUtil.renderLabelField(totalTeamCount, binding!!.totalTeamCountLabel, binding!!.totalTeamCount)

        val totalPlusTransferTeamCount = TournamentUtil.renderTotalPlusTransferTeamCount(context, tournament)
        CommonUtil.renderLabelField(totalPlusTransferTeamCount, binding!!.totalPlusTransferTeamCountLabel, binding!!.totalPlusTransferTeamCount)

        val venueCount = TournamentUtil.renderVenueCount(context, tournament)
        CommonUtil.renderLabelField(venueCount, binding!!.venueCountLabel, binding!!.venueCount)

        TournamentUtil.renderHostLabel(tournament!!.details!!.finalHost, binding!!.finalHostLabel, binding!!.finalHostsLabel)
        val finalHostRecyclerView: RecyclerView = binding!!.finalHostList
        finalHostRecyclerView.layoutManager = LinearLayoutManager(context)
        finalHostsAdapter = TeamsAdapter(context, tournament!!.details!!.finalHostTeam!!)
        finalHostRecyclerView.adapter = finalHostsAdapter

        val finalTeamCount = TournamentUtil.renderFinalTeamCount(context, tournament)
        CommonUtil.renderLabelField(finalTeamCount, binding!!.finalTeamCountLabel, binding!!.finalTeamCount)

        val finalVenueCount = TournamentUtil.renderFinalVenueCount(context, tournament)
        CommonUtil.renderLabelField(finalVenueCount, binding!!.finalVenueCountLabel, binding!!.finalVenueCount)

        TournamentUtil.renderFinalStandings(context, tournament!!.finalStandings!!.championTeam,
            binding!!.championsLabel, binding!!.champions, binding!!.championsFlagName)

        TournamentUtil.renderFinalStandings(context, tournament!!.finalStandings!!.runnersUpTeam,
            binding!!.runnersUpLabel, binding!!.runnersUp, binding!!.runnersUpFlagName)

        if (tournament!!.finalStandings!!.thirdPlaceTeam!!.isNotEmpty()) {
            binding!!.thirdPlaceLabel.visibility = View.VISIBLE
            val recyclerView: RecyclerView = binding!!.thirdPlaceList
            recyclerView.layoutManager = LinearLayoutManager(context)
            CommonUtil.addDivider(recyclerView, requireContext(), R.drawable.no_divider)
            tournamentThirdPlaceAdapter = TeamsAdapter(context, tournament!!.finalStandings!!.thirdPlaceTeam!!)
            recyclerView.adapter = tournamentThirdPlaceAdapter
        } else {
            binding!!.thirdPlaceLabel.visibility = View.GONE
        }

        TournamentUtil.renderFinalStandings(context, tournament!!.finalStandings!!.fourthPlaceTeam,
            binding!!.fourthPlaceLabel, binding!!.fourthPlace, binding!!.fourthPlaceFlagName)

        val matchesPlayed = if (tournament!!.statistics!!.totalMatches != null) tournament!!.statistics!!.totalMatches.toString() else ""
        CommonUtil.renderLabelField(matchesPlayed, binding!!.matchesPlayedLabel, binding!!.matchesPlayed)

        val finalMatchesPlayed = if (tournament!!.statistics!!.finalMatches != null) tournament!!.statistics!!.finalMatches.toString() else ""
        CommonUtil.renderLabelField(finalMatchesPlayed, binding!!.finalMatchesPlayedLabel, binding!!.finalMatchesPlayed)

        val goalsScored = TournamentUtil.renderGoalsScored(context, tournament)
        CommonUtil.renderLabelField(goalsScored, binding!!.goalsScoredLabel, binding!!.goalsScored)

        val finalGoalsScored = TournamentUtil.renderFinalGoalsScored(context, tournament)
        CommonUtil.renderLabelField(finalGoalsScored, binding!!.finalGoalsScoredLabel, binding!!.finalGoalsScored)

        val attendance = TournamentUtil.renderAttendance(context, tournament)
        CommonUtil.renderLabelField(attendance, binding!!.attendanceLabel, binding!!.attendance)

        val finalAttendance = TournamentUtil.renderFinalAttendance(context, tournament)
        CommonUtil.renderLabelField(finalAttendance, binding!!.finalAttendanceLabel, binding!!.finalAttendance)

        if (tournament!!.awards!!.goldenBoot!!.isNotEmpty()) {
            if (tournament!!.awards!!.goldenBoot!!.size == 1) {
                binding!!.goldenBootLabel.visibility = View.VISIBLE
                binding!!.goldenBootsLabel.visibility = View.GONE
            } else {
                binding!!.goldenBootLabel.visibility = View.GONE
                binding!!.goldenBootsLabel.visibility = View.VISIBLE
            }
//            val recyclerView: RecyclerView = binding!!.thirdPlaceList
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            CommonUtil.addDivider(recyclerView, requireContext(), R.drawable.no_divider)
//            tournamentThirdPlaceAdapter = TournamentThirdPlaceAdapter(context, tournament!!.finalStandings!!.thirdPlaceTeam!!)
//            recyclerView.adapter = tournamentThirdPlaceAdapter
        } else {
            binding!!.goldenBootLabel.visibility = View.GONE
            binding!!.goldenBootsLabel.visibility = View.GONE
        }

        if (tournament!!.awards!!.fairPlayTeam!!.isNotEmpty()) {
            binding!!.fairPlayLabel.visibility = View.VISIBLE
            val fairPlayRecyclerView: RecyclerView = binding!!.fairPlayList
            fairPlayRecyclerView.layoutManager = LinearLayoutManager(context)
            CommonUtil.addDivider(fairPlayRecyclerView, requireContext(), R.drawable.no_divider)
            fairPlayAdapter = TeamsAdapter(context, tournament!!.awards!!.fairPlayTeam!!)
            fairPlayRecyclerView.adapter = fairPlayAdapter
        } else {
            binding!!.fairPlayLabel.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(tour: Tournament?): Fragment {
            return TourAboutFragment(tour)
        }
    }
}
