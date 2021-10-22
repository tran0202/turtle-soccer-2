package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName

class Confederation {

    var id: String? = null
    var name: String? = null

    @get:PropertyName("logo_filename")
    @set:PropertyName("logo_filename")
    @PropertyName("logo_filename")
    var logoFilename: String? = null
    var description: String? = null

    @get:PropertyName("time_stamp")
    @set:PropertyName("time_stamp")
    @PropertyName("time_stamp")
    var timeStamp: String? = null

    var competitionList: List<Competition?>? = emptyList()

    constructor()
    constructor(id: String?, name: String?, logoFilename: String?, description: String?) {
        this.id = id
        this.name = name
        this.logoFilename = logoFilename
        this.description = description
    }
}
