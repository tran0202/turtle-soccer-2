package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Team: Serializable {

    var id: String? = null
    var name: String? = null

    @get:PropertyName("short_name")
    @set:PropertyName("short_name")
    @PropertyName("short_name")
    var shortName: String? = null

    @get:PropertyName("team_type_id")
    @set:PropertyName("team_type_id")
    @PropertyName("team_type_id")
    var teamTypeId: String? = null

    var nation: Nation? = null

    @get:PropertyName("parent_team")
    @set:PropertyName("parent_team")
    @PropertyName("parent_team")
    var parentTeam: Team? = null

    var successor: Boolean? = null
    var code: String? = null

    @get:PropertyName("club_code")
    @set:PropertyName("club_code")
    @PropertyName("club_code")
    var clubCode: String? = null

    @get:PropertyName("logo_filename")
    @set:PropertyName("logo_filename")
    @PropertyName("logo_filename")
    var logoFilename: String? = null

    @get:PropertyName("official_name")
    @set:PropertyName("official_name")
    @PropertyName("official_name")
    var officialName: String? = null

    @get:PropertyName("title_count")
    @set:PropertyName("title_count")
    @PropertyName("title_count")
    var titleCount: Int? = null

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    constructor()
    constructor(id: String?, name: String?, shortName: String?, teamTypeId: String?, successor: Boolean?) {
        this.id = id
        this.name = name
        this.shortName = shortName
        this.teamTypeId = teamTypeId
        this.successor = successor
    }

    fun isValid(): Boolean {
        return id != null
    }

    fun copy(): Team {
        val team = Team()
        team.id = this.id
        team.name = this.name
        team.shortName = this.shortName
        team.teamTypeId = this.teamTypeId
        team.nation = this.nation
        team.successor = this.successor
        team.code = this.code
        team.clubCode = this.clubCode
        team.logoFilename = this.logoFilename
        team.officialName = this.officialName
        team.titleCount = this.titleCount
        team.timeStamp = this.timeStamp
        return team
    }
}
