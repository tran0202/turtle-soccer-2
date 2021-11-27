package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class TournamentDetails: Serializable {

    var host: List<String?>? = emptyList()
    var hostTeam: List<Team?>? = emptyList()

    @get:PropertyName("final_host")
    @set:PropertyName("final_host")
    @PropertyName("final_host")
    var finalHost: List<String?>? = emptyList()

    var finalHostTeam: List<Team?>? = emptyList()

    @get:PropertyName("logo_filename")
    @set:PropertyName("logo_filename")
    @PropertyName("logo_filename")
    var logoFilename: String? = null

    @get:PropertyName("logo_height")
    @set:PropertyName("logo_height")
    @PropertyName("logo_height")
    var logoHeight: String? = null

    @get:PropertyName("mascot_filename")
    @set:PropertyName("mascot_filename")
    @PropertyName("mascot_filename")
    var mascotFilename: String? = null

    @get:PropertyName("start_date")
    @set:PropertyName("start_date")
    @PropertyName("start_date")
    var startDate: String? = null

    @get:PropertyName("end_date")
    @set:PropertyName("end_date")
    @PropertyName("end_date")
    var endDate: String? = null

    @get:PropertyName("start_qualifying_date")
    @set:PropertyName("start_qualifying_date")
    @PropertyName("start_qualifying_date")
    var startQualifyingDate: String? = null

    @get:PropertyName("end_qualifying_date")
    @set:PropertyName("end_qualifying_date")
    @PropertyName("end_qualifying_date")
    var endQualifyingDate: String? = null

    @get:PropertyName("start_competition_date")
    @set:PropertyName("start_competition_date")
    @PropertyName("start_competition_date")
    var startCompetitionDate: String? = null

    @get:PropertyName("end_competition_date")
    @set:PropertyName("end_competition_date")
    @PropertyName("end_competition_date")
    var endCompetitionDate: String? = null

    @get:PropertyName("start_league_date")
    @set:PropertyName("start_league_date")
    @PropertyName("start_league_date")
    var startLeagueDate: String? = null

    @get:PropertyName("end_league_date")
    @set:PropertyName("end_league_date")
    @PropertyName("end_league_date")
    var endLeagueDate: String? = null

    @get:PropertyName("start_final_date")
    @set:PropertyName("start_final_date")
    @PropertyName("start_final_date")
    var startFinalDate: String? = null

    @get:PropertyName("end_final_date")
    @set:PropertyName("end_final_date")
    @PropertyName("end_final_date")
    var endFinalDate: String? = null

    @get:PropertyName("start_relegation_date")
    @set:PropertyName("start_relegation_date")
    @PropertyName("start_relegation_date")
    var startRelegationDate: String? = null

    @get:PropertyName("end_relegation_date")
    @set:PropertyName("end_relegation_date")
    @PropertyName("end_relegation_date")
    var endRelegationDate: String? = null

    var color: String? = null

    @get:PropertyName("team_count")
    @set:PropertyName("team_count")
    @PropertyName("team_count")
    var teamCount: Int? = null

    @get:PropertyName("final_team_count")
    @set:PropertyName("final_team_count")
    @PropertyName("final_team_count")
    var finalTeamCount: Int? = null

    @get:PropertyName("competition_team_count")
    @set:PropertyName("competition_team_count")
    @PropertyName("competition_team_count")
    var competitionTeamCount: Int? = null

    @get:PropertyName("transfer_team_count")
    @set:PropertyName("transfer_team_count")
    @PropertyName("transfer_team_count")
    var transferTeamCount: Int? = null

    @get:PropertyName("total_team_count")
    @set:PropertyName("total_team_count")
    @PropertyName("total_team_count")
    var totalTeamCount: Int? = null

    @get:PropertyName("total_transfer_team_count")
    @set:PropertyName("total_transfer_team_count")
    @PropertyName("total_transfer_team_count")
    var totalTransferTeamCount: Int? = null

    @get:PropertyName("association_count")
    @set:PropertyName("association_count")
    @PropertyName("association_count")
    var nationCount: Int? = null

    @get:PropertyName("confed_count")
    @set:PropertyName("confed_count")
    @PropertyName("confed_count")
    var confedCount: Int? = null

    @get:PropertyName("venue_count")
    @set:PropertyName("venue_count")
    @PropertyName("venue_count")
    var venueCount: Int? = null

    @get:PropertyName("final_venue_count")
    @set:PropertyName("final_venue_count")
    @PropertyName("final_venue_count")
    var finalVenueCount: Int? = null

    @get:PropertyName("city_count")
    @set:PropertyName("city_count")
    @PropertyName("city_count")
    var cityCount: Int? = null

    @get:PropertyName("final_city_count")
    @set:PropertyName("final_city_count")
    @PropertyName("final_city_count")
    var finalCityCount: Int? = null

    constructor()
    constructor(logoFilename: String?, startDate: String?, endDate: String?) {
        this.logoFilename = logoFilename
        this.startDate = startDate
        this.endDate = endDate
    }
}

