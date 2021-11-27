package com.mmtran.turtlesoccer.utils

import android.content.Context
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.*
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

object TournamentUtil {

    fun processTournament(tournamentList: List<Tournament?>?) {

        if (tournamentList.isNullOrEmpty()) return

        var previousTournament: Tournament? = tournamentList[0]

        for (i in tournamentList.indices) {
            if (tournamentList[i]!!.noThirdPlace != null) {
                tournamentList[i]!!.thirdPlaceDetermined = ThirdPlaceDetermined.HAS_SEMI_FINALISTS
            }
            if (i == 0) {
                tournamentList[i]!!.compTourResultSectionHeader = if (tournamentList[i]!!.thirdPlaceDetermined == ThirdPlaceDetermined.HAS_THIRD_PLACE)
                    SectionHeader.THIRD_PLACE_HEADER else SectionHeader.SEMIFINALISTS_HEADER
            } else {
                if (tournamentList[i]!!.thirdPlaceDetermined != previousTournament!!.thirdPlaceDetermined || tournamentList[i]!!.era != null) {
                    tournamentList[i]!!.compTourResultSectionHeader = if (tournamentList[i]!!.thirdPlaceDetermined == ThirdPlaceDetermined.HAS_THIRD_PLACE)
                        SectionHeader.THIRD_PLACE_HEADER else SectionHeader.SEMIFINALISTS_HEADER
                }
                if (!previousTournament.compTourResultEvenRow) tournamentList[i]!!.compTourResultEvenRow = true
            }
            previousTournament = tournamentList[i]!!
        }
    }

    fun processTeams(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null) return

