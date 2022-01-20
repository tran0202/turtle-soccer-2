package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.graphics.Paint
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentMatchBinding
import com.mmtran.turtlesoccer.databinding.FragmentTeamFlagBinding
import com.mmtran.turtlesoccer.models.*

object MatchUtil {

    fun processLeagueMatches(tournament: Tournament?, campaign: Campaign?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (campaign?.leagues == null) return

        campaign.stages = campaign.stages!!.plus((Stage("Matchdays", "roundrobin")))

        for (league: League? in campaign.leagues!!) {
            if (league == null) break
            val newLeagueStage = Stage(league.name, "roundrobin")
            campaign.leagueStages = campaign.leagueStages!!.plus(newLeagueStage)
            for (stage: Stage? in league.stages!!) {
                if (stage == null) break
                if (stage.isRoundRobin()) {
                    newLeagueStage.groups = stage.groups
                    for (group: Group? in stage.groups!!) {
                        if (group == null) break
                        RankingUtil.initGroupRankings(group, nationList, teamList)
                        for (matchday: Matchday? in group.matchdays!!) {
                            if (matchday == null) break
                            var matches: List<Match?>? = emptyList()
                            for (match: Match? in matchday.matches!!) {
                                if (match == null) break
                                processMatch(match, nationList, teamList)
                                match.groupName = group.name
                                matches = matches!!.plus(match)
                                RankingUtil.accumulateGroupRankings(tournament, group, match)
                            }
                            val round = campaign.stages!![0]!!.rounds!!.find { it!!.name.equals(matchday.name) }
                            if (round == null) {
                                val newRound = Round(matchday.name)
                                newRound.paths = newRound.paths?.plus(Path("", true))
                                newRound.paths!![0]!!.matchdayList = createMatchdayList(matches, league.name)
                                campaign.stages!![0]!!.rounds = campaign.stages!![0]!!.rounds!!.plus(newRound)
                            } else {
                                round.paths = round.paths?.plus(Path("", true))
                                round.paths!![0]!!.matchdayList = mergeMatchdayList(round.paths!![0]!!.matchdayList, matches, league.name)
                            }
                        }
                        RankingUtil.sortGroupRankings(tournament, group)
                    }
                }
                if (stage.isKnockout()) {
                    campaign.stages = campaign.stages!!.plus(Stage(stage.name!!, stage.type!!))
                }
            }
        }
    }

    fun processStageMatches(tournament: Tournament?, campaign: Campaign?, stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage?.rounds == null) return