class HeroImage: Serializable {

    var name: String? = null
    var filename: String? = null
    var text: String? = null

    constructor()
}

class FinalStandings: Serializable {

    var champions: String? = null
    var championTeam: Team? = null

    @get:PropertyName("runners_up")
    @set:PropertyName("runners_up")
    @PropertyName("runners_up")
    var runnersUp: String? = null

    var runnersUpTeam: Team? = null

    @get:PropertyName("third_place")
    @set:PropertyName("third_place")
    @PropertyName("third_place")
    var thirdPlace: List<String?>? = emptyList()

    var thirdPlaceTeam: List<Team?>? = emptyList()

    @get:PropertyName("third_place_text")
    @set:PropertyName("third_place_text")
    @PropertyName("third_place_text")
    var thirdPlaceText: String? = null

    @get:PropertyName("fourth_place")
    @set:PropertyName("fourth_place")
    @PropertyName("fourth_place")
    var fourthPlace: String? = null

    var fourthPlaceTeam: Team? = Team()

    @get:PropertyName("semi_finalist1")
    @set:PropertyName("semi_finalist1")
    @PropertyName("semi_finalist1")
    var semiFinalist1: String? = null

    var semiFinalist1Team: Team? = Team()

    @get:PropertyName("semi_finalist2")
    @set:PropertyName("semi_finalist2")
    @PropertyName("semi_finalist2")
    var semiFinalist2: String? = null

    var semiFinalist2Team: Team? = Team()

    constructor()
}

class Statistics: Serializable {

    @get:PropertyName("total_matches")
    @set:PropertyName("total_matches")
    @PropertyName("total_matches")
    var totalMatches: Int? = null

    @get:PropertyName("final_matches")
    @set:PropertyName("final_matches")
    @PropertyName("final_matches")
    var finalMatches: Int? = null

    @get:PropertyName("total_goals")
    @set:PropertyName("total_goals")
    @PropertyName("total_goals")
    var totalGoals: Int? = null

    @get:PropertyName("final_goals")
    @set:PropertyName("final_goals")
    @PropertyName("final_goals")
    var finalGoals: Int? = null

    var attendance: Int? = null

    @get:PropertyName("final_attendance")
    @set:PropertyName("final_attendance")
    @PropertyName("final_attendance")
    var finalAttendance: Int? = null

    constructor()
}

class Awards: Serializable {

    @get:PropertyName("golden_boot")
    @set:PropertyName("golden_boot")
    @PropertyName("golden_boot")
    var goldenBoot: List<Player?>? = emptyList()

    @get:PropertyName("silver_boot")
    @set:PropertyName("silver_boot")
    @PropertyName("silver_boot")
    var silverBoot: List<Player?>? = emptyList()

    @get:PropertyName("bronze_boot")
    @set:PropertyName("bronze_boot")
    @PropertyName("bronze_boot")
    var bronzeBoot: List<Player?>? = emptyList()

    @get:PropertyName("final_top_scorer")
    @set:PropertyName("final_top_scorer")
    @PropertyName("final_top_scorer")
    var finalTopScorer: List<Player?>? = emptyList()

    @get:PropertyName("golden_ball")
    @set:PropertyName("golden_ball")
    @PropertyName("golden_ball")
    var goldenBall: List<Player?>? = emptyList()

    @get:PropertyName("final_best_player")
    @set:PropertyName("final_best_player")
    @PropertyName("final_best_player")
    var finalBestPlayer: List<Player?>? = emptyList()

    @get:PropertyName("best_young_player")
    @set:PropertyName("best_young_player")
    @PropertyName("best_young_player")
    var bestYoungPlayer: List<Player?>? = emptyList()

    @get:PropertyName("final_best_young_player")
    @set:PropertyName("final_best_young_player")
    @PropertyName("final_best_young_player")
    var finalBestYoungPlayer: List<Player?>? = emptyList()

    @get:PropertyName("golden_glove")
    @set:PropertyName("golden_glove")
    @PropertyName("golden_glove")
    var goldenGlove: List<Player?>? = emptyList()

    @get:PropertyName("best_forward")
    @set:PropertyName("best_forward")
    @PropertyName("best_forward")
    var bestForward: List<Player?>? = emptyList()

