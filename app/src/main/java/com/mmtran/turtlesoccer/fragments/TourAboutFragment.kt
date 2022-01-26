package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.CampaignsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourAboutBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Campaign
import com.mmtran.turtlesoccer.models.TourAboutViewModel
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class TourAboutFragment(tour: Tournament?) : Fragment(), CampaignsAdapter.ItemClickListener {

    private var tourAboutViewModel: TourAboutViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourAboutBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null
    private var campaignsAdapter: CampaignsAdapter? = null

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

        if (tournament!!.tournamentLogo().isNotEmpty()) {
            firebaseStorageLoader!!.loadImage(activity, binding!!.tournamentLogo, tournament!!.tournamentLogo())
        }

        binding!!.tournamentName.text = tournament!!.tournamentName()

        if (!tournament!!.isQualifier() && tournament!!.previousTournament != null && tournament!!.previousTournament!!.year!!.isNotEmpty()) {
            binding!!.previous.visibility = View.VISIBLE
            binding!!.leftArrow.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding!!.leftArrow.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToFinalTournament(context as MainActivity, tournament!!.previousTournament)
            })
            binding!!.previousTournament.text = tournament!!.previousTournament!!.year
            binding!!.previousTournament.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToFinalTournament(context as MainActivity, tournament!!.previousTournament)
            })
        } else {
            binding!!.previous.visibility = View.GONE
        }

        if (!tournament!!.isQualifier() && tournament!!.competition != null && !tournament!!.competition!!.name.isNullOrEmpty()) {
            binding!!.competition.text = tournament!!.competition!!.name!!
        } else {
            binding!!.competition.visibility = View.GONE
        }
        binding!!.competition.setOnClickListener(View.OnClickListener {
            CompetitionUtil.browseToCompetition(context as MainActivity, tournament!!.competition)
        })

        if (!tournament!!.isQualifier() && tournament!!.nextTournament != null && !tournament!!.nextTournament!!.year.isNullOrEmpty()) {
            binding!!.next.visibility = View.VISIBLE
            binding!!.nextTournament.text = tournament!!.nextTournament!!.year
            binding!!.nextTournament.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToFinalTournament(context as MainActivity, tournament!!.nextTournament)
            })
            binding!!.rightArrow.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding!!.rightArrow.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToFinalTournament(context as MainActivity, tournament!!.nextTournament)
            })
        } else {
            binding!!.next.visibility = View.GONE
        }

        val campaignLinks = getCampaignLinks()
        if (!campaignLinks.isNullOrEmpty()) {
            binding!!.campaignLinks.visibility = View.VISIBLE

            val recyclerView: RecyclerView = binding!!.campaignList
            recyclerView.layoutManager = GridLayoutManager(context, 1)
            campaignsAdapter = CampaignsAdapter(context, campaignLinks)
            campaignsAdapter!!.setClickListener(this)
            recyclerView.adapter = campaignsAdapter
        } else {
            binding!!.campaignLinks.visibility = View.GONE
        }

        if (!tournament!!.isQualifier() && tournament!!.heroImages != null && tournament!!.heroImages!!.isNotEmpty()) {
            firebaseStorageLoader!!.loadImage(
                activity,
                binding!!.heroImage,
                "tournaments/" + tournament!!.id + "/" + tournament!!.heroImages!![0]!!.filename
            )
        }

        CommonUtil.renderLabelField(context, tournament!!.tournamentOriginalName(), binding!!.originalName, R.string.original_name_label)

        CommonUtil.renderTeamList(context, tournament!!.tournamentHost(), binding!!.host,
            R.string.host_label, R.string.hosts_label)

        val tournamentDates = TournamentUtil.renderDates(context, tournament)
        CommonUtil.renderLabelField(context, tournamentDates, binding!!.tournamentDates, R.string.tournament_dates_label)

        val qualifyingDates = TournamentUtil.renderQualifyingDates(context, tournament)
        CommonUtil.renderLabelField(context, qualifyingDates, binding!!.qualifyingDates, R.string.qualifying_dates_label)

        val competitionDates = TournamentUtil.renderCompetitionDates(context, tournament)
        CommonUtil.renderLabelField(context, competitionDates, binding!!.competitionDates, R.string.competition_dates_label)

        val leagueDates = TournamentUtil.renderLeagueDates(context, tournament)
        CommonUtil.renderLabelField(context, leagueDates, binding!!.leagueDates, R.string.league_dates_label)

        val finalDates = TournamentUtil.renderFinalDates(context, tournament)
        CommonUtil.renderLabelField(context, finalDates, binding!!.finalDates, R.string.final_dates_label)

        val relegationDates = TournamentUtil.renderRelegationDates(context, tournament)
        CommonUtil.renderLabelField(context, relegationDates, binding!!.relegationDates, R.string.relegation_dates_label)

        val teamCount = TournamentUtil.renderTeamCount(context, tournament)
        CommonUtil.renderLabelField(context, teamCount, binding!!.teamCount, R.string.tournament_team_count_label)

        val competitionTeamCount = TournamentUtil.renderCompetitionTeamCount(tournament)
        CommonUtil.renderLabelField(context, competitionTeamCount, binding!!.competitionTeamCount, R.string.competition_team_count_label)

        val competitionPlusTransferTeamCount = TournamentUtil.renderCompetitionPlusTransferTeamCount(context, tournament)
        CommonUtil.renderLabelField(context, competitionPlusTransferTeamCount, binding!!.competitionPlusTransferTeamCount, R.string.competition_team_count_label)

        val totalTeamCount = TournamentUtil.renderTotalTeamCount(context, tournament)
        CommonUtil.renderLabelField(context, totalTeamCount, binding!!.totalTeamCount, R.string.total_team_count_label)

        val totalPlusTransferTeamCount = TournamentUtil.renderTotalPlusTransferTeamCount(context, tournament)
        CommonUtil.renderLabelField(context, totalPlusTransferTeamCount, binding!!.totalPlusTransferTeamCount, R.string.total_team_count_label)

        val venueCount = TournamentUtil.renderVenueCount(context, tournament)
        CommonUtil.renderLabelField(context, venueCount, binding!!.venues, R.string.venue_count_label)

        CommonUtil.renderTeamList(context, tournament!!.tournamentFinalHost()!!, binding!!.finalHost,
            R.string.final_host_label, R.string.final_hosts_label)

        val finalTeamCount = TournamentUtil.renderFinalTeamCount(tournament)
        CommonUtil.renderLabelField(context, finalTeamCount, binding!!.finalTeamCount, R.string.final_team_count_label)

        val finalVenueCount = TournamentUtil.renderFinalVenueCount(context, tournament)
        CommonUtil.renderLabelField(context, finalVenueCount, binding!!.finalVenues, R.string.final_venue_count_label)

        val championsLabels = TournamentUtil.getChampionsLabel(tournament!!)
        CommonUtil.renderLabelTeam(context, tournament!!.tournamentChampionsTeam(), binding!!.champions, championsLabels[0])
        CommonUtil.renderLabelTeam(context, tournament!!.tournamentRunnersUpTeam(), binding!!.runnersUp, championsLabels[1])
        CommonUtil.renderTeamList(context, tournament!!.tournamentThirdPlaceTeam(), binding!!.thirdPlace, championsLabels[2], championsLabels[2])
        CommonUtil.renderLabelTeam(context, tournament!!.tournamentFourthPlaceTeam(), binding!!.fourthPlace, R.string.fourth_place_label)
        if (tournament!!.tournamentChampionsTeam() == null) {
            binding!!.fourthPlaceDivider.visibility = View.GONE
        }

        val matchesPlayed = if (tournament!!.tournamentTotalMatches() != null && tournament!!.tournamentTotalMatches()!! > 0) tournament!!.tournamentTotalMatches().toString() else ""
        CommonUtil.renderLabelField(context, matchesPlayed, binding!!.matchesPlayed, R.string.matches_played_label)
        if (matchesPlayed == "") {
            binding!!.finalAttendanceDivider.visibility = View.GONE
        }

        val goalsScored = TournamentUtil.renderGoalsScored(context, tournament)
        CommonUtil.renderLabelField(context, goalsScored, binding!!.goalsScored, R.string.goals_scored_label)

        val attendance = TournamentUtil.renderAttendance(context, tournament)
        CommonUtil.renderLabelField(context, attendance, binding!!.attendance, R.string.attendance_label)

        val finalMatchesPlayed = if (tournament!!.tournamentFinalMatches() != null) tournament!!.tournamentFinalMatches().toString() else ""
        CommonUtil.renderLabelField(context, finalMatchesPlayed, binding!!.finalMatchesPlayed, R.string.final_matches_played_label)

        val finalGoalsScored = TournamentUtil.renderFinalGoalsScored(context, tournament)
        CommonUtil.renderLabelField(context, finalGoalsScored, binding!!.finalGoalsScored, R.string.final_goals_scored_label)

        val finalAttendance = TournamentUtil.renderFinalAttendance(context, tournament)
        CommonUtil.renderLabelField(context, finalAttendance, binding!!.finalAttendance, R.string.final_attendance_label)

        val topScorerLabels = TournamentUtil.getTopScorerLabels(tournament!!)
        CommonUtil.renderPlayerList(context, tournament!!.tournamentGoldenBoot(), binding!!.goldenBoot, topScorerLabels[0], topScorerLabels[1])
        CommonUtil.renderPlayerList(context, tournament!!.tournamentSilverBoot(), binding!!.silverBoot, topScorerLabels[2], topScorerLabels[3])
        CommonUtil.renderPlayerList(context, tournament!!.tournamentBronzeBoot(), binding!!.bronzeBoot, topScorerLabels[4], topScorerLabels[5])
        if (tournament!!.tournamentGoldenBoot()?.isEmpty() == true) {
            binding!!.bronzeBootDivider.visibility = View.GONE
        }

        CommonUtil.renderPlayerList(context, tournament!!.tournamentFinalTopScorer(), binding!!.finalTopScorer,
            R.string.final_top_scorer_label, R.string.final_top_scorers_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentFinalTopScorer(), binding!!.finalTopScorerDivider)

        CommonUtil.renderGoldenBall(context, tournament!!.tournamentGoldenBall(), TournamentUtil.getGoldenBallLabel(tournament!!),
            binding!!.goldenBall, binding!!.silverBall, binding!!.bronzeBall)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentGoldenBall(), binding!!.goldenBallDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentFinalBestPlayer(), binding!!.finalBestPlayer,
            R.string.final_best_player_label, R.string.final_best_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentFinalBestPlayer(), binding!!.finalBestPlayerDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentBestYoungPlayer(), binding!!.bestYoungPlayer,
            R.string.best_young_player_label, R.string.best_young_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentBestYoungPlayer(), binding!!.bestYoungPlayerDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentFinalBestYoungPlayer(), binding!!.finalBestYoungPlayer,
            R.string.final_best_young_player_label, R.string.final_best_young_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentFinalBestYoungPlayer(), binding!!.finalBestYoungPlayerDivider)

        val goldenGloveLabels = TournamentUtil.getGoldenGloveLabel(tournament!!)
        CommonUtil.renderPlayerList(context, tournament!!.tournamentGoldenGlove(), binding!!.goldenGlove,
            goldenGloveLabels[0], goldenGloveLabels[1])
        CommonUtil.renderPlayerDivider(tournament!!.tournamentGoldenGlove(), binding!!.goldenGloveDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentBestForward(), binding!!.bestForward,
            R.string.best_forward_label, R.string.best_forwards_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentBestForward(), binding!!.bestForwardDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentMidfielder(), binding!!.bestMidfielder,
            R.string.best_midfielder_label, R.string.best_midfielders_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentMidfielder(), binding!!.bestMidfielderDivider)

        CommonUtil.renderPlayerList(context, tournament!!.tournamentBestDefender(), binding!!.bestDefender,
            R.string.best_defender_label, R.string.best_defenders_label)
        CommonUtil.renderPlayerDivider(tournament!!.tournamentBestDefender(), binding!!.bestDefenderDivider)

        CommonUtil.renderTeamList(context, tournament!!.tournamentFairPlayTeam(), binding!!.fairPlay,
            R.string.fair_play_label, R.string.fair_play_label)
    }

    private fun getCampaignLinks(): List<Campaign?> {
        return tournament!!.campaigns!!.filter { it!!.id != tournament!!.currentCampaign!!.id }
    }

    override fun onItemClick(view: View?, campaignList: List<Campaign?>, position: Int) {

        TournamentUtil.browseToCampaign(context as MainActivity, tournament, campaignList[position]!!)
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
