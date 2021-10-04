package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName

class Club {

    var id: String? = null
    var name: String? = null

    @get:PropertyName("team_type_id")
    @set:PropertyName("team_type_id")
    @PropertyName("team_type_id")
    var teamTypeId: String? = null

    @get:PropertyName("nation_id")
    @set:PropertyName("nation_id")
    @PropertyName("nation_id")
    var nationId: String? = null

    @get:PropertyName("parent_team_id")
    @set:PropertyName("parent_team_id")
    @PropertyName("parent_team_id")
    var parentTeamId: String? = null

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

    constructor() {}
    constructor(id: String?, name: String?, teamTypeId: String?, nationId: String?,
                parentTeamId: String?, clubCode: String?, logoFilename: String?, officialName: String?) {
        this.id = id
        this.name = name
        this.teamTypeId = teamTypeId
        this.nationId = nationId
        this.parentTeamId = parentTeamId
        this.clubCode = clubCode
        this.logoFilename = logoFilename
        this.officialName = officialName
    }
}