    @get:PropertyName("best_midfielder")
    @set:PropertyName("best_midfielder")
    @PropertyName("best_midfielder")
    var bestMidfielder: List<Player?>? = emptyList()

    @get:PropertyName("best_defender")
    @set:PropertyName("best_defender")
    @PropertyName("best_defender")
    var bestDefender: List<Player?>? = emptyList()

    @get:PropertyName("fair_play_team")
    @set:PropertyName("fair_play_team")
    @PropertyName("fair_play_team")
    var fairPlay: List<String?>? = emptyList()

    var fairPlayTeam: List<Team?>? = emptyList()

    constructor()
}

class Player: Serializable {

    var name: String? = null
    var team: String? = null
    var teamT: Team? = null
    var club: String? = null
    var clubT: Team? = null
    var club2: String? = null
    var club2T: Team? = null
    var goals: Int? = null
    var assists: Int? = null
    var minutes: Int? = null
    var rejected: Boolean? = null

    @get:PropertyName("rejected_notes")
    @set:PropertyName("rejected_notes")
    @PropertyName("rejected_notes")
    var rejectedNotes: String? = null

    constructor()

    fun isMultipleClubs(): Boolean {
        return clubT != null && club2T != null
    }

    fun isClubPlayer(): Boolean {
        return clubT != null
    }
}

enum class SectionHeader(val value: Int) {
    NOT_HEADER(0),
    THIRD_PLACE_HEADER(1),
    SEMIFINALISTS_HEADER(2)
}

enum class ThirdPlaceDetermined(val value: Int) {
    HAS_THIRD_PLACE(0),
    HAS_SEMI_FINALISTS(1)
}

class Tournament: Serializable {

    var id: String? = null
    var year: String? = null

    @get:PropertyName("short_year")
    @set:PropertyName("short_year")
    @PropertyName("short_year")
    var shortYear: String? = null

    var name: String? = null

    @get:PropertyName("short_name")
    @set:PropertyName("short_name")
    @PropertyName("short_name")
    var shortName: String? = null

    @get:PropertyName("original_name")
    @set:PropertyName("original_name")
    @PropertyName("original_name")
    var originalName: String? = null

    var era: String? = null

    @get:PropertyName("competition_id")
    @set:PropertyName("competition_id")
    @PropertyName("competition_id")
    var competitionId: String? = null

    var active: Boolean? = null

    @get:PropertyName("golden_goal_rule")
    @set:PropertyName("golden_goal_rule")
    @PropertyName("golden_goal_rule")
    var goldenGoalRule: Boolean? = null

    @get:PropertyName("silver_goal_rule")
    @set:PropertyName("silver_goal_rule")
    @PropertyName("silver_goal_rule")
    var silverGoalRule: Boolean? = null

    @get:PropertyName("points_for_win")
    @set:PropertyName("points_for_win")
    @PropertyName("points_for_win")
    var pointsForWin: Int? = null

    @get:PropertyName("tiebreakers_collapsed")
    @set:PropertyName("tiebreakers_collapsed")
    @PropertyName("tiebreakers_collapsed")
    var tiebreakersCollapsed: Boolean? = null

    var tiebreakers: List<String?>? = emptyList()

    @get:PropertyName("no_third_place")
    @set:PropertyName("no_third_place")
    @PropertyName("no_third_place")
    var noThirdPlace: Boolean? = null

    @get:PropertyName("show_match_year")
    @set:PropertyName("show_match_year")
    @PropertyName("show_match_year")
    var showMatchYear: Boolean? = null

    var details: TournamentDetails? = TournamentDetails()

    @get:PropertyName("hero_images")
    @set:PropertyName("hero_images")
    @PropertyName("hero_images")
    var heroImages: List<HeroImage?>? = emptyList()

    @get:PropertyName("final_standings")
    @set:PropertyName("final_standings")
    @PropertyName("final_standings")
    var finalStandings: FinalStandings? = null

    var statistics: Statistics? = Statistics()
    var awards: Awards? = Awards()

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    var compTourResultSectionHeader: SectionHeader = SectionHeader.NOT_HEADER
    var compTourResultEvenRow: Boolean = false
    var thirdPlaceDetermined: ThirdPlaceDetermined = ThirdPlaceDetermined.HAS_THIRD_PLACE
    var competition: Competition? = Competition()

    constructor()
    constructor(id: String?, year: String?, name: String?, competitionId: String?, active: Boolean?,
                goldenGoalRule: Boolean?, pointsForWin: Int?) {
        this.id = id
        this.year = year
        this.name = name
        this.competitionId = competitionId
        this.active = active
        this.goldenGoalRule = goldenGoalRule
        this.pointsForWin = pointsForWin
    }
}