        for (round: Round? in stage.rounds!!) {
            if (round == null) break
            round.hideRoundName = stage.hideRoundName()
            for (path: Path? in round.paths!!) {
                if (path == null) break
                path.hidePathName = round.hidePathName()
                for (matchday: Matchday? in path.matchdayList!!) {
                    if (matchday == null) break
                    for (league: League? in matchday.leagues!!) {
                        if (league == null) break
                        league.hideLeagueName = matchday.hideLeagueName()
                    }
                }
            }
        }
        if (stage.isRoundRobin()) {
            processRoundRobinMatches(tournament, stage, nationList, teamList)
        }
        if (stage.isKnockout()) {
            processKnockoutMatches(tournament, stage, nationList, teamList)
        }
    }

    private fun processRoundRobinMatches(tournament: Tournament?, stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage?.groups == null) return

        if (!stage.multipleMatchdays!!) {
            var matches: List<Match?>? = emptyList()
            for (group: Group? in stage.groups!!) {
                if (group == null) break
                RankingUtil.initGroupRankings(group, nationList, teamList)
                group.hideGroupName = stage.hideGroupName()
                for (match: Match? in group.matches!!) {
                    if (match == null) break
                    processMatch(match, nationList, teamList)
                    match.groupName = group.name
                    matches = matches!!.plus(match)
                    RankingUtil.accumulateGroupRankings(tournament, group, match)
                }
                RankingUtil.sortGroupRankings(tournament, group)
            }
            stage.rounds = emptyList()
            stage.rounds = stage.rounds!!.plus(Round("", true))
            stage.rounds!![0]!!.paths = stage.rounds!![0]!!.paths?.plus(Path("", true))
            stage.rounds!![0]!!.paths!![0]!!.matchdayList = createMatchdayList(matches, "")
        } else {
            var roundList: List<Round?>? = emptyList()
            for (group: Group? in stage.groups!!) {
                if (group == null) break
                RankingUtil.initGroupRankings(group, nationList, teamList)
                group.hideGroupName = stage.hideGroupName()
                for (matchday: Matchday? in group.matchdays!!) {
                    if (matchday == null) break
                    var matches: List<Match?>? = emptyList()
                    for (match: Match? in matchday.matches!!) {
                        if (match == null) break
                        processMatch(match, nationList, teamList)
                        match.groupName = group.name
                        matches = matches!!.plus(match)
                        RankingUtil.accumulateGroupRankings(tournament, group, match)
                    }
                    val round = roundList!!.find { it!!.name.equals(matchday.name) }
                    if (round == null) {
                        val newRound = Round(matchday.name)
                        newRound.paths = newRound.paths?.plus(Path("", true))
                        newRound.matches = matches
                        roundList = roundList.plus(newRound)
                    } else {
                        round.matches = round.matches!!.plus(matches!!.toTypedArray())
                    }
                }
                RankingUtil.sortGroupRankings(tournament, group)
            }
            for (round: Round? in roundList!!) {
                if (round == null) break
                if (round.paths != null && round.paths!!.isNotEmpty()) {
                    round.paths!![0]!!.matchdayList = createMatchdayList(round.matches, "")
                }
            }
            stage.rounds = roundList
        }
    }

    private fun processKnockoutMatches(tournament: Tournament?, stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage?.rounds == null) return

        for (round: Round? in stage.rounds!!) {
            if (round == null) break
            if (!round.multiplePaths!!) {
                for (match: Match? in round.matches!!) {
                    if (match == null) break
                    processMatch(match, nationList, teamList)
                }
                var pathList: List<Path?>? = emptyList()
                pathList = pathList?.plus(Path("", true))
                round.paths = pathList
                round.paths!![0]!!.matchdayList = createMatchdayList(round.matches, "")
            } else {
                for (path: Path? in round.paths!!) {
                    if (path == null) break
                    for (match: Match? in path.matches!!) {
                        if (match == null) break
                        processMatch(match, nationList, teamList)
                    }
                    path.matchdayList = createMatchdayList(path.matches,"")
                }
            }
        }
    }

    private fun processMatch(match: Match?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (match == null) return

        var team = TeamUtil.getTeam(match.homeTeam?.id, nationList, teamList)
        if (team != null) {
            match.homeTeam = team
        }
        team = TeamUtil.getTeam(match.awayTeam?.id, nationList, teamList)
        if (team != null) {
            match.awayTeam = team
        }
        team = TeamUtil.getTeam(match.leg2HomeTeam?.id, nationList, teamList)
        if (team != null) {
            match.leg2HomeTeam = team
        }
        team = TeamUtil.getTeam(match.leg2AwayTeam?.id, nationList, teamList)
        if (team != null) {
            match.leg2AwayTeam = team
        }
        team = TeamUtil.getTeam(match.replayHomeTeam?.id, nationList, teamList)
        if (team != null) {
            match.replayHomeTeam = team
        }
        team = TeamUtil.getTeam(match.replayAwayTeam?.id, nationList, teamList)
        if (team != null) {
            match.replayAwayTeam = team
        }
    }

    private fun createMatchdayList(matches: List<Match?>?, leagueName: String?): List<Matchday?>? {

        val mdl: List<Matchday?> = emptyList()
        if (matches == null) return mdl

        return mergeMatchdayList(mdl, matches, leagueName)
    }

    private fun mergeMatchdayList(matchdays: List<Matchday?>?, matches: List<Match?>?, leagueName: String?): List<Matchday?>? {

        var mdl: List<Matchday?>? = matchdays
        if (matches == null) return mdl

        val hideLeagueName = !(leagueName != null && leagueName.isNotEmpty())

        for (match: Match? in matches) {
            if (match == null) break
            val matchday = mdl!!.find { it!!.name.equals(match.date) }
            if (matchday == null) {
                val newLeague = League(leagueName, hideLeagueName)
                newLeague.matches = newLeague.matches!!.plus(match)
                val newMatchday = Matchday(match.date)
                newMatchday.leagues = newMatchday.leagues!!.plus(newLeague)
                mdl = mdl.plus(newMatchday)
            } else {
                val league = matchday.leagues!!.find { it!!.name.equals(leagueName) }
                if (league == null) {
                    val newLeague = League(leagueName, hideLeagueName)
                    newLeague.matches = newLeague.matches!!.plus(match)
                    matchday.leagues = matchday.leagues!!.plus(newLeague)
                } else {
                    league.matches = league.matches!!.plus(match)
                }
            }
        }
        for (matchday: Matchday? in mdl!!) {
            if (matchday == null) break
            for (league: League? in matchday.leagues!!) {
                if (league == null) break
                league.matches = league.matches!!.sortedBy { it!!.time }
            }
        }
        return mdl.sortedBy { it!!.name }
    }

    fun renderLegDivider(match: Match?, view: View) {

        if (match == null) return

        if (!match.multipleLegs!!) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    fun renderLeg2Row(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.multipleLegs!!) {
            binding.root.visibility = View.GONE
        } else {
            binding.root.visibility = View.VISIBLE
        }
    }

    fun renderReplayDivider(match: Match?, view: View) {

        if (match == null) return

        if (!match.replayMatch!!) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    fun renderReplayRow(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.replayMatch!!) {
            binding.root.visibility = View.GONE
        } else {
            binding.root.visibility = View.VISIBLE
        }
    }

    fun setMatchMinHeight(context: Context?, match: Match?, binding: FragmentMatchBinding) {
        if (match == null) return
        val factor: Float = context!!.resources.displayMetrics.density
        binding.timeColumn.minimumHeight = (getMatchMinHeight(match) * factor).toInt()
    }

    private fun getMatchMinHeight(match: Match?): Int {
        if (match?.homeTeam == null) return 0
        if (match.homeTeam!!.isClub()) return 90
        return if (match.groupName != null || match.disqualifiedMatch!!
            || match.homeWithdrew!! || match.awayWithdrew!!) 70 else 50
    }

    fun renderLeg1Count(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderLegCount(context, match, binding, match.multipleLegs, R.string.leg1)
    }

    fun renderLeg2Count(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderLegCount(context, match, binding, match.multipleLegs, R.string.leg2)
    }

    fun renderReplayCount(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderLegCount(context, match, binding, match.replayMatch, R.string.replay)
    }

    private fun renderLegCount(context: Context?, match: Match?, binding: FragmentMatchBinding, flag: Boolean?, count: Int?) {

        if (match == null) return

        if (!flag!!) {
            binding.legCount.visibility = View.GONE
        } else {
            binding.legCount.visibility = View.VISIBLE
            renderField(context!!.getString(count!!), binding.legCount)
            binding.legCount.paintFlags = binding.legCount.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    fun renderLeg1Date(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        binding.date.visibility = View.GONE
    }

    fun renderLeg2Date(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        renderMatchDate(match.leg2Date, binding, match.multipleLegs)
    }

    fun renderReplayDate(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        renderMatchDate(match.replayDate, binding, match.replayMatch)
    }

    private fun renderMatchDate(value: String?, binding: FragmentMatchBinding, flag: Boolean?) {

        if (value == null) return

        if (!flag!!) {
            binding.date.visibility = View.GONE
        } else {
            binding.date.visibility = View.VISIBLE
            renderField(CommonUtil.renderShortDate(value), binding.date)
            binding.date.paintFlags = binding.date.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    fun renderLeg1Time(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.time, binding.time)
    }

    fun renderLeg2Time(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.leg2Time, binding.time)
    }

    fun renderReplayTime(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.replayTime, binding.time)
    }

    fun renderGroupName(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.groupName, binding.groupName)
    }

    fun renderLeg1City(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.city, binding.city)
    }

    fun renderLeg2City(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.leg2City, binding.city)
    }

    fun renderReplayCity(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderField(match.replayCity, binding.city)
    }

    fun renderLeg1HomeAwarded(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.homeAwarded!!) {
            binding.homeAwdText.visibility = View.GONE
        } else {
            binding.homeAwdText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1AwayAwarded(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.awayAwarded!!) {
            binding.awayAwdText.visibility = View.GONE
        } else {
            binding.awayAwdText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1HomeDisqualified(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.homeDisqualified!!) {
            binding.homeDqText.visibility = View.GONE
        } else {
            binding.homeDqText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1AwayDisqualified(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.awayDisqualified!!) {
            binding.awayDqText.visibility = View.GONE
        } else {
            binding.awayDqText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1HomeWithdrew(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.homeWithdrew!!) {
            binding.homeWithdrewText.visibility = View.GONE
        } else {
            binding.homeWithdrewText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1AwayWithdrew(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (!match.awayWithdrew!!) {
            binding.awayWithdrewText.visibility = View.GONE
        } else {
            binding.awayWithdrewText.visibility = View.VISIBLE
        }
    }

    fun renderLeg1HomeTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.homeTeam, binding.homeTeam, match.isLeg1HomeEmphasizedName())
    }

    fun renderLeg1AwayTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.awayTeam, binding.awayTeam, match.isLeg1AwayEmphasizedName())
    }

    fun renderLeg2HomeTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.leg2HomeTeam, binding.homeTeam, match.isLeg2HomeEmphasizedName())
    }

    fun renderLeg2AwayTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.leg2AwayTeam, binding.awayTeam, match.isLeg2AwayEmphasizedName())
    }

    fun renderReplayHomeTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.replayHomeTeam, binding.homeTeam, match.isReplayHomeEmphasizedName())
    }

    fun renderReplayAwayTeamName(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamName(context, match.replayAwayTeam, binding.awayTeam, match.isReplayAwayEmphasizedName())
    }

    private fun renderTeamName(context: Context?, team: Team?, textView: TextView, emphasizedName: Boolean) {

        if (team == null) return

        renderField(team.name, textView)
        if (emphasizedName) {
            textView.setTextColor(ContextCompat.getColor(context!!, R.color.black))
        } else {
            textView.setTextColor(ContextCompat.getColor(context!!, R.color.gray3))
        }
    }

    fun renderLeg1HomeTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match?.homeTeam == null) return
        renderTeamFlag(context, match.homeTeam!!, binding.homeFlag )
    }

    fun renderLeg1AwayTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match?.awayTeam == null) return
        renderTeamFlag(context, match.awayTeam!!, binding.awayFlag )
    }

    fun renderLeg2HomeTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamFlag(context, match.leg2HomeTeam!!, binding.homeFlag )
    }

    fun renderLeg2AwayTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamFlag(context, match.leg2AwayTeam!!, binding.awayFlag )
    }

    fun renderReplayHomeTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamFlag(context, match.replayHomeTeam!!, binding.homeFlag )
    }

    fun renderReplayAwayTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderTeamFlag(context, match.replayAwayTeam!!, binding.awayFlag )
    }

    private fun renderTeamFlag(context: Context?, team: Team?, binding: FragmentTeamFlagBinding?) {

        if (team == null) return
        CommonUtil.renderTeamFlag(context, team, binding!!.clubLogo, binding.flag)
    }

    fun renderLeg1ScoreColumn(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.isExtraTimeMatch()) {
            binding.score.visibility = View.VISIBLE
            renderLeg1RegularScore(context, match, binding)
            binding.extraScore.visibility = View.GONE
            binding.extraScoreText.visibility = View.GONE
        } else {
            binding.score.visibility = View.GONE
            binding.extraScore.visibility = View.VISIBLE
            binding.extraScoreText.visibility = View.VISIBLE
            renderLeg1ExtraScore(context, match, binding)
        }
        if (!match.awardedMatch!!) {
            binding.awdText.visibility = View.GONE
        } else {
            binding.awdText.visibility = View.VISIBLE
        }
        if (!match.byeMatch!!) {
            binding.byeText.visibility = View.GONE
        } else {
            binding.byeText.visibility = View.VISIBLE
        }
        if (!match.homeWalkover!! && !match.awayWalkover!!) {
            binding.woText.visibility = View.GONE
        } else {
            binding.woText.visibility = View.VISIBLE
        }
        if (!match.cancelledMatch!!) {
            binding.cancelledText.visibility = View.GONE
        } else {
            binding.cancelledText.visibility = View.VISIBLE
        }
        if (!match.postponedMatch!!) {
            binding.postponedText.visibility = View.GONE
        } else {
            binding.postponedText.visibility = View.VISIBLE
        }
    }

    fun renderLeg2ScoreColumn(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.isLeg2ExtraTimeMatch()) {
            binding.score.visibility = View.VISIBLE
            renderLeg2RegularScore(context, match, binding)
            binding.extraScore.visibility = View.GONE
            binding.extraScoreText.visibility = View.GONE
        } else {
            binding.score.visibility = View.GONE
            binding.extraScore.visibility = View.VISIBLE
            binding.extraScoreText.visibility = View.VISIBLE
            renderLeg2ExtraScore(context, match, binding)
        }
        if (!match.leg2AwardedMatch!!) {
            binding.awdText.visibility = View.GONE
        } else {
            binding.awdText.visibility = View.VISIBLE
        }
    }

    fun renderReplayScoreColumn(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.isReplayExtraTimeMatch()) {
            binding.score.visibility = View.VISIBLE
            renderReplayRegularScore(context, match, binding)
            binding.extraScore.visibility = View.GONE
        } else {
            binding.score.visibility = View.GONE
            binding.extraScore.visibility = View.GONE
        }
    }

    private fun renderLeg1RegularScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.homeScore(), match.awayScore(), binding.score)
    }

    private fun renderLeg1ExtraScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.homeAfterExtraTimeScore(), match.awayAfterExtraTimeScore(), binding.extraScore)
    }

    private fun renderLeg2RegularScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.leg2HomeScore(), match.leg2AwayScore(), binding.score)
    }

    private fun renderLeg2ExtraScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.leg2HomeAfterExtraTimeScore(), match.leg2AwayAfterExtraTimeScore(), binding.extraScore)
    }

    private fun renderReplayRegularScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.replayHomeScore, match.replayAwayScore, binding.score)
    }

    private fun renderReplayExtraScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.replayHomeAfterExtraTimeScore(), match.replayAwayAfterExtraTimeScore(), binding.extraScore)
    }

    private fun renderScore(context: Context?, score1: Int?, score2: Int?, textView: TextView) {
        if (score1 == null || score2 == null) {
            renderField(null, textView)
        } else {
            renderField(context!!.getString(R.string.score, score1.toString(), score2.toString()), textView)
        }
    }

    fun renderLeg1AggregateScore(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        binding.aggregateScoreRow.visibility = View.GONE
    }

    fun renderLeg2AggregateScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (!match.multipleLegs!!) {
            binding.aggregateScoreRow.visibility = View.GONE
        } else {
            binding.aggregateScoreRow.visibility = View.VISIBLE
            binding.aggregateScore.text = Html.fromHtml(
                context!!.getString(R.string.aggregate_footnote,
                context.getString(R.string.score, match.aggregateHomeScore().toString(), match.aggregateAwayScore().toString())),
                0)
        }
    }

    fun renderReplayAggregateScore(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        binding.aggregateScoreRow.visibility = View.GONE
    }

    private fun getPenaltyScore(context: Context?, match: Match?): String? {

        if (match == null) return null
        return if (!match.multipleLegs!!) {
            context!!.getString(R.string.score, match.homePenaltyScore!!.toString(), match.awayPenaltyScore!!.toString())
        } else {
            context!!.getString(R.string.score, match.leg2HomePenaltyScore!!.toString(), match.leg2AwayPenaltyScore!!.toString())
        }
    }

    fun renderLeg1ExtraTimeFootnote(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (match.replayMatch!! || match.voidMatch!!) {
            binding.extraTimeFootnoteRow.visibility = View.GONE
            return
        }

        if (match.isExtraTimeMatch() || match.isPenaltyMatch()) {
            binding.extraTimeFootnoteRow.visibility = View.VISIBLE
            if (!match.isPenaltyMatch()) {
                binding.extraTimeFootnote.text = Html.fromHtml(
                    context!!.getString(R.string.aet_footnote, match.getTeamNameWin()),
                    0)
            } else {
                binding.extraTimeFootnote.text = Html.fromHtml(
                    context!!.getString(R.string.penalty_footnote, match.getTeamNameWin(), getPenaltyScore(context, match)),
                    0)
            }
        } else {
            binding.extraTimeFootnoteRow.visibility = View.GONE
        }
    }

    fun renderLeg2ExtraTimeFootnote(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        if (match.multipleLegs!! && match.isAggregateScoreTie()) {
            binding.extraTimeFootnoteRow.visibility = View.VISIBLE
            if (match.leg2HomeExtraScore == null) {
                binding.extraTimeFootnote.text = Html.fromHtml(
                    context!!.getString(R.string.away_goals_footnote, match.getTeamNameWin()),
                    0)
            } else if (!match.isLeg2PenaltyMatch()) {
                binding.extraTimeFootnote.text = Html.fromHtml(
                    context!!.getString(R.string.away_goals_aet_footnote, match.getTeamNameWin()),
                    0)
            } else {
                binding.extraTimeFootnote.text = Html.fromHtml(
                    context!!.getString(R.string.penalty_footnote, match.getTeamNameWin(), getPenaltyScore(context, match)),
                    0)
            }
        } else {
            binding.extraTimeFootnoteRow.visibility = View.GONE
        }
    }

    fun renderReplayExtraTimeFootnote(match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return

        binding.extraTimeFootnoteRow.visibility = View.GONE
    }

    private fun renderField(field: String?, textView: TextView) {
        if (field != null) {
            textView.visibility = View.VISIBLE
            textView.text = field
        } else {
            textView.visibility = View.GONE
        }
    }
}
