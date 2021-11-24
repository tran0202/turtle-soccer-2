package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.adapters.CompSuccessfulTeamsAdapter
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
    private var compSuccessfulTeamsAdapter: CompSuccessfulTeamsAdapter? = null
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
        CommonUtil.renderLabelField(teamCount, binding!!.competitionTeamCountLabel, binding!!.competitionTeamCount)

        CompetitionUtil.renderChampions(requireContext(), competition!!.currentChampions, binding!!.competitionCurrentChampionsLabel,
            binding!!.competitionCurrentChampions, binding!!.currentChampionsFlagName, binding!!.currentChampionsTitleCount)

        CompetitionUtil.renderChampions(requireContext(), competition!!.lastChampions, binding!!.competitionLastChampionsLabel,
            binding!!.competitionLastChampions, binding!!.lastChampionsFlagName, binding!!.lastChampionsTitleCount)

        if (!competition!!.mostSuccessfulTeams.isNullOrEmpty()) {
            if (competition!!.mostSuccessfulTeams!!.size == 1) {
                binding!!.mostSuccessfulTeamLabel.visibility =View.VISIBLE
                binding!!.mostSuccessfulTeamsLabel.visibility =View.GONE
            } else {
                binding!!.mostSuccessfulTeamLabel.visibility =View.GONE
                binding!!.mostSuccessfulTeamsLabel.visibility =View.VISIBLE
            }
        } else {
            binding!!.mostSuccessfulTeamLabel.visibility =View.GONE
            binding!!.mostSuccessfulTeamsLabel.visibility =View.GONE
        }

        val recyclerView: RecyclerView = binding!!.compSuccessfulTeamList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        compSuccessfulTeamsAdapter = CompSuccessfulTeamsAdapter(requireContext(), competition!!.mostSuccessfulTeams!!)
        recyclerView.adapter = compSuccessfulTeamsAdapter

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
