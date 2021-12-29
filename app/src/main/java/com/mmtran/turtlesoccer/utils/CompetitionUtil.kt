package com.mmtran.turtlesoccer.utils

import android.app.Activity
import android.os.Bundle
import androidx.navigation.Navigation
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.EXTRA_COMPETITION
import com.mmtran.turtlesoccer.models.*

object CompetitionUtil {

    fun processCompetition(competition: Competition?, tournamentList: List<Tournament?>?, campaignList: List<Campaign?>?,
                           nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition == null || tournamentList.isNullOrEmpty() || campaignList.isNullOrEmpty()
            || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        getChampion(competition, nationList, teamList)
        getMostSuccessfulTeams(competition, nationList, teamList)

        competition.tournamentList = tournamentList.filter { it!!.competitionId == competition.id }
        attachCompetition(competition, competition.tournamentList)
        TournamentUtil.attachCampaigns(competition.tournamentList!!, campaignList)

        processFinalStandings(competition, nationList, teamList)

        competition.tournamentList = competition.tournamentList!!.reversed()
        processTournamentList(competition.tournamentList!!)
    }

    private fun getMostSuccessfulTeams(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.mostSuccessfulTeams.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (champion: Champion? in competition.mostSuccessfulTeams!!) {

            val team = TeamUtil.getTeam(champion!!.teamId, nationList, teamList)
            if (team != null) {
                champion.team = team
            }
        }
    }

    private fun getChampion(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        var championId: String? = null
        if (competition!!.currentChampions !== null) {
            championId = competition!!.currentChampions!!.teamId
        } else if (competition!!.lastChampions !== null) {
            championId = competition!!.lastChampions!!.teamId
        }
        if (championId.isNullOrEmpty()) return

        val team = TeamUtil.getTeam(championId, nationList, teamList)
        if (team!= null) {
            if (competition!!.currentChampions !== null) {
                competition!!.currentChampions!!.team = team
            } else {
                competition!!.lastChampions!!.team = team
            }
        }
    }

    private fun attachCompetition(competition: Competition?, tournamentList: List<Tournament?>?) {

        if (tournamentList.isNullOrEmpty()) return

        for (tournament: Tournament? in tournamentList) {
            tournament!!.competition = competition
        }
    }

    private fun processFinalStandings(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.tournamentList.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        for (tournament: Tournament? in competition.tournamentList!!) {
            TournamentUtil.processFinalStandings(tournament, nationList, teamList)
        }
    }

    private fun processTournamentList(tournamentList: List<Tournament?>?) {

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

    fun renderTeamCount(competition: Competition?): String {

        if (competition!!.teamCount == null) return ""

        return competition.teamCount.toString()
    }

    fun browseToCompetition(context: Activity, competition: Competition?) {
        val args = Bundle()
        args.putSerializable(EXTRA_COMPETITION, competition)
        val navController = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_competitions, args)
    }
}
