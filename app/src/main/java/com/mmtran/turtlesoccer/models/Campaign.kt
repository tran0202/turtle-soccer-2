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

    @get:PropertyName("multiple_legs")
    @set:PropertyName("multiple_legs")
    @PropertyName("multiple_legs")
    var multipleLegs: Boolean? = false

    @get:PropertyName("home_team")
    @set:PropertyName("home_team")
    @PropertyName("home_team")
    var homeTeam: Team? = null

    @get:PropertyName("away_team")
    @set:PropertyName("away_team")
    @PropertyName("away_team")
    var awayTeam: Team? = null

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

    @get:PropertyName("leg2_home_team")
    @set:PropertyName("leg2_home_team")
    @PropertyName("leg2_home_team")
    var leg2HomeTeam: Team? = null

    @get:PropertyName("leg2_away_team")
    @set:PropertyName("leg2_away_team")
    @PropertyName("leg2_away_team")
    var leg2AwayTeam: Team? = null

    @get:PropertyName("leg2_home_score")
    @set:PropertyName("leg2_home_score")
    @PropertyName("leg2_home_score")
    var leg2HomeScore: Int? = null

    @get:PropertyName("leg2_away_score")
    @set:PropertyName("leg2_away_score")
    @PropertyName("leg2_away_score")
    var leg2AwayScore: Int? = null

    @get:PropertyName("leg2_home_extra_score")
    @set:PropertyName("leg2_home_extra_score")
    @PropertyName("leg2_home_extra_score")
    var leg2HomeExtraScore: Int? = null

    @get:PropertyName("leg2_away_extra_score")
    @set:PropertyName("leg2_away_extra_score")
    @PropertyName("leg2_away_extra_score")
    var leg2AwayExtraScore: Int? = null

    @get:PropertyName("leg2_home_penalty_score")
    @set:PropertyName("leg2_home_penalty_score")
    @PropertyName("leg2_home_penalty_score")
    var leg2HomePenaltyScore: Int? = null

    @get:PropertyName("leg2_away_penalty_score")
    @set:PropertyName("leg2_away_penalty_score")
    @PropertyName("leg2_away_penalty_score")
    var leg2AwayPenaltyScore: Int? = null

    @get:PropertyName("shared_bronze")
    @set:PropertyName("shared_bronze")
    @PropertyName("shared_bronze")
    var sharedBronze: Boolean? = false

    @get:PropertyName("shared_bronze_text")
    @set:PropertyName("shared_bronze_text")
    @PropertyName("shared_bronze_text")
    var sharedBronzeText: String? = null

    var date: String? = null
    var time: String? = null
    var stadium: String? = null
    var city: String? = null

    @get:PropertyName("leg2_date")
    @set:PropertyName("leg2_date")
    @PropertyName("leg2_date")
    var leg2Date: String? = null

    @get:PropertyName("leg2_time")
    @set:PropertyName("leg2_time")
    @PropertyName("leg2_time")
    var leg2Time: String? = null

    @get:PropertyName("leg2_stadium")
    @set:PropertyName("leg2_stadium")
    @PropertyName("leg2_stadium")
    var leg2Stadium: String? = null

    @get:PropertyName("leg2_city")
    @set:PropertyName("leg2_city")
    @PropertyName("leg2_city")
    var leg2City: String? = null

    @get:PropertyName("bracket_order")
    @set:PropertyName("bracket_order")
    @PropertyName("bracket_order")
    var bracketOrder: Int? = null

    var groupName: String? = null

    constructor()

    private fun isRoundRobinMatch(): Boolean {
        return groupName != null
    }

    private fun isKnockoutMatch(): Boolean {
        return !isRoundRobinMatch()
    }

    fun isExtraTimeMatch(): Boolean {
        return homeExtraScore != null && awayExtraScore != null
    }

    fun isPenaltyMatch(): Boolean {
        return homePenaltyScore != null && awayPenaltyScore != null
    }

    fun isLeg2ExtraTimeMatch(): Boolean {
        return multipleLegs!! && leg2HomeExtraScore != null && leg2AwayExtraScore != null
    }

    fun isLeg2PenaltyMatch(): Boolean {
        return multipleLegs!! && leg2HomePenaltyScore != null && leg2AwayPenaltyScore != null
    }

    fun homeAfterExtraTimeScore(): Int? {
        if (homeScore == null || homeExtraScore == null) return null
        return homeScore!! + homeExtraScore!!
    }

    fun awayAfterExtraTimeScore(): Int? {
        if (awayScore == null || awayExtraScore == null) return null
        return awayScore!! + awayExtraScore!!
    }

    fun leg2HomeAfterExtraTimeScore(): Int? {
        if (leg2HomeScore == null || leg2HomeExtraScore == null) return null
        return leg2HomeScore!! + leg2HomeExtraScore!!
    }

    fun leg2AwayAfterExtraTimeScore(): Int? {
        if (leg2AwayScore == null || leg2AwayExtraScore == null) return null
        return leg2AwayScore!! + leg2AwayExtraScore!!
    }

    fun aggregateHomeScore(): Int? {
        if (leg2HomeScore == null || awayScore == null) return null
        if (leg2HomeExtraScore == null) return leg2HomeScore!! + awayScore!!
        return leg2HomeAfterExtraTimeScore()!! + awayScore!!
    }

    fun aggregateAwayScore(): Int? {
        if (leg2AwayScore == null || homeScore == null) return null
        if (leg2AwayExtraScore == null) return leg2AwayScore!! + homeScore!!
        return leg2AwayAfterExtraTimeScore()!! + homeScore!!
    }

    private fun isHomeRegularWin(): Boolean {
        if (homeScore == null || awayScore == null) return false
        return homeScore!! > awayScore!!
    }

    private fun isAwayRegularWin(): Boolean {
        if (homeScore == null || awayScore == null) return false
        return homeScore!! < awayScore!!
    }

    private fun isHomeExtraWin(): Boolean {
        if (homeScore == null || awayScore == null || homeExtraScore == null || awayExtraScore == null) return false
        return homeScore!! == awayScore!! && homeExtraScore!! > awayExtraScore!!
    }

    private fun isAwayExtraWin(): Boolean {
        if (homeScore == null || awayScore == null || homeExtraScore == null || awayExtraScore == null) return false
        return homeScore!! == awayScore!! && homeExtraScore!! < awayExtraScore!!
    }

    private fun isHomePenaltyWin(): Boolean {
        if (homeScore == null || awayScore == null || homeExtraScore == null || awayExtraScore == null
            || homePenaltyScore == null || awayPenaltyScore == null) return false
        return homeScore!! == awayScore!! && homeExtraScore!! == awayExtraScore!! && homePenaltyScore!! > awayPenaltyScore!!
    }

    private fun isAwayPenaltyWin(): Boolean {
        if (homeScore == null || awayScore == null || homeExtraScore == null || awayExtraScore == null
            || homePenaltyScore == null || awayPenaltyScore == null) return false
        return homeScore!! == awayScore!! && homeExtraScore!! == awayExtraScore!! && homePenaltyScore!! < awayPenaltyScore!!
    }

    private fun isHomeWin(): Boolean {
        if (homeScore == null || awayScore == null) return false
        if (sharedBronze!!) return true
        if (isHomeRegularWin()) return true
        if (isHomeExtraWin()) return true
        if (isHomePenaltyWin()) return true
        return false
    }

    private fun isAwayWin(): Boolean {
        if (homeScore == null || awayScore == null) return false
        if (sharedBronze!!) return true
        if (isAwayRegularWin()) return true
        if (isAwayExtraWin()) return true
        if (isAwayPenaltyWin()) return true
        return false
    }

    private fun isKnockoutHomeWin(): Boolean {
        return isKnockoutMatch() && isHomeWin()
    }

    private fun isKnockoutAwayWin(): Boolean {
        return isKnockoutMatch() && isAwayWin()
    }

    fun isAggregateScoreTie(): Boolean {
        return multipleLegs!! && aggregateHomeScore() == aggregateAwayScore()
    }

    private fun isAggregateHomeWin(): Boolean {
        if (multipleLegs!! && aggregateHomeScore()!! > aggregateAwayScore()!!) return true
        if (isAggregateScoreTie()) {
            return if (!isLeg2ExtraTimeMatch()) awayScore!! > leg2AwayScore!!
                    else awayScore!! > leg2AwayAfterExtraTimeScore()!!
        }
        return false
    }

    private fun isAggregateAwayWin(): Boolean {
        if (multipleLegs!! && aggregateHomeScore()!! < aggregateAwayScore()!!) return true
        if (isAggregateScoreTie()) {
            return if (!isLeg2ExtraTimeMatch()) awayScore!! < leg2AwayScore!!
                    else awayScore!! < leg2AwayAfterExtraTimeScore()!!
        }
        return false
    }

    private fun isLeg2HomePenaltyWin(): Boolean {
        if (isAggregateScoreTie() && isLeg2PenaltyMatch()) {
            return leg2HomePenaltyScore!! > leg2AwayPenaltyScore!!
        }
        return false
    }

    private fun isLeg2AwayPenaltyWin(): Boolean {
        if (isAggregateScoreTie() && isLeg2PenaltyMatch()) {
            return leg2HomePenaltyScore!! < leg2AwayPenaltyScore!!
        }
        return false
    }

    fun getTeamNameWin(): String? {
        if (getTeamWin() == null) return ""
        return getTeamWin()!!.name
    }

    private fun getTeamWin(): Team? {
        if (!multipleLegs!!) {
            if (isHomeWin()) return homeTeam
            if (isAwayWin()) return awayTeam
        }
        if (!isLeg2PenaltyMatch()) {
            if (isAggregateHomeWin()) return leg2HomeTeam
            if (isAggregateAwayWin()) return leg2AwayTeam
        } else {
            if (isLeg2HomePenaltyWin()) return leg2HomeTeam
            if (isLeg2AwayPenaltyWin()) return leg2AwayTeam
        }
        return null
    }

    fun isLeg1HomeEmphasizedName(): Boolean {
        return isRoundRobinMatch() || isKnockoutHomeWin() || multipleLegs!!
    }

    fun isLeg1AwayEmphasizedName(): Boolean {
        return isRoundRobinMatch() || isKnockoutAwayWin() || multipleLegs!!
    }

    fun isLeg2HomeEmphasizedName(): Boolean {
        return isAggregateHomeWin() || isLeg2HomePenaltyWin()
    }

    fun isLeg2AwayEmphasizedName(): Boolean {
        return isAggregateAwayWin() || isLeg2AwayPenaltyWin()
    }
}

