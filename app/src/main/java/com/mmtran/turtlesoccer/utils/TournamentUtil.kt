package com.mmtran.turtlesoccer.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.navigation.Navigation
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.EXTRA_CAMPAIGN
import com.mmtran.turtlesoccer.adapters.EXTRA_TOURNAMENT
import com.mmtran.turtlesoccer.models.*
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

object TournamentUtil {

    fun processTournament(tournament: Tournament?, tournamentList: List<Tournament?>?, campaignList: List<Campaign?>?,
                          nationList: List<Nation?>?, teamList: List<Team?>?, competitionList: List<Competition?>?) {

        if (tournament == null || tournamentList.isNullOrEmpty() || campaignList.isNullOrEmpty()
            || nationList.isNullOrEmpty() || teamList.isNullOrEmpty() || competitionList.isNullOrEmpty()) return

        attachCompetition(tournament, competitionList)
        attachCampaigns(tournamentList, campaignList)
        processPreviousNextTournaments(tournament, tournamentList)
        processHosts(tournament, nationList, teamList)
        processFinalStandings(tournament, nationList, teamList)
        processAwards(tournament, nationList, teamList)
        processStages(tournament, nationList, teamList)
    }

    private fun attachCompetition(tournament: Tournament?, competitionList: List<Competition?>?) {

        if (tournament == null || competitionList.isNullOrEmpty()) return

        tournament.competition = competitionList.find { it!!.id.equals(tournament.competition?.id) }
    }

    fun attachCampaigns(tournamentList: List<Tournament?>?, campaignList: List<Campaign?>?) {

        if (tournamentList.isNullOrEmpty() || campaignList.isNullOrEmpty()) return

        for (tournament: Tournament? in tournamentList) {
            processCampaigns(tournament, campaignList)
        }
    }

    private fun processCampaigns(tournament: Tournament?, campaignList: List<Campaign?>?) {

        if (tournament == null || campaignList.isNullOrEmpty()) return

        var cList: List<Campaign?>? = emptyList()
        for (campaign: Campaign? in campaignList) {
            if (campaign!!.tournamentId == tournament.id) {
                cList = cList!!.plus(campaign)
            }
        }
        cList = cList!!.sortedBy { campaign -> campaign!!.order }
        tournament.campaigns = cList
    }

    private fun processPreviousNextTournaments(tournament: Tournament?, tournamentList: List<Tournament?>?) {

        if (tournament == null) return

        var tourList = tournamentList!!.filter { it!!.competition?.id == tournament.competition?.id }
        if (tourList.isNotEmpty()) {
            tourList = tourList.sortedBy { tour -> tour!!.details!!.startDate }
            val index = tourList.indexOf(tournament)
            tournament.previousTournament = if (index > 0) tourList[index - 1] else null
            tournament.nextTournament = if (index < tourList.size - 1) tourList[index + 1] else null
        }
    }

    private fun processHosts(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament?.details == null || tournament.currentCampaign?.details == null || tournament.campaigns.isNullOrEmpty()) return

