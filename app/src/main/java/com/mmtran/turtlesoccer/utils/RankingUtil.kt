package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.widget.TextView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.*

object RankingUtil {

    fun newRanking(team: Team?): Ranking {
        return Ranking(null, team, 0,0,0,0,0,0,0,0)
    }

    fun initGroupRankings(group: Group?, nationList: List<Nation?>?, teamList: List<Team?>?) {
        if (group?.teams == null) return

        var newTeamList: List<Team?>? = emptyList()
        for (team: Team? in group.teams!!) {
            if (team == null) break

            val newTeam = TeamUtil.getTeam(team.id, nationList, teamList)
            if (newTeam != null) {
                newTeamList = newTeamList!!.plus(newTeam)
            }
        }
        group.teams = newTeamList

        var newRankingList: List<Ranking?>? = emptyList()
        for (team: Team? in group.teams!!) {
            if (team == null) break

            val newRanking = newRanking(team)
            newRankingList = newRankingList!!.plus(newRanking)
        }
        group.rankings = newRankingList
    }

    fun accumulateGroupRankings(tournament: Tournament?, group: Group?, match: Match?) {

        if (tournament == null || group == null || match == null || match.homeTeam == null || match.awayTeam == null
            || match.homeScore == null || match.awayScore == null) return

        val homeRanking = group.rankings!!.find { it!!.team!!.name.equals(match.homeTeam!!.name) }
        val awayRanking = group.rankings!!.find { it!!.team!!.name.equals(match.awayTeam!!.name) }
        if (homeRanking == null || awayRanking == null) return
        accumulateRankings(tournament, tournament.pointsForWin, homeRanking, awayRanking, match)
    }

    fun accumulateRankings(tournament: Tournament?, pointsForWin: Int?, homeRanking: Ranking?, awayRanking: Ranking?, match: Match?) {
        if (tournament == null || pointsForWin == null || homeRanking == null || awayRanking == null
            || match?.homeScore() == null || match.awayScore() == null) return

        homeRanking.mp = homeRanking.mp?.plus(1)
        awayRanking.mp = awayRanking.mp?.plus(1)
        when {
            match.homeScore()!! > match.awayScore()!! -> {
                homeRanking.w = homeRanking.w?.plus(1)
                homeRanking.pts = homeRanking.pts?.plus(pointsForWin)
                awayRanking.l = awayRanking.l?.plus(1)
            }
            match.homeScore()!! < match.awayScore()!! -> {
                homeRanking.l = homeRanking.l?.plus(1)
                awayRanking.w = awayRanking.w?.plus(1)
                awayRanking.pts = awayRanking.pts?.plus(pointsForWin)
            }
            else -> when {
                match.isExtraTimeMatch() && match.homeExtraScore!! > match.awayExtraScore!! -> {
                    homeRanking.w = homeRanking.w?.plus(1)
                    homeRanking.pts = homeRanking.pts?.plus(pointsForWin)
                    awayRanking.l = awayRanking.l?.plus(1)
                }
                match.isExtraTimeMatch() && match.homeExtraScore!! < match.awayExtraScore!! -> {
                    homeRanking.l = homeRanking.l?.plus(1)
                    awayRanking.w = awayRanking.w?.plus(1)
                    awayRanking.pts = awayRanking.pts?.plus(pointsForWin)
                }
                else -> {
                    homeRanking.d = homeRanking.d?.plus(1)
                    homeRanking.pts = homeRanking.pts?.plus(1)
                    awayRanking.d = awayRanking.d?.plus(1)
                    awayRanking.pts = awayRanking.pts?.plus(1)
                }
            }
        }
        homeRanking.gf = homeRanking.gf?.plus(match.homeScore()!!)
        homeRanking.ga = homeRanking.ga?.plus(match.awayScore()!!)
        if (match.isExtraTimeMatch()) {
            homeRanking.gf = homeRanking.gf?.plus(match.homeExtraScore!!)
            homeRanking.ga = homeRanking.ga?.plus(match.awayExtraScore!!)
        }
        homeRanking.gd = homeRanking.gf!! - homeRanking.ga!!

        awayRanking.gf = awayRanking.gf?.plus(match.awayScore()!!)
        awayRanking.ga = awayRanking.ga?.plus(match.homeScore()!!)
        if (match.isExtraTimeMatch()) {
            awayRanking.gf = awayRanking.gf?.plus(match.awayExtraScore!!)
            awayRanking.ga = awayRanking.ga?.plus(match.homeExtraScore!!)
        }
        awayRanking.gd = awayRanking.gf!! - awayRanking.ga!!
    }