class Group: Serializable {

    var name: String? = null
    var teams: List<Team?>? = emptyList()

    var matches: List<Match?>? = emptyList()
    var matchdays: List<Matchday?>? = emptyList()

    constructor()
}

class Matchday: Serializable {

    var name: String? = null
    var matches: List<Match?>? = emptyList()

    constructor()
    constructor(name: String?) {
        this.name = name
    }
}

class Path: Serializable {

    var name: String? = null
    var matches: List<Match?>? = emptyList()

    var matchdayList: List<Matchday?>? = emptyList()
    var hidePathName: Boolean? = false

    constructor()
    constructor(name: String?, hidePathName: Boolean?) {
        this.name = name
        this.hidePathName = hidePathName
    }
}

class Round: Serializable {

    var name: String? = null

    @get:PropertyName("short_name")
    @set:PropertyName("short_name")
    @PropertyName("short_name")
    var shortName: String? = null

    @get:PropertyName("eliminate_count")
    @set:PropertyName("eliminate_count")
    @PropertyName("eliminate_count")
    var eliminateCount: Int? = null

    @get:PropertyName("next_round")
    @set:PropertyName("next_round")
    @PropertyName("next_round")
    var nextRound: String? = null

    @get:PropertyName("bye_teams")
    @set:PropertyName("bye_teams")
    @PropertyName("bye_teams")
    var byeTeams: List<Team?>? = emptyList()

