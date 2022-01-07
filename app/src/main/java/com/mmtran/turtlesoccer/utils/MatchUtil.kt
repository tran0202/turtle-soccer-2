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

    fun processStage(stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage == null) return

        if (stage.rounds != null) {
            for (round: Round? in stage.rounds!!) {
                round!!.hideRoundName = stage.hideRoundName()
                for (path: Path? in round.paths!!) {
                    path!!.hidePathName = round.hidePathName()
                }
            }
        }
        if (stage.isRoundRobin()) {
            processRoundRobin(stage, nationList, teamList)
        }
        if (stage.isKnockout()) {
            processKnockout(stage, nationList, teamList)
        }
    }

    private fun processRoundRobin(stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage == null) return

        if (stage.groups != null) {
            if (!stage.multipleMatchdays!!) {
                var matches: List<Match?>? = emptyList()
                for (group: Group? in stage.groups!!) {
                    if (group == null) break
                    for (match: Match? in group.matches!!) {
                        if (match == null) break
                        processMatch(match, nationList, teamList)
                        match.groupName = group.name
                        matches = matches!!.plus(match)
                    }
                }
                stage.rounds = emptyList()
                stage.rounds = stage.rounds!!.plus(Round("", true))
                stage.rounds!![0]!!.paths = stage.rounds!![0]!!.paths?.plus(Path("", true))
                stage.rounds!![0]!!.paths!![0]!!.matchdayList = createMatchdayList(matches)
            } else {
                var roundList: List<Round?>? = emptyList()
                for (group: Group? in stage.groups!!) {
                    if (group == null) break
                    for (matchday: Matchday? in group.matchdays!!) {
                        if (matchday == null) break
                        var matches: List<Match?>? = emptyList()
                        for (match: Match? in matchday.matches!!) {
                            if (match == null) break
                            processMatch(match, nationList, teamList)
                            match.groupName = group.name
                            matches = matches!!.plus(match)
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
                }
                for (round: Round? in roundList!!) {
                    if (round == null) break
                    if (round.paths != null && round.paths!!.isNotEmpty()) {
                        round.paths!![0]!!.matchdayList = createMatchdayList(round.matches)
                    }
                }
                stage.rounds = roundList
            }
        }
    }

    private fun processKnockout(stage: Stage?, nationList: List<Nation?>?, teamList: List<Team?>?) {

        if (stage == null) return

        if (stage.rounds != null) {
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
                    round.paths!![0]!!.matchdayList = createMatchdayList(round.matches)
                } else {
                    for (path: Path? in round.paths!!) {
                        if (path == null) break
                        for (match: Match? in path.matches!!) {
                            if (match == null) break
                            processMatch(match, nationList, teamList)
                        }
                        path.matchdayList = createMatchdayList(path.matches)
                    }
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
    }

    private fun createMatchdayList(matches: List<Match?>?): List<Matchday?>? {

        var mdl: List<Matchday?>? = emptyList()
        if (matches == null) return mdl

        for (match: Match? in matches) {
            if (match != null) {
                val matchday = mdl!!.find { it!!.name.equals(match.date) }
                if (matchday == null) {
                    val newMatchday = Matchday(match.date)
                    newMatchday.matches = newMatchday.matches!!.plus(match)
                    mdl = mdl.plus(newMatchday)
                } else {
                    matchday.matches = matchday.matches!!.plus(match)
                }
            }
        }
        for (matchday: Matchday? in mdl!!) {
            if (matchday == null) break
            matchday.matches = matchday.matches!!.sortedBy { it!!.time }
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

    fun setMatchMinHeight(context: Context?, match: Match?, binding: FragmentMatchBinding) {
        if (match == null) return
        val factor: Float = context!!.resources.displayMetrics.density
        binding.timeColumn.minimumHeight = (getMatchMinHeight(match)!! * factor).toInt()
    }

    private fun getMatchMinHeight(match: Match?): Int {
        if (match?.homeTeam == null) return 0
        if (match.homeTeam!!.isClub()) return 90
        return if (match.groupName != null ) 70 else 50
    }

    fun renderLeg1Count(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderLegCount(context, match, binding, R.string.leg1)
    }

    fun renderLeg2Count(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderLegCount(context, match, binding, R.string.leg2)
    }

    private fun renderLegCount(context: Context?, match: Match?, binding: FragmentMatchBinding, count: Int?) {

        if (match == null) return

        if (!match.multipleLegs!!) {
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

        if (!match.multipleLegs!!) {
            binding.date.visibility = View.GONE
        } else {
            binding.date.visibility = View.VISIBLE
            renderField(CommonUtil.renderShortDate(match.leg2Date), binding.date)
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

        if (match == null) return
        renderTeamFlag(context, match.homeTeam!!, binding.homeFlag )
    }

    fun renderLeg1AwayTeamFlag(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
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

    private fun renderTeamFlag(context: Context?, team: Team?, binding: FragmentTeamFlagBinding?) {

        if (team == null) return
        CommonUtil.renderTeamFlag(context, team, binding!!.clubLogo, binding.flag)
    }

    fun renderLeg1ScoreColumn(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (match.homeScore == null || match.awayScore == null) return

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
    }

    fun renderLeg2ScoreColumn(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        if (match.leg2HomeScore == null || match.leg2AwayScore == null) return

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
    }

    private fun renderLeg1RegularScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.homeScore, match.awayScore, binding.score)
    }

    private fun renderLeg1ExtraScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.homeAfterExtraTimeScore(), match.awayAfterExtraTimeScore(), binding.extraScore)
    }

    private fun renderLeg2RegularScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.leg2HomeScore, match.leg2AwayScore, binding.score)
    }

    private fun renderLeg2ExtraScore(context: Context?, match: Match?, binding: FragmentMatchBinding) {

        if (match == null) return
        renderScore(context, match.leg2HomeAfterExtraTimeScore(), match.leg2AwayAfterExtraTimeScore(), binding.extraScore)
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

    private fun renderField(field: String?, textView: TextView) {
        if (field != null) {
            textView.visibility = View.VISIBLE
            textView.text = field
        } else {
            textView.visibility = View.GONE
        }
    }
}
