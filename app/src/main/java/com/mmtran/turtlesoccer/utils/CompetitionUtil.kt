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

        competition.tournamentList = tournamentList.filter { it!!.competition?.id == competition.id }
        attachCompetition(competition, competition.tournamentList)
        TournamentUtil.attachCampaigns(competition.tournamentList!!, campaignList)

        processFinalStandings(competition, nationList, teamList)

        competition.tournamentList = competition.tournamentList!!.reversed()
        processTournamentList(competition.tournamentList!!)

        processCompetitionRankings(competition, competition.tournamentList!!, nationList, teamList)
    }

    private fun getChampion(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        var championId: String? = null
        var titleCount: Int? = null
        if (competition!!.currentChampions !== null) {
            championId = competition!!.currentChampions!!.id
            titleCount = competition.currentChampions!!.titleCount
        } else if (competition!!.lastChampions !== null) {
            championId = competition!!.lastChampions!!.id
            titleCount = competition.lastChampions!!.titleCount
        }
        if (championId.isNullOrEmpty()) return

        val team = TeamUtil.getTeam(championId, nationList, teamList)?.copy()
        team?.titleCount = titleCount
        if (competition!!.currentChampions !== null) {
            competition!!.currentChampions = team
        } else {
            competition!!.lastChampions = team
        }
    }

    private fun getMostSuccessfulTeams(competition: Competition?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition!!.mostSuccessfulTeams.isNullOrEmpty() || nationList.isNullOrEmpty() || teamList.isNullOrEmpty()) return

        var tl: List<Team?> = emptyList()
        for (champion: Team? in competition.mostSuccessfulTeams!!) {
            val team = TeamUtil.getTeam(champion!!.id, nationList, teamList)
            var team2: Team
            if (team != null) {
                team2 = team.copy()
                team2.titleCount = champion.titleCount
                tl = tl.plus(team2)
            }
        }
        competition.mostSuccessfulTeams = tl
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

    private fun processCompetitionRankings(competition: Competition?, tournamentList: List<Tournament?>?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (competition == null || tournamentList.isNullOrEmpty()) return

        var rankingList: List<Ranking?>? = emptyList()
        for (tournament: Tournament? in tournamentList) {

            if (tournament == null) continue
            val campaign = tournament.getFinalTournament() ?: continue
            if (campaign.multipleLeagues!! && campaign.leagues != null) {
                for (league: League? in campaign.leagues!!) {
                    if (league?.stages == null) break
                    for (stage: Stage? in league.stages!!) {
                        if (stage?.groups == null) break
                        for (group: Group? in stage.groups!!) {
                            if (group?.matchdays == null) break
                            for (matchday: Matchday? in group.matchdays!!) {
                                if (matchday?.matches == null) break
                                rankingList = accumulateMatchesRankings(tournament, rankingList, matchday.matches, nationList, teamList)
                            }
                        }
                    }
                }
            }
            if (!campaign.multipleLeagues!! && campaign.stages != null) {
                for (stage: Stage? in campaign.stages!!) {
                    if (stage == null) break
                    if (stage.isRoundRobin()) {
                        if (stage.groups == null) break
                        for (group: Group? in stage.groups!!) {
                            if (!stage.multipleMatchdays!!) {
                                if (group?.matches == null) break
                                rankingList = accumulateMatchesRankings(tournament, rankingList, group.matches, nationList, teamList)
                            } else {
                                if (group?.matchdays == null) break
                                for (matchday: Matchday? in group.matchdays!!) {
                                    if (matchday?.matches == null) break
                                    rankingList = accumulateMatchesRankings(tournament, rankingList, matchday.matches, nationList, teamList)
                                }
                            }
                        }
                    }
                    if (stage.isKnockout()) {
                        if (stage.rounds == null) break
                        for (round: Round? in stage.rounds!!) {
                            if (round?.matches == null) break
                            rankingList = accumulateMatchesRankings(tournament, rankingList, round.matches, nationList, teamList)
                        }
                    }
                }
            }
        }
        competition.pools = RankingUtil.sortRankings(rankingList, 1)
    }

    private fun accumulateMatchesRankings(tournament: Tournament?, rankings: List<Ranking?>?,
                                          matches: List<Match?>?, nationList: List<Nation?>?, teamList: List<Team?>?): List<Ranking?>? {

        if (tournament == null || matches.isNullOrEmpty()) return rankings

        var rankingList = rankings
        for (match: Match? in matches) {
            if (match == null || !match.isMatchValidScore()) continue
            MatchUtil.processMatch(match, nationList, teamList)
            val homeTeam = if (match.homeTeam!!.parentTeam == null) match.homeTeam else match.homeTeam!!.parentTeam
            val awayTeam = if (match.awayTeam!!.parentTeam == null) match.awayTeam else match.awayTeam!!.parentTeam
            var homeRanking = rankingList!!.find { it!!.team!!.name.equals(homeTeam!!.name) }
            var awayRanking = rankingList.find { it!!.team!!.name.equals(awayTeam!!.name) }
            if (homeRanking == null) {
                homeRanking = RankingUtil.newRanking(homeTeam)
                rankingList = rankingList.plus(homeRanking)
            }
            if (awayRanking == null) {
                awayRanking = RankingUtil.newRanking(awayTeam)
                rankingList = rankingList.plus(awayRanking)
            }
            RankingUtil.accumulateRankings(tournament, 3, homeRanking, awayRanking, match)
        }
        return rankingList
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
