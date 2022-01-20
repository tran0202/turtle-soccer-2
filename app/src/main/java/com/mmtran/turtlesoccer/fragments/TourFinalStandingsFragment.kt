package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.adapters.RoundMatchesAdapter
import com.mmtran.turtlesoccer.adapters.RoundRankingsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentTourFinalStandingsBinding
import com.mmtran.turtlesoccer.models.*

class TourFinalStandingsFragment(tour: Tournament?) : Fragment() {

    private var tourFinalStandingsViewModel: TourFinalStandingsViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourFinalStandingsBinding? = null
    private var roundRankingsAdapter: RoundRankingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourFinalStandingsViewModel = ViewModelProvider(this).get(modelClass = TourFinalStandingsViewModel::class.java)
        tourFinalStandingsViewModel!!.setTournament(tournament!!)

        binding = FragmentTourFinalStandingsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding!!.roundRankingsList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        roundRankingsAdapter = RoundRankingsAdapter(requireContext(), tournament!!.currentCampaign!!.roundRankings!!)
        recyclerView.adapter = roundRankingsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(tour: Tournament?): Fragment {
            return TourFinalStandingsFragment(tour)
        }
    }
}
