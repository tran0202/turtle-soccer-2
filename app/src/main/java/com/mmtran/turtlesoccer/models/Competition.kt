package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Competition: Serializable {

    var id: String? = null
    var name: String? = null
    var color: String? = null

    @get:PropertyName("team_type_id")
    @set:PropertyName("team_type_id")
    @PropertyName("team_type_id")
    var teamTypeId: String? = null

    var confederation: Confederation? = null

    @get:PropertyName("logo_path")
    @set:PropertyName("logo_path")
    @PropertyName("logo_path")
    var logoPath: String? = null

    @get:PropertyName("trophy_filename")
    @set:PropertyName("trophy_filename")
    @PropertyName("trophy_filename")
    var trophyFilename: String? = null

    @get:PropertyName("show_successors")
    @set:PropertyName("show_successors")
    @PropertyName("show_successors")
    var showSuccessors: Boolean? = null

    @get:PropertyName("team_count")
    @set:PropertyName("team_count")
    @PropertyName("team_count")
    var teamCount: Int? = null

    @get:PropertyName("current_champions")
    @set:PropertyName("current_champions")
    @PropertyName("current_champions")
    var currentChampions: Team? = null

    @get:PropertyName("last_champions")
    @set:PropertyName("last_champions")
    @PropertyName("last_champions")
    var lastChampions: Team? = null

    @get:PropertyName("most_successful_teams")
    @set:PropertyName("most_successful_teams")
    @PropertyName("most_successful_teams")
    var mostSuccessfulTeams: List<Team?>? = emptyList()

    var descriptions: List<String?>? = emptyList()
    var order: Int? = null

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    var tournamentList: List<Tournament?>? = emptyList()
    var pools: List<Pool?>? = emptyList()

    constructor()
    constructor(id: String?, name: String?, color: String?, teamTypeId: String?,
                logoPath: String?, trophyFilename: String?,
                teamCount: Int?, descriptions: List<String?>?, order: Int?) {
        this.id = id
        this.name = name
        this.color = color
        this.teamTypeId = teamTypeId
        this.logoPath = logoPath
        this.trophyFilename = trophyFilename
        this.teamCount = teamCount
        this.descriptions = descriptions
        this.order = order
    }
}
