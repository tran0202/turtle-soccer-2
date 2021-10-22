package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Champion: Serializable {

    @get:PropertyName("team_id")
    @set:PropertyName("team_id")
    @PropertyName("team_id")
    var teamId: String? = null

    var team: Team? = null

    @get:PropertyName("title_count")
    @set:PropertyName("title_count")
    @PropertyName("title_count")
    var titleCount: Int? = null

    constructor()
    constructor(teamId: String?) {
        this.teamId = teamId
    }
}

class Competition: Serializable {

    var id: String? = null
    var name: String? = null
    var color: String? = null

    @get:PropertyName("team_type_id")
    @set:PropertyName("team_type_id")
    @PropertyName("team_type_id")
    var teamTypeId: String? = null

    @get:PropertyName("confederation_id")
    @set:PropertyName("confederation_id")
    @PropertyName("confederation_id")
    var confederationId: String? = null

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
    var currentChampions: Champion? = null

    @get:PropertyName("last_champions")
    @set:PropertyName("last_champions")
    @PropertyName("last_champions")
    var lastChampions: Champion? = null

    @get:PropertyName("most_successful_teams")
    @set:PropertyName("most_successful_teams")
    @PropertyName("most_successful_teams")
    var mostSuccessfulTeams: List<Champion?>? = emptyList()

    var descriptions: List<String?>? = emptyList()
    var order: Int? = null

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    var tournamentList: List<Tournament?>? = emptyList()

    constructor()
    constructor(id: String?, name: String?, color: String?, teamTypeId: String?, confederationId: String?,
                logoPath: String?, trophyFilename: String?,
                teamCount: Int?, descriptions: List<String?>?, order: Int?) {
        this.id = id
        this.name = name
        this.color = color
        this.teamTypeId = teamTypeId
        this.confederationId = confederationId
        this.logoPath = logoPath
        this.trophyFilename = trophyFilename
        this.teamCount = teamCount
        this.descriptions = descriptions
        this.order = order
    }

    fun isClubCompetition(): Boolean {
        return teamTypeId.equals("CLUB")
    }
}
