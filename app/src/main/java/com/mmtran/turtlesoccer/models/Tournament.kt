package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName

class TournamentDetails {

    @get:PropertyName("logo_filename")
    @set:PropertyName("logo_filename")
    @PropertyName("logo_filename")
    var logoFilename: String? = null

    @get:PropertyName("start_date")
    @set:PropertyName("start_date")
    @PropertyName("start_date")
    var startDate: String? = null

    @get:PropertyName("end_date")
    @set:PropertyName("end_date")
    @PropertyName("end_date")
    var endDate: String? = null

    constructor() {}
    constructor(logoFilename: String?, startDate: String?, endDate: String?) {
        this.logoFilename = logoFilename
        this.startDate = startDate
        this.endDate = endDate
    }

}

class Tournament {

    var id: String? = null
    var year: String? = null
    var name: String? = null

    @get:PropertyName("competition_id")
    @set:PropertyName("competition_id")
    @PropertyName("competition_id")
    var competitionId: String? = null

    var active: Boolean? = null

    @get:PropertyName("golden_goal_rule")
    @set:PropertyName("golden_goal_rule")
    @PropertyName("golden_goal_rule")
    var goldenGoalRule: Boolean? = null

    @get:PropertyName("points_for_win")
    @set:PropertyName("points_for_win")
    @PropertyName("points_for_win")
    var pointsForWin: Int? = null

    var details: TournamentDetails? = TournamentDetails()

    constructor() {}
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