    fun sortGroupRankings(tournament: Tournament?, group: Group?) {
        if (tournament == null || group?.rankings == null) return

        group.pools = sortRankings(group.rankings, 1)
    }

    fun sortRankings(rankings: List<Ranking?>?, firstPosition: Int): List<Pool?> {
        if (rankings == null) return emptyList()

        val comparatorRanking = ComparatorRanking()
        val rankingList = rankings.sortedWith(comparatorRanking).reversed()

        val poolList: List<Pool?>? = createPoolList(rankingList)
        var nextPosition = firstPosition
        for (pool: Pool? in poolList!!) {
            if (pool == null) break
            pool.position = nextPosition
            nextPosition += pool.rankings?.size!!
            pool.rankings = pool.rankings!!.sortedBy { it!!.team!!.name  }
        }
        return poolList
    }

    private fun createPoolList(rankings: List<Ranking?>?): List<Pool?>? {
        if (rankings == null) return emptyList()

        var poolList: List<Pool?>? = emptyList()
        for (ranking: Ranking? in rankings) {
            if (ranking == null) break
            if (poolList!!.isEmpty()) {
                val newRankings: List<Ranking?> = emptyList<Ranking>().plus(ranking)
                poolList = poolList.plus(Pool(ranking.gf, ranking.ga, ranking.pts, newRankings))
            } else {
                var hasPool = false
                for (pool: Pool? in poolList) {
                    if (pool == null) break
                    if (ranking.isEqualRankings(pool)) {
                        hasPool = true
                        pool.rankings = pool.rankings!!.plus(ranking)
                    }
                }
                if (!hasPool) {
                    val newRankings: List<Ranking?> = emptyList<Ranking>().plus(ranking)
                    poolList = poolList.plus(Pool(ranking.gf, ranking.ga, ranking.pts, newRankings))
                }
            }
        }
        return poolList
    }

    fun processRankings(tournament: Tournament?) {

        if (tournament?.campaigns == null) return

        for (campaign: Campaign? in tournament.campaigns!!) {
            if (campaign?.leagues != null && campaign.multipleLeagues!!) {
                for (league: League? in campaign.leagues!!) {
                    if (league == null) break
                    processLeagueRankings(tournament, campaign, league)
                }
            }
            if (campaign?.stages != null && !campaign.multipleLeagues!!) {
                for (stage: Stage? in campaign.stages!!) {
                    if (stage == null) break
                    if (stage.isRoundRobin()) {
                        processRoundRobinRankings(tournament, campaign, stage)
                    }
                    if (stage.isKnockout()) {
                        processKnockoutRankings(tournament, campaign, stage)
                    }
                }
                campaign.roundRankings = campaign.roundRankings!!.reversed()
            }
        }
    }

    private fun processRoundRobinRankings(tournament: Tournament?, campaign: Campaign?, stage: Stage?) {
        if (tournament == null || campaign?.roundRankings == null || stage?.groups == null) return

        if (stage.championshipRound!!) {
            processChampionshipRoundRankings(tournament, campaign, stage)
            return
        }
        eliminateRoundRobinRankings(tournament, campaign, stage)
        advanceRoundRobinRankings(tournament, campaign, stage)
    }

    private fun processChampionshipRoundRankings(tournament: Tournament?, campaign: Campaign?, stage: Stage?) {
        if (tournament == null || campaign?.roundRankings == null || stage?.groups == null) return

        val roundRanking = initRoundRankings(campaign, stage.name)
        val poolList = stage.groups!![0]!!.pools
        roundRanking.positionPools!![0]!!.pools = poolList
        sortRoundRankings(tournament, roundRanking, roundRanking.positionPools!![0]!!.getRankingList(), stage.eliminateCount)
        highlightChampionshipRoundRankings(roundRanking.positionPools!![0]?.pools!!)
        roundRanking.positionPools!![0]!!.hidePositionPoolDivider = true
    }

