package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CompSuccessfulTeamsAdapter
import com.mmtran.turtlesoccer.adapters.DescriptionsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompAboutBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.CompAboutViewModel
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.utils.TeamUtil.renderFlagName

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

        compAboutViewModel = ViewModelProvider(this).get(
            CompAboutViewModel::class.java
        )
        compAboutViewModel!!.setCompetition(competition!!)

        binding = FragmentCompAboutBinding.inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding!!.compSuccessfulTeamList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        compSuccessfulTeamsAdapter = CompSuccessfulTeamsAdapter(requireContext(), competition!!.mostSuccessfulTeams!!)
        recyclerView.adapter = compSuccessfulTeamsAdapter

        descriptionsAdapter = DescriptionsAdapter(requireContext())
        binding!!.descriptionList.adapter = descriptionsAdapter

        compAboutViewModel!!.competition.observe(
            viewLifecycleOwner,
            { _: Competition? ->
                competitionObserver()
            })

        return binding!!.root
    }

    private fun competitionObserver() { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseStorageLoader = FirebaseStorageLoader(requireContext())

        if (competition!!.teamCount != null) {
            binding!!.competitionTeamCount.visibility = View.VISIBLE
            binding!!.competitionTeamCount.text = requireContext().getString(R.string.competition_team_count, competition!!.teamCount.toString())
        } else {
            binding!!.competitionTeamCount.visibility = View.GONE
        }

        when {
            competition!!.currentChampions !== null && competition!!.currentChampions!!.team !== null ->  {
                binding!!.competitionCurrentChampionsLabel.visibility = View.VISIBLE
                binding!!.competitionCurrentChampionsLabel.text = requireContext().getString(R.string.competition_current_champions_label)
                binding!!.competitionCurrentChampions.visibility = View.VISIBLE
                renderFlagName(requireContext(), binding!!.flagName, competition!!.currentChampions!!.team)
                if (competition!!.currentChampions!!.titleCount != null) {
                    binding!!.titleCount.text = requireActivity().getString(R.string.competition_title_count, competition!!.currentChampions!!.titleCount.toString())
                } else {
                    binding!!.titleCount.visibility = View.GONE
                }
            }
            competition!!.lastChampions !== null && competition!!.lastChampions!!.team !== null ->  {
                binding!!.competitionCurrentChampionsLabel.visibility = View.VISIBLE
                binding!!.competitionCurrentChampionsLabel.text = requireContext().getString(R.string.competition_last_champions_label)
                binding!!.competitionCurrentChampions.visibility = View.VISIBLE
                renderFlagName(requireContext(), binding!!.flagName, competition!!.lastChampions!!.team)
                if (competition!!.lastChampions!!.titleCount != null) {
                    binding!!.titleCount.text = requireActivity().getString(R.string.competition_title_count, competition!!.lastChampions!!.titleCount.toString())
                } else {
                    binding!!.titleCount.visibility = View.GONE
                }
            }
            else ->  {
                binding!!.competitionCurrentChampionsLabel.visibility = View.GONE
                binding!!.competitionCurrentChampions.visibility = View.GONE
            }
        }

        if (!competition!!.mostSuccessfulTeams.isNullOrEmpty()) {
            binding!!.mostSuccessfulTeamLabel.text = requireContext().getString(R.string.competition_most_successful_team_label)
        } else {
            binding!!.mostSuccessfulTeamLabel.visibility =View.GONE
        }

        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.competitionTrophy,
            competition!!.logoPath + "/" + competition!!.trophyFilename
        )

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
