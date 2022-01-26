package com.mmtran.turtlesoccer.models

import java.io.Serializable

const val FINAL = "Final"
const val THIRD_PLACE = "Third-place"
const val SEMI_FINALS = "Semi-finals"
const val FINAL_ROUND = "Final Round"

class RoundRanking: Serializable {

    var name: String? = null

    var positionPools: List<PositionPool?>? = emptyList()
    var processed: Boolean? = false

    constructor()
    constructor(name: String?) {
        this.name = name
    }
    constructor(name: String?, processed: Boolean?) {
        this.name = name
        this.processed = processed
    }

    fun showRoundRankingName(): Boolean {
        return processed!! && name != FINAL && name != THIRD_PLACE && name != FINAL_ROUND
    }
}

class PositionPool: Serializable {

    var position: Int? = null
    var pools: List<Pool?>? = emptyList()
    var hidePositionPoolDivider: Boolean? = false

    constructor()
    constructor(position: Int?) {
        this.position = position
    }

    fun getRankingList(): List<Ranking?>? {
        var rankings: List<Ranking?>? = emptyList()
        for (pool: Pool? in pools!!) {
            if (pool == null) break
            for (ranking: Ranking? in pool.rankings!!) {
                if (ranking == null) break
                rankings = rankings!!.plus(ranking)
            }
        }
        return rankings
    }
 }

class Pool: Serializable {

    var position: Int? = null
    var gf: Int? = null
    var ga: Int? = null
    var pts: Int? = null
    var rankings: List<Ranking?>? = emptyList()
    var highlighted: Boolean? = false

    constructor()
    constructor(gf: Int?, ga: Int?, pts: Int?, rankings: List<Ranking?>?) {
        this.gf = gf
        this.ga = ga
        this.pts = pts
        this.rankings = rankings
    }

    fun copyPool(): Pool {
        var newRankings = emptyList<Ranking?>()
        for (ranking: Ranking? in rankings!!) {
            val newRanking = ranking!!.copyRanking()
            newRankings = newRankings.plus(newRanking)
        }
        return Pool(this.gf, this.ga, this.pts, newRankings)
    }
}

class KnockoutRankingList {

    var eliminated: List<Ranking?>? = emptyList()
    var advanced: List<Ranking?>? = emptyList()

    constructor()
    constructor(eliminated: List<Ranking?>?, advanced: List<Ranking?>?) {
        this.eliminated = eliminated
        this.advanced = advanced
    }
}

class Ranking {

    var position: Int? = null
    var team: Team? = null
    var mp: Int? = null
    var w: Int? = null
    var d: Int? = null
    var l: Int? = null
    var gf: Int? = null
    var ga: Int? = null
    var gd: Int? = null
    var pts: Int? = null

    constructor()
    constructor(position: Int?, team: Team?, mp: Int?, w: Int?, d: Int?, l: Int?, gf: Int?, ga: Int?, gd: Int?, pts: Int?) {
        this.position = position
        this.team = team
        this.mp = mp
        this.w = w
        this.d = d
        this.l = l
        this.gf = gf
        this.ga = ga
        this.gd = gd
        this.pts = pts
    }

    fun isEqualRankings(pool: Pool?): Boolean {
        if (pool?.gf == null || pool.ga == null || pool.pts == null) return false
        return this.pts == pool.pts && this.gf == pool.gf && this.ga == pool.ga
    }

    fun copyRanking(): Ranking {
        return Ranking(null, this.team, this.mp, this.w, this.d, this.l, this.gf, this.ga, this.gd, this.pts)
    }
}
