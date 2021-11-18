package com.mmtran.turtlesoccer.utils

import android.content.Context
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        if (tournament!!.details!!.startDate.isNullOrEmpty() || tournament.details!!.endDate.isNullOrEmpty()) return ""

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val startDate = LocalDate.parse(tournament.details!!.startDate, DateTimeFormatter.ISO_DATE)
        val formattedStartDate = startDate.format(formatter)
        val endDate = LocalDate.parse(tournament.details!!.endDate, DateTimeFormatter.ISO_DATE)
        val formattedEndDate = endDate.format(formatter)

        return context!!.getString(R.string.tournament_dates, formattedStartDate, formattedEndDate)
    }

    fun renderQualifyingDates(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.startQualifyingDate.isNullOrEmpty() || tournament.details!!.endQualifyingDate.isNullOrEmpty()) return ""

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val startQualifyingDate = LocalDate.parse(tournament.details!!.startQualifyingDate, DateTimeFormatter.ISO_DATE)
        val formattedQualifyingStartDate = startQualifyingDate.format(formatter)
        val endQualifyingDate = LocalDate.parse(tournament.details!!.endQualifyingDate, DateTimeFormatter.ISO_DATE)
        val formattedQualifyingEndDate = endQualifyingDate.format(formatter)

        return context!!.getString(R.string.tournament_dates, formattedQualifyingEndDate, formattedQualifyingEndDate)
    }

    fun renderQualifyingCompetitionDates(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.startQualifyingDate.isNullOrEmpty() || tournament.details!!.endCompetitionDate.isNullOrEmpty()) return ""

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val startQualifyingDate = LocalDate.parse(tournament.details!!.startQualifyingDate, DateTimeFormatter.ISO_DATE)
        val formattedQualifyingStartDate = startQualifyingDate.format(formatter)
        val endCompetitionDate = LocalDate.parse(tournament.details!!.endCompetitionDate, DateTimeFormatter.ISO_DATE)
        val formattedCompetitionEndDate = endCompetitionDate.format(formatter)

        return context!!.getString(R.string.tournament_dates, formattedQualifyingStartDate, formattedCompetitionEndDate)
    }

    fun renderLeagueFinalDates(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.startLeagueDate.isNullOrEmpty() || tournament.details!!.endFinalDate.isNullOrEmpty()) return ""

        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        val startLeagueDate = LocalDate.parse(tournament.details!!.startLeagueDate, DateTimeFormatter.ISO_DATE)
        val formattedLeagueStartDate = startLeagueDate.format(formatter)
        val endFinalDate = LocalDate.parse(tournament.details!!.endFinalDate, DateTimeFormatter.ISO_DATE)
        val formattedFinalEndDate = endFinalDate.format(formatter)

        return context!!.getString(R.string.tournament_dates, formattedLeagueStartDate, formattedFinalEndDate)
    }

    fun renderTotalPlusTransferTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.details!!.totalTeamCount == null || tournament.details!!.totalTransferTeamCount == null) return ""

        return context!!.getString(R.string.total_plus_transfer_team_count, tournament!!.details!!.totalTeamCount.toString(), tournament.details!!.totalTransferTeamCount.toString())
    }
}
