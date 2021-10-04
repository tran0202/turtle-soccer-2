package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmtran.turtlesoccer.R

import com.mmtran.turtlesoccer.databinding.RowCompetitionBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition

class CompetitionItem(private val competition: Competition) : ListItem() {

    private var binding: RowCompetitionBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

    override fun getView(context: Context?, convertView: View?, parent: ViewGroup?): View {

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowCompetitionBinding.inflate(inflater, parent, false)
        val root: View = binding!!.root

        firebaseStorageLoader = FirebaseStorageLoader()
        firebaseStorageLoader!!.init(context)
        firebaseStorageLoader!!.loadImage(
            context,
            binding!!.competitionTrophy,
            competition.logoPath + "/" + competition.trophyFilename
        )

        binding!!.competitionName.text = competition.name
        binding!!.competitionTeamCount.text = context.getString(R.string.competition_team_count, competition.teamCount.toString());

        if (competition.currentChampions !== null) {
            binding!!.competitionCurrentChampionsLabel.text = context.getString(R.string.competition_current_champions_label);
        } else {
            binding!!.competitionCurrentChampionsLabel.text = context.getString(R.string.competition_last_champions_label);
        }
        if (competition.isClubCompetition()) {
            firebaseStorageLoader!!.loadImage(
                context,
                binding!!.competitionCurrentChampionsFlag,
                "club_logos/" + competition.currentChampionClub.value!!.logoFilename
            )
            binding!!.competitionCurrentChampions.text = competition.currentChampionClub.value!!.name
        } else {
            firebaseStorageLoader!!.loadImage(
                context,
                binding!!.competitionCurrentChampionsFlag,
                "flags/" + competition.currentChampionNation.value!!.flagFilename
            )
            binding!!.competitionCurrentChampions.text = competition.currentChampionNation.value!!.name
        }

        binding!!.description.text = if (competition.descriptions!!.isNotEmpty()) competition.descriptions!![0] else ""

        return root
    }
}