    fun highlightChampionshipRoundRankings(poolList: List<Pool?>?) {
        if (poolList == null) return
        for (pool: Pool? in poolList) {
            if (pool == null) break
            pool.highlighted = true
        }
    }

    private fun eliminateRoundRobinRankings(tournament: Tournament?, campaign: Campaign?, stage: Stage?) {
        if (tournament == null || campaign?.roundRankings == null || stage?.groups == null) return

        val roundRanking = initRoundRankings(campaign, stage.name)
        pickRoundRankings(roundRanking, stage, stage.advancement?.eliminated)
        sortRoundRankings(tournament, roundRanking, roundRanking.positionPools!![0]!!.getRankingList(), stage.eliminateCount)
    }

    private fun advanceRoundRobinRankings(tournament: Tournament?, campaign: Campaign?, stage: Stage?) {
        if (tournament == null || campaign?.roundRankings == null || stage?.groups == null) return

        val roundRanking = initRoundRankings(campaign, stage.nextRound)
        pickRoundRankings(roundRanking, stage, stage.advancement?.auto)
    }

    private fun initRoundRankings(campaign: Campaign?, name:String?): RoundRanking {
        if (campaign == null || name == null) return RoundRanking()

        var roundRanking = campaign.roundRankings!!.find { it!!.name!! == name }
        if (roundRanking == null) {
            roundRanking = RoundRanking(name)
            roundRanking.positionPools = roundRanking.positionPools!!.plus(PositionPool())
            campaign.roundRankings = campaign.roundRankings!!.plus(roundRanking)
        }
        return roundRanking
    }

    private fun pickRoundRankings(roundRanking: RoundRanking?, stage: Stage?, positions: List<Int?>?) {
        if (roundRanking == null || stage == null || positions == null) return

        if (roundRanking.positionPools!!.isEmpty()) return
        var poolList = roundRanking.positionPools!![0]!!.pools
        for (group: Group? in stage.groups!!) {
            if (group?.pools.isNullOrEmpty()) break
            for (pos: Int? in positions) {
                if (pos == null ) break
                val pool = group?.pools!!.find { it!!.position == pos }
                if (pool != null) {
                    poolList = poolList!!.plus(pool.copyPool())
                }
            }
        }
        roundRanking.positionPools!![0]!!.pools = poolList
    }

    private fun sortRoundRankings(tournament: Tournament?, roundRanking: RoundRanking?, rankingList: List<Ranking?>?, eliminateCount: Int?) {
        if (tournament == null || roundRanking == null || rankingList == null || rankingList.isEmpty()) return

        val firstPosition = if (eliminateCount != null) eliminateCount + 1 else 1
        val poolList = sortRankings(rankingList, firstPosition)
        roundRanking.positionPools!![0]!!.pools = poolList
        roundRanking.processed = true
    }

    private fun processKnockoutRankings(tournament: Tournament?, campaign: Campaign?, stage: Stage?) {
        if (tournament == null || campaign == null || stage == null) return
        for (round: Round? in stage.rounds!!) {
            if (round == null) break
            val roundRanking = initRoundRankings(campaign, round.name)
            val thirdPlaceRanking = if (round.name == SEMI_FINALS) initRoundRankings(campaign, THIRD_PLACE) else null
            val nextRoundRanking = initRoundRankings(campaign, round.nextRound)
            val rankingList = roundRanking.positionPools!![0]!!.getRankingList()
            val knockoutRankingList = executeKnockoutRound(tournament, rankingList, round)
            val eliminatedRankingList: List<Ranking?>? = knockoutRankingList!!.eliminated
            val advancedRankingList: List<Ranking?>? = knockoutRankingList.advanced
            when (round.name) {
                FINAL -> {
                    copyPoolPosition(roundRanking)
                    roundRanking.positionPools!![0]!!.hidePositionPoolDivider = true
                    roundRanking.processed = true
                }
                THIRD_PLACE -> {
                    copyPoolPosition(roundRanking)
                    roundRanking.processed = true
                }
                SEMI_FINALS -> {
                    thirdPlaceRanking!!.positionPools!![0]!!.pools = createPoolList(eliminatedRankingList!!)
                    if (nextRoundRanking.positionPools!!.isNotEmpty()) {
                        nextRoundRanking.positionPools!![0]!!.pools = createPoolList(advancedRankingList!!)
                    }
                }
                else -> {
                    sortRoundRankings(tournament, roundRanking, eliminatedRankingList, round.eliminateCount)
                    if (nextRoundRanking.positionPools!!.isNotEmpty()) {
                        nextRoundRanking.positionPools!![0]!!.pools = createPoolList(advancedRankingList!!)
                    }
                }
            }
        }
    }