        processHosts(tournament.details, nationList, teamList)
        for (campaign: Campaign? in tournament.campaigns!!) {
            if (campaign != null) {
                processHosts(campaign.details, nationList, teamList)
            }
        }
    }

    private fun processHosts(details: TournamentDetails?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (details == null) return

        val hostList = details.host
        val finalHostList = details.finalHost

        var tmpHostList: List<Team?>? = emptyList()
        for (hostId: Team? in hostList!!) {
            val team = TeamUtil.getTeam(hostId?.id, nationList, teamList)
            if (team != null) {
                tmpHostList = tmpHostList?.plus(team)
            }
        }
        details.host = tmpHostList

        var tmpFinalHostList: List<Team?>? = emptyList()
        for (hostId: Team? in finalHostList!!) {
            val team = TeamUtil.getTeam(hostId?.id, nationList, teamList)
            if (team != null) {
                tmpFinalHostList = tmpFinalHostList?.plus(team)
            }
        }
        details.finalHost = tmpFinalHostList
    }

    fun processFinalStandings(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null) return

        if (tournament.finalStandings != null) {
            val championId = tournament.finalStandings!!.champions?.id
            val runnersUpId = tournament.finalStandings!!.runnersUp?.id
            val thirdPlaceList = tournament.finalStandings!!.thirdPlace
            val fourthPlaceId = tournament.finalStandings!!.fourthPlace?.id
            val semiFinalist1Id = tournament.finalStandings!!.semiFinalist1?.id
            val semiFinalist2Id = tournament.finalStandings!!.semiFinalist2?.id

            val team1 = TeamUtil.getTeam(championId, nationList, teamList)
            if (team1 != null) {
                tournament.finalStandings!!.champions = team1
            }
            val team2 = TeamUtil.getTeam(runnersUpId, nationList, teamList)
            if (team2 != null) {
                tournament.finalStandings!!.runnersUp = team2
            }
            var tmpThirdPlaceList: List<Team?> = emptyList()
            for (thirdPlace: Team? in thirdPlaceList!!) {
                val team3 = TeamUtil.getTeam(thirdPlace?.id, nationList, teamList)
                if (team3 != null) {
                    tmpThirdPlaceList = tmpThirdPlaceList.plus(team3)
                }
            }
            tournament.finalStandings!!.thirdPlace = tmpThirdPlaceList
            val team4 = TeamUtil.getTeam(fourthPlaceId, nationList, teamList)
            if (team4 != null) {
                tournament.finalStandings!!.fourthPlace = team4
            }
            val team5 = TeamUtil.getTeam(semiFinalist1Id, nationList, teamList)
            if (team5 != null) {
                tournament.finalStandings!!.semiFinalist1 = team5
            }
            val team6 = TeamUtil.getTeam(semiFinalist2Id, nationList, teamList)
            if (team6 != null) {
                tournament.finalStandings!!.semiFinalist2 = team6
            }
        }
    }

    private fun processAwards(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament == null || tournament.campaigns.isNullOrEmpty()) return

        processAwards(tournament.awards, nationList, teamList)
        for (campaign: Campaign? in tournament.campaigns!!) {
            if (campaign != null) {
                processAwards(campaign.awards, nationList, teamList)
            }
        }
    }

    private fun processAwards(processingAward: Awards?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (processingAward == null) return

        for (player: Player? in processingAward.goldenBoot!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.silverBoot!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.bronzeBoot!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.finalTopScorer!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.goldenBall!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.finalBestPlayer!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.bestYoungPlayer!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.finalBestYoungPlayer!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.goldenGlove!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.bestForward!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.bestMidfielder!!) {
            processPlayer(player, nationList, teamList)
        }

        for (player: Player? in processingAward.bestDefender!!) {
            processPlayer(player, nationList, teamList)
        }

        var tmpFairPlayTeam: List<Team?> = emptyList()
        for (fairPlay: Team? in processingAward.fairPlayTeam!!) {
            val team = TeamUtil.getTeam(fairPlay!!.id, nationList, teamList)
            if (team != null) {
                tmpFairPlayTeam = tmpFairPlayTeam.plus(team)
            }
        }
        processingAward.fairPlayTeam = tmpFairPlayTeam
    }

    private fun processPlayer(player: Player?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (player?.team == null) return

        var team = TeamUtil.getTeam(player.team!!.id, nationList, teamList)
        if (team != null) {
            player.team = team
        }

        if (player.club != null) {
            team = TeamUtil.getTeam(player.club!!.id, nationList, teamList)
            if (team != null) {
                player.club = team
            }
        }

        if (player.club2 != null) {
            team = TeamUtil.getTeam(player.club2!!.id, nationList, teamList)
            if (team != null) {
                player.club2 = team
            }
        }
    }

    private fun processStages(tournament: Tournament?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (tournament?.campaigns == null) return

        for (campaign: Campaign? in tournament.campaigns!!) {
            if (campaign?.leagues != null && campaign.multipleLeagues!!) {
                MatchUtil.processLeagueCampaign(campaign, nationList, teamList)
            }
            if (campaign?.stages != null && !campaign.multipleLeagues!!) {
                for (stage: Stage? in campaign.stages!!) {
                    MatchUtil.processStage(stage, nationList, teamList)
                }
            }
        }
    }

    fun renderDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartDate(), tournament.tournamentEndDate())
    }

    fun renderQualifyingDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartQualifyingDate(), tournament.tournamentEndQualifyingDate())
    }

    fun renderCompetitionDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartCompetitionDate(), tournament.tournamentEndCompetitionDate())
    }

    fun renderQualifyingCompetitionDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartQualifyingDate(), tournament.tournamentEndCompetitionDate())
    }

    fun renderLeagueDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartLeagueDate(), tournament.tournamentEndLeagueDate())
    }

    fun renderFinalDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartFinalDate(), tournament.tournamentEndFinalDate())
    }

    fun renderLeagueFinalDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartLeagueDate(), tournament.tournamentEndFinalDate())
    }

    fun renderRelegationDates(context: Context?, tournament: Tournament?): String {
        return renderStartEndDates(context, tournament!!.tournamentStartRelegationDate(), tournament.tournamentEndRelegationDate())
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

        if (tournament!!.tournamentTeamCount() == null) return ""

        return if (tournament.tournamentConfedCount() != null && tournament.tournamentConfedCount()!! > 0) {
            if (tournament.tournamentConfedCount() == 1) {
                context!!.getString(
                    R.string.team_count_with_one_confed,
                    tournament.tournamentTeamCount().toString(),
                    tournament.tournamentConfedCount().toString()
                )
            } else {
                context!!.getString(
                    R.string.team_count_with_confeds,
                    tournament.tournamentTeamCount().toString(),
                    tournament.tournamentConfedCount().toString()
                )
            }
        } else tournament.tournamentTeamCount().toString()
    }

    fun renderCompetitionTeamCount(tournament: Tournament?): String {

        if (tournament!!.tournamentCompetitionTeamCount() == null) return ""

        if (tournament.tournamentCompetitionTeamCount() != null && tournament.tournamentTransferTeamCount() != null) return ""

        return tournament.tournamentCompetitionTeamCount().toString()
    }

    fun renderCompetitionPlusTransferTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentCompetitionTeamCount() == null || tournament.tournamentTransferTeamCount() == null) return ""

        return context!!.getString(R.string.total_plus_transfer_team_count, tournament.tournamentCompetitionTeamCount().toString(), tournament.tournamentTransferTeamCount().toString())
    }

    fun renderTotalTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentTotalTeamCount() == null) return ""

        if (tournament.tournamentTotalTeamCount() != null && tournament.tournamentTotalTransferTeamCount() != null) return ""

        return if (tournament.tournamentNationCount() != null) {
            context!!.getString(
                R.string.total_team_count_with_association,
                tournament.tournamentTotalTeamCount().toString(),
                tournament.tournamentNationCount().toString()
            )
        } else tournament.tournamentTotalTeamCount().toString()
    }

    fun renderTotalPlusTransferTeamCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentTotalTeamCount() == null || tournament.tournamentTotalTransferTeamCount() == null) return ""

        return if (tournament.tournamentNationCount() != null) {
            context!!.getString(
                R.string.total_plus_transfer_team_count_with_association,
                tournament.tournamentTotalTeamCount().toString(),
                tournament.tournamentTotalTransferTeamCount().toString(),
                tournament.tournamentNationCount().toString()
            )
        } else context!!.getString(
            R.string.total_plus_transfer_team_count,
            tournament.tournamentTotalTeamCount().toString(),
            tournament.tournamentTotalTransferTeamCount().toString()
        )
    }

    fun renderFinalTeamCount(tournament: Tournament?): String {

        if (tournament!!.tournamentFinalTeamCount() == null) return ""

        return tournament.tournamentFinalTeamCount().toString()
    }

    fun renderVenueCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentVenueCount() == null) return ""

        return if (tournament.tournamentCityCount() != null && tournament.tournamentCityCount()!! > 0) {
            if (tournament.tournamentCityCount()!! == 1) {
                context!!.getString(
                    R.string.venue_count_with_one_city,
                    tournament.tournamentVenueCount().toString(),
                    tournament.tournamentCityCount().toString()
                )
            } else {
                context!!.getString(
                    R.string.venue_count_with_cities,
                    tournament.tournamentVenueCount().toString(),
                    tournament.tournamentCityCount().toString()
                )
            }
        } else tournament.tournamentVenueCount().toString()
    }

    fun renderFinalVenueCount(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentFinalVenueCount() == null) return ""

        return if (tournament.tournamentFinalCityCount() != null) {
            context!!.getString(
                R.string.venue_count_with_cities,
                tournament.tournamentFinalVenueCount().toString(),
                tournament.tournamentFinalCityCount().toString()
            )
        } else tournament.tournamentFinalVenueCount().toString()
    }

    fun renderGoalsScored(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentTotalGoals() == null) return ""

        return if (tournament.tournamentTotalMatches() != null && tournament.tournamentTotalMatches() != 0) {
            context!!.getString(
                R.string.goals_scored_per_match,
                tournament.tournamentTotalGoals().toString(),
                getGoalsPerMatch(tournament.tournamentTotalGoals(), tournament.tournamentTotalMatches()!!)
            )
        } else tournament.tournamentTotalGoals().toString()
    }

    fun renderFinalGoalsScored(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentFinalGoals() == null) return ""

        return if (tournament.tournamentFinalMatches() != null && tournament.tournamentFinalMatches() != 0) {
            context!!.getString(
                R.string.goals_scored_per_match,
                tournament.tournamentFinalGoals().toString(),
                getGoalsPerMatch(tournament.tournamentFinalGoals(), tournament.tournamentFinalMatches()!!)
            )
        } else tournament.tournamentFinalGoals().toString()
    }

    private fun getGoalsPerMatch(goals: Int?, matches: Int?) : String {
        val goalsPerMatch: Double = goals!!.toDouble() / matches!! * 100
        val goalsPerMatchRounded = goalsPerMatch.roundToInt()
        return (goalsPerMatchRounded.toDouble() / 100).toString()
    }

    fun renderAttendance(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentAttendance() == null) return ""

        return if (tournament.tournamentTotalMatches() != null && tournament.tournamentTotalMatches() != 0) {
            val nf = NumberFormat.getInstance()
            context!!.getString(
                R.string.attendance_per_match,
                nf.format(tournament.tournamentAttendance()),
                getAttendancePerMatch(tournament.tournamentAttendance()!!, tournament.tournamentTotalMatches()!!)
            )
        } else tournament.tournamentAttendance().toString()
    }

    fun renderFinalAttendance(context: Context?, tournament: Tournament?): String {

        if (tournament!!.tournamentFinalAttendance() == null) return ""

        return if (tournament.tournamentFinalMatches() != null && tournament.tournamentFinalMatches() != 0) {
            val nf = NumberFormat.getInstance()
            context!!.getString(
                R.string.attendance_per_match,
                nf.format(tournament.tournamentFinalAttendance()),
                getAttendancePerMatch(tournament.tournamentFinalAttendance()!!, tournament.tournamentFinalMatches()!!)
            )
        } else tournament.tournamentFinalAttendance().toString()
    }

    private fun getAttendancePerMatch(attendance: Int?, matches: Int?) : String {
        val nf = NumberFormat.getInstance()
        return nf.format((attendance!!.toDouble() / matches!!).roundToInt())
    }

    fun browseToFinalTournament(context: Activity, tournament: Tournament?) {

        if (tournament == null) return
        var finalCampaign: Campaign? = null
        if (!tournament.campaigns.isNullOrEmpty()) {
            finalCampaign = tournament.campaigns!!.find { it!!.id == tournament.id }
        }
        browseToCampaign(context, tournament, finalCampaign)
    }

    fun browseToCampaign(context: Activity, tournament: Tournament?, campaign: Campaign?) {

        val args = Bundle()
        args.putSerializable(EXTRA_TOURNAMENT, tournament)
        args.putSerializable(EXTRA_CAMPAIGN, campaign)
        val navController = Navigation.findNavController(context, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_tournaments, args)
    }

    fun getChampionsLabel(tournament: Tournament?): Array<Int> {

        if (tournament == null || tournament.competition?.id.isNullOrEmpty()) return arrayOf(0, 0, 0)

        val championsLabel: Int = if (tournament.competition?.id == "MOFT" || tournament.competition?.id == "WOFT") R.string.gold_medal_label else R.string.champions_label
        val runnersUpLabel: Int = if (tournament.competition?.id == "MOFT" || tournament.competition?.id == "WOFT") R.string.silver_medal_label else R.string.runners_up_label
        val thirdPlaceLabel: Int = if (tournament.competition?.id == "MOFT" || tournament.competition?.id == "WOFT") R.string.bronze_medal_label else R.string.third_place_label
        return arrayOf(championsLabel, runnersUpLabel, thirdPlaceLabel)
    }

    fun getTopScorerLabels(tournament: Tournament?): Array<Int> {

        if (tournament == null || tournament.competition?.id.isNullOrEmpty() || tournament.year.isNullOrEmpty()) return arrayOf(0, 0, 0, 0, 0, 0)

        val goldenBootLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.top_scorer_label
                tournament.year!! <= "2006" -> R.string.golden_shoe_label
                else -> R.string.golden_boot_label
            }
            "AFCON", "COPA", "MOFT", "UCL", "UEL", "UECL", "WOFT" -> R.string.top_scorer_label
            else -> R.string.golden_boot_label
        }
        val goldenBootsLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.top_scorers_label
                tournament.year!! <= "2006" -> R.string.golden_shoes_label
                else -> R.string.golden_boots_label
            }
            "AFCON", "COPA", "MOFT", "UCL", "UEL", "UECL", "WOFT" -> R.string.top_scorers_label
            else -> R.string.golden_boots_label
        }
        val silverBootLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.runner_up_label
                tournament.year!! <= "2006" -> R.string.silver_shoe_label
                else -> R.string.silver_boot_label
            }
            else -> R.string.silver_boot_label
        }
        val silverBootsLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.runners_up_label
                tournament.year!! <= "2006" -> R.string.silver_shoes_label
                else -> R.string.silver_boots_label
            }
            else -> R.string.silver_boots_label
        }
        val bronzeBootLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.third_place_label
                tournament.year!! <= "2006" -> R.string.bronze_shoe_label
                else -> R.string.bronze_boot_label
            }
            else -> R.string.bronze_boot_label
        }
        val bronzeBootsLabel: Int = when (tournament.competition?.id) {
            "WC" -> when {
                tournament.year!! <= "1978" -> R.string.third_place_label
                tournament.year!! <= "2006" -> R.string.bronze_shoes_label
                else -> R.string.bronze_boots_label
            }
            else -> R.string.bronze_boots_label
        }
        return arrayOf(goldenBootLabel, goldenBootsLabel, silverBootLabel, silverBootsLabel, bronzeBootLabel, bronzeBootsLabel)
    }

    fun getGoldenBallLabel(tournament: Tournament?): Int {

        if (tournament == null || tournament.competition?.id.isNullOrEmpty()) return 0

        return when (tournament.competition?.id) {
            "EURO" -> R.string.player_tournament_label
            "AFCON" -> R.string.man_competition_label
            "COPA", "UEL" -> R.string.best_player_label
            else -> R.string.golden_ball_label
        }
    }

    fun getGoldenGloveLabel(tournament: Tournament?): Array<Int> {

        if (tournament == null || tournament.competition?.id.isNullOrEmpty()) return arrayOf(0, 0)

        val goldenGloveLabel: Int = if (tournament.competition?.id == "AFCON" || tournament.competition?.id == "COPA" || tournament.competition?.id == "UCL") R.string.best_goalkeeper_label else R.string.golden_glove_label
        val goldenGlovesLabel: Int = if (tournament.competition?.id == "AFCON" || tournament.competition?.id == "COPA" || tournament.competition?.id == "UCL") R.string.best_goalkeepers_label else R.string.golden_gloves_label
        return arrayOf(goldenGloveLabel, goldenGlovesLabel)
    }
}
