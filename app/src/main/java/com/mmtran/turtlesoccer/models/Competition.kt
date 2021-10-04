package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.PropertyName

class Competition {

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

    @get:PropertyName("team_count")
    @set:PropertyName("team_count")
    @PropertyName("team_count")
    var teamCount: Int? = null

    @get:PropertyName("current_champions")
    @set:PropertyName("current_champions")
    @PropertyName("current_champions")
    var currentChampions: String? = null

    @get:PropertyName("last_champions")
    @set:PropertyName("last_champions")
    @PropertyName("last_champions")
    var lastChampions: String? = null

    var descriptions: List<String?>? = emptyList()
    var order: Int? = null

    private val _currentChampionNation: MutableLiveData<Nation> = MutableLiveData()
    val currentChampionNation: LiveData<Nation>
        get() = _currentChampionNation
    fun setCurrentChampionNation(nation: Nation) {
        _currentChampionNation.postValue(nation)
    }

    private val _currentChampionClub: MutableLiveData<Club> = MutableLiveData()
    val currentChampionClub: LiveData<Club>
        get() = _currentChampionClub
    fun setCurrentChampionClub(club: Club) {
        _currentChampionClub.postValue(club)
    }

    constructor() {}
    constructor(id: String?, name: String?, color: String?, teamTypeId: String?, confederationId: String?,
                logoPath: String?, trophyFilename: String?, currentChampions: String?, lastChampions: String?,
                teamCount: Int?, descriptions: List<String?>?, order: Int?) {
        this.id = id
        this.name = name
        this.color = color
        this.teamTypeId = teamTypeId
        this.confederationId = confederationId
        this.logoPath = logoPath
        this.trophyFilename = trophyFilename
        this.currentChampions = currentChampions
        this.lastChampions = lastChampions
        this.teamCount = teamCount
        this.descriptions = descriptions
        this.order = order
    }

    fun isClubCompetition(): Boolean {
        return teamTypeId.equals("CLUB")
    }
}
