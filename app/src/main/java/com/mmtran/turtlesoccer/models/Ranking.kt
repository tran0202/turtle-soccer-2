package com.mmtran.turtlesoccer.models

import java.io.Serializable

class Pool: Serializable {

    var position: Int? = null
    var gf: Int? = null
    var ga: Int? = null
    var pts: Int? = null
    var rankings: List<Ranking?>? = emptyList()

    constructor()
    constructor(gf: Int?, ga: Int?, pts: Int?, rankings: List<Ranking?>?) {
        this.gf = gf
        this.ga = ga
        this.pts = pts
        this.rankings = rankings
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
}
