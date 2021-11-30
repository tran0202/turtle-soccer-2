package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.DescriptionsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompAboutBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.CompAboutViewModel
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.utils.CommonUtil
import com.mmtran.turtlesoccer.utils.CompetitionUtil

class CompAboutFragment(comp: Competition?) : Fragment() {

    private var compAboutViewModel: CompAboutViewModel? = null
    private var competition: Competition? = comp

    private var binding: FragmentCompAboutBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null
    private var descriptionsAdapter: DescriptionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        compAboutViewModel = ViewModelProvider(this).get(modelClass = CompAboutViewModel::class.java)
        compAboutViewModel!!.setCompetition(competition!!)

        binding = FragmentCompAboutBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseStorageLoader = FirebaseStorageLoader(requireContext())

        val teamCount = CompetitionUtil.renderTeamCount(competition!!)
        CommonUtil.renderWrapLabelField(requireContext(), teamCount, binding!!.competitionTeamCount, R.string.tournament_team_count_label)

        CommonUtil.renderLabelChampion(requireContext(), competition!!.currentChampions, binding!!.currentChampions, R.string.competition_current_champions_label)

        CommonUtil.renderLabelChampion(requireContext(), competition!!.lastChampions, binding!!.lastChampions, R.string.competition_last_champions_label)

        CommonUtil.renderChampionList(requireContext(), competition!!.mostSuccessfulTeams!!, binding!!.mostSuccessfulTeams,
            R.string.competition_most_successful_team_label, R.string.competition_most_successful_teams_label)

        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.competitionTrophy,
            competition!!.logoPath + "/" + competition!!.trophyFilename
        )

        descriptionsAdapter = DescriptionsAdapter(requireContext())
        binding!!.descriptionList.adapter = descriptionsAdapter
        descriptionsAdapter!!.setData(competition!!.descriptions!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(comp: Competition?): Fragment {
            return CompAboutFragment(comp)
        }
    }
}
