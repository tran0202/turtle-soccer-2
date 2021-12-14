package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.databinding.FragmentTourAboutBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.TourAboutViewModel
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil
import com.mmtran.turtlesoccer.utils.TournamentUtil

class TourAboutFragment(tour: Tournament?) : Fragment() {

    private var tourAboutViewModel: TourAboutViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourAboutBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

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

        if (tournament!!.name!!.isNotEmpty()) {
            binding!!.tournamentName.text = tournament!!.name!!
        }

        if (tournament!!.previousTournament != null && tournament!!.previousTournament!!.year!!.isNotEmpty()) {
            binding!!.previous.visibility = View.VISIBLE
            binding!!.leftArrow.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding!!.leftArrow.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToTournament(context as MainActivity, tournament!!.previousTournament)
            })
            binding!!.previousTournament.text = tournament!!.previousTournament!!.year
            binding!!.previousTournament.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToTournament(context as MainActivity, tournament!!.previousTournament)
            })
        } else {
            binding!!.previous.visibility = View.GONE
        }

        if (tournament!!.competition!!.name!!.isNotEmpty()) {
            binding!!.competition.text = tournament!!.competition!!.name!!
        } else {
            binding!!.competition.visibility = View.GONE
        }
        binding!!.competition.setOnClickListener(View.OnClickListener {
            CompetitionUtil.browseToCompetition(context as MainActivity, tournament!!.competition)
        })

        if (tournament!!.nextTournament != null && !tournament!!.nextTournament!!.year.isNullOrEmpty()) {
            binding!!.next.visibility = View.VISIBLE
            binding!!.nextTournament.text = tournament!!.nextTournament!!.year
            binding!!.nextTournament.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToTournament(context as MainActivity, tournament!!.nextTournament)
            })
            binding!!.rightArrow.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding!!.rightArrow.setOnClickListener(View.OnClickListener {
                TournamentUtil.browseToTournament(context as MainActivity, tournament!!.nextTournament)
            })
        } else {
            binding!!.next.visibility = View.GONE
        }

        if (tournament!!.heroImages != null && tournament!!.heroImages!!.isNotEmpty()) {
            firebaseStorageLoader!!.loadImage(
                activity,
                binding!!.heroImage,
                "tournaments/" + tournament!!.id + "/" + tournament!!.heroImages!![0]!!.filename
            )
        }

        CommonUtil.renderLabelField(context, tournament!!.originalName, binding!!.originalName, R.string.original_name_label)

        CommonUtil.renderTeamList(context, tournament!!.details!!.hostTeam!!, binding!!.host,
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

        CommonUtil.renderTeamList(context, tournament!!.details!!.finalHostTeam!!, binding!!.finalHost,
            R.string.final_host_label, R.string.final_hosts_label)

        val finalTeamCount = TournamentUtil.renderFinalTeamCount(tournament)
        CommonUtil.renderLabelField(context, finalTeamCount, binding!!.finalTeamCount, R.string.final_team_count_label)

        val finalVenueCount = TournamentUtil.renderFinalVenueCount(context, tournament)
        CommonUtil.renderLabelField(context, finalVenueCount, binding!!.finalVenues, R.string.final_venue_count_label)

        val championsLabels = TournamentUtil.getChampionsLabel(tournament!!)
        CommonUtil.renderLabelTeam(context, TournamentUtil.getChampionsTeam(tournament), binding!!.champions, championsLabels[0])
        CommonUtil.renderLabelTeam(context, TournamentUtil.getRunnersUpTeam(tournament), binding!!.runnersUp, championsLabels[1])
        CommonUtil.renderTeamList(context, TournamentUtil.getThirdPlaceTeam(tournament), binding!!.thirdPlace, championsLabels[2], championsLabels[2])
        CommonUtil.renderLabelTeam(context, TournamentUtil.getFourthPlaceTeam(tournament), binding!!.fourthPlace, R.string.fourth_place_label)
        if (TournamentUtil.getChampionsTeam(tournament) == null) {
            binding!!.fourthPlaceDivider.visibility = View.GONE
        }

        val matchesPlayed = if (tournament!!.statistics!!.totalMatches != null && tournament!!.statistics!!.totalMatches!! > 0) tournament!!.statistics!!.totalMatches.toString() else ""
        CommonUtil.renderLabelField(context, matchesPlayed, binding!!.matchesPlayed, R.string.matches_played_label)
        if (matchesPlayed == "") {
            binding!!.finalAttendanceDivider.visibility = View.GONE
        }

        val goalsScored = TournamentUtil.renderGoalsScored(context, tournament)
        CommonUtil.renderLabelField(context, goalsScored, binding!!.goalsScored, R.string.goals_scored_label)

        val attendance = TournamentUtil.renderAttendance(context, tournament)
        CommonUtil.renderLabelField(context, attendance, binding!!.attendance, R.string.attendance_label)

        val finalMatchesPlayed = if (tournament!!.statistics!!.finalMatches != null) tournament!!.statistics!!.finalMatches.toString() else ""
        CommonUtil.renderLabelField(context, finalMatchesPlayed, binding!!.finalMatchesPlayed, R.string.final_matches_played_label)

        val finalGoalsScored = TournamentUtil.renderFinalGoalsScored(context, tournament)
        CommonUtil.renderLabelField(context, finalGoalsScored, binding!!.finalGoalsScored, R.string.final_goals_scored_label)

        val finalAttendance = TournamentUtil.renderFinalAttendance(context, tournament)
        CommonUtil.renderLabelField(context, finalAttendance, binding!!.finalAttendance, R.string.final_attendance_label)

        val topScorerLabels = TournamentUtil.getTopScorerLabels(tournament!!)
        CommonUtil.renderPlayerList(context, tournament!!.awards!!.goldenBoot!!, binding!!.goldenBoot, topScorerLabels[0], topScorerLabels[1])
        CommonUtil.renderPlayerList(context, tournament!!.awards!!.silverBoot!!, binding!!.silverBoot, topScorerLabels[2], topScorerLabels[3])
        CommonUtil.renderPlayerList(context, tournament!!.awards!!.bronzeBoot!!, binding!!.bronzeBoot, topScorerLabels[4], topScorerLabels[5])
        if (tournament!!.awards!!.goldenBoot!!.isEmpty()) {
            binding!!.bronzeBootDivider.visibility = View.GONE
        }

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.finalTopScorer!!, binding!!.finalTopScorer,
            R.string.final_top_scorer_label, R.string.final_top_scorers_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.finalTopScorer!!, binding!!.finalTopScorerDivider)

        CommonUtil.renderGoldenBall(context, tournament!!.awards!!.goldenBall!!, TournamentUtil.getGoldenBallLabel(tournament!!),
            binding!!.goldenBall, binding!!.silverBall, binding!!.bronzeBall)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.goldenBall!!, binding!!.goldenBallDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.finalBestPlayer!!, binding!!.finalBestPlayer,
            R.string.final_best_player_label, R.string.final_best_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.finalBestPlayer!!, binding!!.finalBestPlayerDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.bestYoungPlayer!!, binding!!.bestYoungPlayer,
            R.string.best_young_player_label, R.string.best_young_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.bestYoungPlayer!!, binding!!.bestYoungPlayerDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.finalBestYoungPlayer!!, binding!!.finalBestYoungPlayer,
            R.string.final_best_young_player_label, R.string.final_best_young_players_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.finalBestYoungPlayer!!, binding!!.finalBestYoungPlayerDivider)

        val goldenGloveLabels = TournamentUtil.getGoldenGloveLabel(tournament!!)
        CommonUtil.renderPlayerList(context, tournament!!.awards!!.goldenGlove!!, binding!!.goldenGlove,
            goldenGloveLabels[0], goldenGloveLabels[1])
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.goldenGlove!!, binding!!.goldenGloveDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.bestForward!!, binding!!.bestForward,
            R.string.best_forward_label, R.string.best_forwards_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.bestForward!!, binding!!.bestForwardDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.bestMidfielder!!, binding!!.bestMidfielder,
            R.string.best_midfielder_label, R.string.best_midfielders_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.bestMidfielder!!, binding!!.bestMidfielderDivider)

        CommonUtil.renderPlayerList(context, tournament!!.awards!!.bestDefender!!, binding!!.bestDefender,
            R.string.best_defender_label, R.string.best_defenders_label)
        CommonUtil.renderPlayerDivider(tournament!!.awards!!.bestDefender!!, binding!!.bestDefenderDivider)

        CommonUtil.renderTeamList(context, tournament!!.awards!!.fairPlayTeam!!, binding!!.fairPlay,
            R.string.fair_play_label, R.string.fair_play_label)
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