    @get:PropertyName("multiple_paths")
    @set:PropertyName("multiple_paths")
    @PropertyName("multiple_paths")
    var multiplePaths: Boolean? = false

    var paths: List<Path?>? = emptyList()

    var matches: List<Match?>? = emptyList()
    var hideRoundName: Boolean? = false

    constructor()
    constructor(name: String?) {
        this.name = name
    }
    constructor(name: String?, hideRoundName: Boolean?) {
        this.name = name
        this.hideRoundName = hideRoundName
    }

    fun hidePathName(): Boolean {
        return paths?.isEmpty()!! || paths?.size == 1
    }
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

    @get:PropertyName("bye_teams")
    @set:PropertyName("bye_teams")
    @PropertyName("bye_teams")
    var byeTeams: List<Team?>? = emptyList()

    var advancement: Advancement? = Advancement()

    @get:PropertyName("multiple_matchdays")
    @set:PropertyName("multiple_matchdays")
    @PropertyName("multiple_matchdays")
    var multipleMatchdays: Boolean? = false

    var groups: List<Group?>? = emptyList()

    @get:PropertyName("hide_bracket")
    @set:PropertyName("hide_bracket")
    @PropertyName("hide_bracket")
    var hideBracket: Boolean? = null

    var rounds: List<Round?>? = emptyList()

    constructor()
    constructor(name: String?, type: String?) {
        this.name = name
        this.type = type
    }

    fun isRoundRobin(): Boolean {
        return type == ROUNDROBIN
    }

    fun isKnockout(): Boolean {
        return type == KNOCKOUT
    }

    fun hideRoundName(): Boolean {
        return rounds?.isEmpty()!! || rounds?.size == 1
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
