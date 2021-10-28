package com.mmtran.turtlesoccer.utils

import com.mmtran.turtlesoccer.models.*

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

    fun processFinalStandings(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.tournamentList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (tournament: Tournament? in competition.tournamentList!!) {
            val championId = tournament!!.finalStandings!!.champions
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

    fun attachCompetition(tournamentList: List<Tournament?>?, competition: Competition?) {

        if (tournamentList.isNullOrEmpty()) return

        for (tournament: Tournament? in tournamentList) {
            tournament!!.competition = competition
        }
    }
}
