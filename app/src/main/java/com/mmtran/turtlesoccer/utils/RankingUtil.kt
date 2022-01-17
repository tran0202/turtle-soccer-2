package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.widget.TextView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.*

object RankingUtil {

    private fun newRanking(team: Team?): Ranking {
        return Ranking(null, team, 0,0,0,0,0,0,0,0)
    }

    fun initRankings(group: Group?, nationList: List<Nation?>?, teamList: List<Team?>?) {
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

    fun accumulateRankings(tournament: Tournament?, group: Group?, match: Match?) {

        if (tournament == null || group == null || match == null || match.homeTeam == null || match.awayTeam == null
            || match.homeScore == null || match.awayScore == null) return

        val homeRanking = group.rankings!!.find { it!!.team!!.name.equals(match.homeTeam!!.name) }
        val awayRanking = group.rankings!!.find { it!!.team!!.name.equals(match.awayTeam!!.name) }
        if (homeRanking == null || awayRanking == null) return

        homeRanking.mp = homeRanking.mp?.plus(1)
        awayRanking.mp = awayRanking.mp?.plus(1)
        when {
            match.homeScore!! > match.awayScore!! -> {
                homeRanking.w = homeRanking.w?.plus(1)
                homeRanking.pts = homeRanking.pts?.plus(tournament.pointsForWin!!)
                awayRanking.l = awayRanking.l?.plus(1)
            }
            match.homeScore!! < match.awayScore!! -> {
                homeRanking.l = homeRanking.l?.plus(1)
                awayRanking.w = awayRanking.w?.plus(1)
                awayRanking.pts = awayRanking.pts?.plus(tournament.pointsForWin!!)
            }
            else -> {
                homeRanking.d = homeRanking.d?.plus(1)
                homeRanking.pts = homeRanking.pts?.plus(1)
                awayRanking.d = awayRanking.d?.plus(1)
                awayRanking.pts = awayRanking.pts?.plus(1)
            }
        }
        homeRanking.gf = homeRanking.gf?.plus(match.homeScore!!)
        homeRanking.ga = homeRanking.ga?.plus(match.awayScore!!)
        homeRanking.gd = homeRanking.gd?.plus(homeRanking.gf!! - homeRanking.ga!!)
        awayRanking.gf = awayRanking.gf?.plus(match.awayScore!!)
        awayRanking.ga = awayRanking.ga?.plus(match.homeScore!!)
        awayRanking.gd = awayRanking.gd?.plus(awayRanking.gf!! - awayRanking.ga!!)
    }

    fun sortRankings(tournament: Tournament?, group: Group?) {
        if (tournament == null || group?.rankings == null) return
        val comparatorRanking = ComparatorRanking()
        group.rankings = group.rankings!!.sortedWith(comparatorRanking).reversed()

        var poolList: List<Pool?>? = emptyList()
        for (ranking: Ranking? in group.rankings!!) {
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
        var nextPosition = 1
        for (pool: Pool? in poolList!!) {
            if (pool == null) break
            pool.position = nextPosition
            nextPosition += pool.rankings?.size!!
            pool.rankings = pool.rankings!!.sortedBy { it!!.team!!.name  }
        }
        group.pools = poolList
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
        return if (o1.pts!! > o2.pts!!) 1 else -1
    }
}
