package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class Stage: Serializable {

    var name: String? = null

    constructor()
}

class Campaign: Serializable {

    var id: String? = null
    var name: String? = null

    @get:PropertyName("tournament_id")
    @set:PropertyName("tournament_id")
    @PropertyName("tournament_id")
    var tournamentId: String? = null

    var description: String? = null
    var order: Int? = null
    var stages: List<Stage?>? = emptyList()

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    constructor()
    constructor(id: String?, name: String?, tournamentId: String?, description: String?, order: Int?) {
        this.id = id
        this.name = name
        this.tournamentId = tournamentId
        this.description = description
        this.order = order
    }
}