    private fun executeKnockoutRound(tournament: Tournament?, rankingList: List<Ranking?>?, round: Round?): KnockoutRankingList? {
        if (tournament == null || round == null) return null

        var eliminatedRankingList: List<Ranking?>? = emptyList()
        var advancedRankingList: List<Ranking?>? = emptyList()
        for (match: Match? in round.matches!!) {
            if (match == null) break
            val homeRanking: Ranking? = rankingList!!.find { it!!.team!!.name == match.homeTeam!!.name }
            val awayRanking: Ranking? = rankingList.find { it!!.team!!.name == match.awayTeam!!.name }
            if (homeRanking == null || awayRanking == null) break
            accumulateRankings(tournament, tournament.pointsForWin, homeRanking, awayRanking, match)
            if (match.getTeamNameWin() == homeRanking.team!!.name) {
                eliminatedRankingList = eliminatedRankingList!!.plus(awayRanking)
                advancedRankingList = advancedRankingList!!.plus(homeRanking)
                if (round.name == THIRD_PLACE) {
                    homeRanking.position = 3
                    awayRanking.position = 4
                }
                if (round.name == FINAL) {
                    homeRanking.position = 1
                    awayRanking.position = 2
                }
            }
            if (match.getTeamNameWin() == awayRanking.team!!.name) {
                eliminatedRankingList = eliminatedRankingList!!.plus(homeRanking)
                advancedRankingList = advancedRankingList!!.plus(awayRanking)
                if (round.name == THIRD_PLACE) {
                    homeRanking.position = 4
                    awayRanking.position = 3
                }
                if (round.name == FINAL) {
                    homeRanking.position = 2
                    awayRanking.position = 1
                }
            }
        }
        return KnockoutRankingList(eliminatedRankingList, advancedRankingList)
    }

    private fun copyPoolPosition(roundRanking: RoundRanking?) {
        if (roundRanking?.positionPools == null || roundRanking.positionPools!!.isEmpty()
            || roundRanking.positionPools!![0]?.pools == null) return
        for (pool: Pool? in roundRanking.positionPools!![0]?.pools!!) {
            if (pool == null) break
            pool.position = if (pool.rankings.isNullOrEmpty()) null else pool.rankings!![0]!!.position
            pool.highlighted = true
        }
    }

    private fun processLeagueRankings(tournament: Tournament?, campaign: Campaign?, league: League?) {
        if (tournament == null || campaign == null || league?.stages == null) return

        val isTopLeague = campaign.roundRankings!!.isEmpty()
        if (isTopLeague) {
            campaign.roundRankings = campaign.roundRankings!!.plus(RoundRanking(FINAL))
            campaign.roundRankings = campaign.roundRankings!!.plus(RoundRanking(THIRD_PLACE))
        }

        val roundRanking = RoundRanking(league.name)
        campaign.roundRankings = campaign.roundRankings!!.plus(roundRanking)
        for (stage: Stage? in league.stages!!) {
            if (stage?.groups == null) break
            if (stage.isRoundRobin()) {
                for (group: Group? in stage.groups!!) {
                    if (group?.pools == null) break
                    for (pool: Pool? in group.pools!!) {
                        if (pool?.rankings == null) break
                        var positionPool = roundRanking.positionPools!!.find { it!!.position == pool.position }
                        if (positionPool == null) {
                            positionPool = PositionPool(pool.position)
                            roundRanking.positionPools = roundRanking.positionPools!!.plus(positionPool)
                        }
                        positionPool.pools = positionPool.pools!!.plus(pool.copyPool())
                    }
                }
                var standingCount = league.standingCount?.plus(1)
                for (positionPool: PositionPool? in roundRanking.positionPools!!) {
                    if (positionPool?.pools == null) break
                    val rankingList = positionPool.getRankingList()
                    positionPool.pools = sortRankings(rankingList, standingCount!!)
                    standingCount += rankingList!!.size
                }
                roundRanking.processed = roundRanking.positionPools!!.isNotEmpty()
            }
            if (stage.isKnockout() && isTopLeague) {
                processLeagueFinalRankings(tournament, campaign, league, stage)
            }
        }
    }