        processHosts(tournament, nationList, teamList)
        processFinalStandings(tournament, nationList, teamList)
        processAwards(tournament, nationList, teamList)
    }

    private fun processHosts(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null) return

        if (tournament.details != null) {
            val hostList = tournament.details!!.host
            val finalHostList = tournament.details!!.finalHost

            tournament.details!!.hostTeam = emptyList()
            for (hostId: String? in hostList!!) {
                val team = TeamUtil.getTeam(hostId, nationList, teamList)
                if (team != null) {
                    tournament.details!!.hostTeam = tournament.details!!.hostTeam?.plus(team)
                }
            }
            tournament.details!!.finalHostTeam = emptyList()
            for (hostId: String? in finalHostList!!) {
                val team = TeamUtil.getTeam(hostId, nationList, teamList)
                if (team != null) {
                    tournament.details!!.finalHostTeam = tournament.details!!.finalHostTeam?.plus(team)
                }
            }
        }

    }

    fun processFinalStandings(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null) return

        if (tournament.finalStandings != null) {
            val championId = tournament.finalStandings!!.champions
            val runnersUpId = tournament.finalStandings!!.runnersUp
            val thirdPlaceList = tournament.finalStandings!!.thirdPlace
            val fourthPlaceId = tournament.finalStandings!!.fourthPlace
            val semiFinalist1Id = tournament.finalStandings!!.semiFinalist1
            val semiFinalist2Id = tournament.finalStandings!!.semiFinalist2

            val team1 = TeamUtil.getTeam(championId, nationList, teamList)
            if (team1 != null) {
                tournament.finalStandings!!.championTeam = team1
            }
            val team2 = TeamUtil.getTeam(runnersUpId, nationList, teamList)
            if (team2 != null) {
                tournament.finalStandings!!.runnersUpTeam = team2
            }
            tournament.finalStandings!!.thirdPlaceTeam = emptyList()
            for (thirdPlaceId: String? in thirdPlaceList!!) {
                val team3 = TeamUtil.getTeam(thirdPlaceId, nationList, teamList)
                if (team3 != null) {
                    tournament.finalStandings!!.thirdPlaceTeam = tournament.finalStandings!!.thirdPlaceTeam?.plus(team3)
                }
            }
            val team4 = TeamUtil.getTeam(fourthPlaceId, nationList, teamList)
            if (team4 != null) {
                tournament.finalStandings!!.fourthPlaceTeam = team4
            }
            val team5 = TeamUtil.getTeam(semiFinalist1Id, nationList, teamList)
            if (team5 != null) {
                tournament.finalStandings!!.semiFinalist1Team = team5
            }
            val team6 = TeamUtil.getTeam(semiFinalist2Id, nationList, teamList)
            if (team6 != null) {
                tournament.finalStandings!!.semiFinalist2Team = team6
            }
        }
    }

    private fun processAwards(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null) return

        if (tournament.awards != null) {

            for (player: Player? in tournament.awards!!.goldenBoot!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.silverBoot!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.bronzeBoot!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.finalTopScorer!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.goldenBall!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.finalBestPlayer!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.bestYoungPlayer!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.finalBestYoungPlayer!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.goldenGlove!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.bestForward!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.bestMidfielder!!) {
                processPlayer(player, nationList, teamList)
            }

            for (player: Player? in tournament.awards!!.bestDefender!!) {
                processPlayer(player, nationList, teamList)
            }

            tournament.awards!!.fairPlayTeam = emptyList()
            for (fairPlayId: String? in tournament.awards!!.fairPlay!!) {
                val team = TeamUtil.getTeam(fairPlayId, nationList, teamList)
                if (team != null) {
                    tournament.awards!!.fairPlayTeam = tournament.awards!!.fairPlayTeam?.plus(team)
                }
            }
        }
    }

    private fun processPlayer(player: Player?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (player == null || player.team.isNullOrEmpty()) return

        var team = TeamUtil.getTeam(player.team, nationList, teamList)
        if (team != null) {
            player.teamT = team
        }

        if (!player.club.isNullOrEmpty()) {
            team = TeamUtil.getTeam(player.club, nationList, teamList)
            if (team != null) {
                player.clubT = team
            }
        }

        if (!player.club2.isNullOrEmpty()) {
            team = TeamUtil.getTeam(player.club2, nationList, teamList)
            if (team != null) {
                player.club2T = team
            }
        }
    }

    fun processFinalStandings(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.tournamentList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (tournament: Tournament? in competition.tournamentList!!) {
            processFinalStandings(tournament, nationList, teamList)
        }
    }

    fun attachCompetition(tournamentList: List<Tournament?>?, competition: Competition?) {

        if (tournamentList.isNullOrEmpty()) return

        for (tournament: Tournament? in tournamentList) {
            tournament!!.competition = competition
        }
    }

    fun renderDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startDate, tournament.details!!.endDate)
    }

    fun renderQualifyingDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startQualifyingDate, tournament.details!!.endQualifyingDate)
    }

    fun renderCompetitionDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startCompetitionDate, tournament.details!!.endCompetitionDate)
    }

    fun renderQualifyingCompetitionDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startQualifyingDate, tournament.details!!.endCompetitionDate)
    }

    fun renderLeagueDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startLeagueDate, tournament.details!!.endLeagueDate)
    }

    fun renderFinalDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startFinalDate, tournament.details!!.endFinalDate)
    }

    fun renderLeagueFinalDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startLeagueDate, tournament.details!!.endFinalDate)
    }

    fun renderRelegationDates(context: Context?, tournament: Tournament?): String {
        if (tournament?.details == null) return ""
        return renderStartEndDates(context, tournament.details!!.startRelegationDate, tournament.details!!.endRelegationDate)
    }

    private fun renderStartEndDates(context: Context?, startDate: String?, endDate: String?): String {

        if (startDate.isNullOrEmpty() || endDate.isNullOrEmpty()) return ""

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE)
        val formattedStartDate = start.format(formatter)
        val end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE)
        val formattedEndDate = end.format(formatter)

        return context!!.getString(R.string.tournament_dates, formattedStartDate, formattedEndDate)
    }

    fun renderTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.teamCount == null) return ""

        return if (tournament.details!!.confedCount != null) {
            context!!.getString(
                R.string.team_count_with_confed,
                tournament.details!!.teamCount.toString(),
                tournament.details!!.confedCount.toString()
            )
        } else tournament.details!!.teamCount.toString()
    }

    fun renderCompetitionTeamCount(tournament: Tournament?): String {

        if (tournament!!.details!!.competitionTeamCount == null) return ""

        if (tournament.details!!.competitionTeamCount != null && tournament.details!!.transferTeamCount != null) return ""

        return tournament.details!!.competitionTeamCount.toString()
    }

    fun renderCompetitionPlusTransferTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.competitionTeamCount == null || tournament.details!!.transferTeamCount == null) return ""

        return context!!.getString(R.string.total_plus_transfer_team_count, tournament.details!!.competitionTeamCount.toString(), tournament.details!!.transferTeamCount.toString())
    }

    fun renderTotalTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.totalTeamCount == null) return ""

        if (tournament.details!!.totalTeamCount != null && tournament.details!!.totalTransferTeamCount != null) return ""

        return if (tournament.details!!.nationCount != null) {
            context!!.getString(
                R.string.total_team_count_with_association,
                tournament.details!!.totalTeamCount.toString(),
                tournament.details!!.nationCount.toString()
            )
        } else tournament.details!!.totalTeamCount.toString()
    }

    fun renderTotalPlusTransferTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.totalTeamCount == null || tournament.details!!.totalTransferTeamCount == null) return ""

        return if (tournament.details!!.nationCount != null) {
            context!!.getString(
                R.string.total_plus_transfer_team_count_with_association,
                tournament.details!!.totalTeamCount.toString(),
                tournament.details!!.totalTransferTeamCount.toString(),
                tournament.details!!.nationCount.toString()
            )
        } else context!!.getString(
            R.string.total_plus_transfer_team_count,
            tournament.details!!.totalTeamCount.toString(),
            tournament.details!!.totalTransferTeamCount.toString()
        )
    }

    fun renderFinalTeamCount(tournament: Tournament?): String {

        if (tournament!!.details!!.finalTeamCount == null) return ""

        return tournament.details!!.finalTeamCount.toString()
    }

    fun renderVenueCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.venueCount == null) return ""

        return if (tournament.details!!.cityCount != null) {
            context!!.getString(
                R.string.venue_count_with_cities,
                tournament.details!!.venueCount.toString(),
                tournament.details!!.cityCount.toString()
            )
        } else tournament.details!!.venueCount.toString()
    }

    fun renderFinalVenueCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.finalVenueCount == null) return ""

        return if (tournament.details!!.finalCityCount != null) {
            context!!.getString(
                R.string.venue_count_with_cities,
                tournament.details!!.finalVenueCount.toString(),
                tournament.details!!.finalCityCount.toString()
            )
        } else tournament.details!!.finalVenueCount.toString()
    }

    fun renderGoalsScored(context: Context?, tournament: Tournament?): String {

        if (tournament!!.statistics!!.totalGoals == null) return ""

        return if (tournament.statistics!!.totalMatches != null && tournament.statistics!!.totalMatches != 0) {
            context!!.getString(
                R.string.goals_scored_per_match,
                tournament.statistics!!.totalGoals.toString(),
                getGoalsPerMatch(tournament.statistics!!.totalGoals!!, tournament.statistics!!.totalMatches!!)
            )
        } else tournament.statistics!!.totalGoals.toString()
    }

    fun renderFinalGoalsScored(context: Context?, tournament: Tournament?): String {

        if (tournament!!.statistics!!.finalGoals == null) return ""

        return if (tournament.statistics!!.finalMatches != null && tournament.statistics!!.finalMatches != 0) {
            context!!.getString(
                R.string.goals_scored_per_match,
                tournament.statistics!!.finalGoals.toString(),
                getGoalsPerMatch(tournament.statistics!!.finalGoals!!, tournament.statistics!!.finalMatches!!)
            )
        } else tournament.statistics!!.totalGoals.toString()
    }

    private fun getGoalsPerMatch(goals: Int?, matches: Int?) : String {
        val goalsPerMatch: Double = goals!!.toDouble() / matches!! * 100
        val goalsPerMatchRounded = goalsPerMatch.roundToInt()
        return (goalsPerMatchRounded.toDouble() / 100).toString()
    }

    fun renderAttendance(context: Context?, tournament: Tournament?): String {

        if (tournament!!.statistics!!.attendance == null) return ""

        return if (tournament.statistics!!.totalMatches != null && tournament.statistics!!.totalMatches != 0) {
            val nf = NumberFormat.getInstance()
            context!!.getString(
                R.string.attendance_per_match,
                nf.format(tournament.statistics!!.attendance),
                getAttendancePerMatch(tournament.statistics!!.attendance!!, tournament.statistics!!.totalMatches!!)
            )
        } else tournament.statistics!!.attendance.toString()
    }

    fun renderFinalAttendance(context: Context?, tournament: Tournament?): String {

        if (tournament!!.statistics!!.finalAttendance == null) return ""

        return if (tournament.statistics!!.finalMatches != null && tournament.statistics!!.finalMatches != 0) {
            val nf = NumberFormat.getInstance()
            context!!.getString(
                R.string.attendance_per_match,
                nf.format(tournament.statistics!!.finalAttendance),
                getAttendancePerMatch(tournament.statistics!!.finalAttendance!!, tournament.statistics!!.finalMatches!!)
            )
        } else tournament.statistics!!.finalAttendance.toString()
    }

    private fun getAttendancePerMatch(attendance: Int?, matches: Int?) : String {
        val nf = NumberFormat.getInstance()
        return nf.format((attendance!!.toDouble() / matches!!).roundToInt())
    }
}
