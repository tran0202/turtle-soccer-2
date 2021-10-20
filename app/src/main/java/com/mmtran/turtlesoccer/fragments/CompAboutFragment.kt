package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.R
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        compAboutViewModel = ViewModelProvider(this).get(
            CompAboutViewModel::class.java
        )
        compAboutViewModel!!.setCompetition(competition!!)

        binding = FragmentCompAboutBinding.inflate(inflater, container, false)

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

        binding!!.competitionTeamCount.text = requireContext().getString(R.string.competition_team_count, competition!!.teamCount.toString())

        if (competition!!.currentChampions !== null) {
            binding!!.competitionCurrentChampionsLabel.text = requireContext().getString(R.string.competition_current_champions_label)
            renderFlagName(requireContext(), binding!!.flagName, competition!!.currentChampionTeam)
        } else {
            binding!!.competitionCurrentChampionsLabel.text = requireContext().getString(R.string.competition_last_champions_label)
            renderFlagName(requireContext(), binding!!.flagName, competition!!.lastChampionTeam)
        }

        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.competitionTrophy,
            competition!!.logoPath + "/" + competition!!.trophyFilename
        )
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
