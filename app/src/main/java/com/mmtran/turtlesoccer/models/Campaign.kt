package com.mmtran.turtlesoccer.models

import com.google.firebase.firestore.PropertyName
import java.io.Serializable

const val ROUNDROBIN = "roundrobin"
const val KNOCKOUT = "knockout"

class WildCard: Serializable {

    var position: Int? = null
    var count: Int? = null

    @get:PropertyName("count_extra")
    @set:PropertyName("count_extra")
    @PropertyName("count_extra")
    var countExtra: Int? = null

    @get:PropertyName("text_extra")
    @set:PropertyName("text_extra")
    @PropertyName("text_extra")
    var textExtra: String? = null

    constructor()
    constructor(position: Int?, count: Int?) {
        this.position = position
        this.count = count
    }
}

class Advancement: Serializable {

    var auto: List<Int?>? = emptyList()

    @get:PropertyName("wild_card")
    @set:PropertyName("wild_card")
    @PropertyName("wild_card")
    var wildCard: WildCard? = WildCard()

    var transferred: List<Int?>? = emptyList()
    var eliminated: List<Int?>? = emptyList()
    var text: String? = null
    var extra: String? = null

    constructor()
    constructor(auto: List<Int?>?) {
        this.auto = auto
    }
}

class Match: Serializable {

    @get:PropertyName("home_team")
    @set:PropertyName("home_team")
    @PropertyName("home_team")
    var homeTeam: String? = null

    @get:PropertyName("away_team")
    @set:PropertyName("away_team")
    @PropertyName("away_team")
    var awayTeam: String? = null

    @get:PropertyName("home_score")
    @set:PropertyName("home_score")
    @PropertyName("home_score")
    var homeScore: Int? = null

    @get:PropertyName("away_score")
    @set:PropertyName("away_score")
    @PropertyName("away_score")
    var awayScore: Int? = null

    @get:PropertyName("home_extra_score")
    @set:PropertyName("home_extra_score")
    @PropertyName("home_extra_score")
    var homeExtraScore: Int? = null

    @get:PropertyName("away_extra_score")
    @set:PropertyName("away_extra_score")
    @PropertyName("away_extra_score")
    var awayExtraScore: Int? = null

    @get:PropertyName("home_penalty_score")
    @set:PropertyName("home_penalty_score")
    @PropertyName("home_penalty_score")
    var homePenaltyScore: Int? = null

    @get:PropertyName("away_penalty_score")
    @set:PropertyName("away_penalty_score")
    @PropertyName("away_penalty_score")
    var awayPenaltyScore: Int? = null

    var date: String? = null
    var time: String? = null
    var stadium: String? = null
    var city: String? = null

    @get:PropertyName("bracket_order")
    @set:PropertyName("bracket_order")
    @PropertyName("bracket_order")
    var bracketOrder: Int? = null

    constructor()
}

class Group: Serializable {

    var name: String? = null
    var teams: List<Team?>? = emptyList()
    var matches: List<Match?>? = emptyList()

    constructor()
}

class Stage: Serializable {

    var name: String? = null
    var type: String? = null
    var default: Boolean? = null

    @get:PropertyName("eliminate_count")
    @set:PropertyName("eliminate_count")
    @PropertyName("eliminate_count")
    var eliminateCount: Int? = null

    @get:PropertyName("home_and_away")
    @set:PropertyName("home_and_away")
    @PropertyName("home_and_away")
    var homeAndAway: Boolean? = null

    @get:PropertyName("next_round")
    @set:PropertyName("next_round")
    @PropertyName("next_round")
    var nextRound: String? = null

    var advancement: Advancement? = Advancement()
    var groups: List<Group?>? = emptyList()

    constructor()
    constructor(name: String?, type: String?) {
        this.name = name
        this.type = type
    }

    fun isRoundRobin(): Boolean {
        return type == ROUNDROBIN
    }
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
    var details: TournamentDetails? = TournamentDetails()
    var finalStandings: FinalStandings? = FinalStandings()
    var statistics: Statistics? = Statistics()
    var awards: Awards? = Awards()
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