    private fun processLeagueFinalRankings(tournament: Tournament?, campaign: Campaign?, league: League?, stage: Stage?) {
        if (tournament == null || campaign == null || league == null || stage?.rounds == null) return

        val finalRoundRanking = campaign.roundRankings!!.find { it!!.name == FINAL }
        val finalPositionPool = PositionPool()
        finalRoundRanking!!.positionPools = finalRoundRanking.positionPools!!.plus(finalPositionPool)

        val thirdPlaceRoundRanking = campaign.roundRankings!!.find { it!!.name == THIRD_PLACE }
        val thirdPlacePositionPool = PositionPool()
        thirdPlaceRoundRanking!!.positionPools = thirdPlaceRoundRanking.positionPools!!.plus(thirdPlacePositionPool)

        for (round: Round? in stage.rounds!!) {
            if (round?.matches == null) break
            when (round.name) {
                SEMI_FINALS -> {
                    val topLeagueRoundRanking = campaign.roundRankings!!.find { it!!.name == league.name }
                    val topLeagueFirstPositionPool = topLeagueRoundRanking!!.positionPools!!.find { it!!.position == 1 }
                    val knockoutRankingList = executeKnockoutRound(tournament, topLeagueFirstPositionPool!!.getRankingList(), round)
                    val eliminatedRankingList: List<Ranking?>? = knockoutRankingList!!.eliminated
                    val advancedRankingList: List<Ranking?>? = knockoutRankingList.advanced
                    thirdPlacePositionPool.pools = createPoolList(eliminatedRankingList)
                    finalPositionPool.pools = createPoolList(advancedRankingList)
                    topLeagueRoundRanking.positionPools = topLeagueRoundRanking.positionPools!!.filter { it!!.position != 1 }
                }
                THIRD_PLACE -> {
                    val knockoutRankingList = executeKnockoutRound(tournament, thirdPlacePositionPool.getRankingList(), round)
                    val eliminatedRankingList: List<Ranking?>? = knockoutRankingList!!.eliminated
                    val advancedRankingList: List<Ranking?>? = knockoutRankingList.advanced
                    thirdPlacePositionPool.pools = createPoolList(advancedRankingList!!.plus(eliminatedRankingList!!.toTypedArray()))
                    copyPoolPosition(thirdPlaceRoundRanking)
                    thirdPlaceRoundRanking.processed = true
                }
                FINAL -> {
                    val knockoutRankingList = executeKnockoutRound(tournament, finalPositionPool.getRankingList(), round)
                    val eliminatedRankingList: List<Ranking?>? = knockoutRankingList!!.eliminated
                    val advancedRankingList: List<Ranking?>? = knockoutRankingList.advanced
                    finalPositionPool.pools = createPoolList(advancedRankingList!!.plus(eliminatedRankingList!!.toTypedArray()))
                    copyPoolPosition(finalRoundRanking)
                    finalPositionPool.hidePositionPoolDivider = true
                    finalRoundRanking.processed = true
                }
                else -> {}
            }
        }
    }

    fun renderNumber(context: Context?, field: Int?, textView: TextView, gdField: Boolean = false) {
        if (field != null) {
            textView.text = if (gdField && field > 0) context!!.getString(R.string.positive, field.toString()) else field.toString()
        } else {
            textView.text = context!!.getString(R.string.minus)
        }
    }
}

class ComparatorRanking: Comparator<Ranking?>{
    override fun compare(o1: Ranking?, o2: Ranking?): Int {
        if (o1 == null || o2 == null) {
            return 0;
        }
        when {
            o1.pts!! > o2.pts!! -> return 1
            o1.pts!! < o2.pts!! -> return -1
            o1.pts!! == o2.pts!! ->
                when {
                    o1.gd!! > o2.gd!! -> return 1
                    o1.gd!! < o2.gd!! -> return -1
                    o1.gd!! == o2.gd!! -> when {
                        o1.gf!! > o2.gf!! -> return 1
                        o1.gf!! < o2.gf!! -> return -1
                    }
                }
        }
        return 0
    }
}
