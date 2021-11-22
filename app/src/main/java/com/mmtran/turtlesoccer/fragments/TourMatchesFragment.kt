package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.databinding.FragmentTourMatchesBinding
import com.mmtran.turtlesoccer.models.TourMatchesViewModel
import com.mmtran.turtlesoccer.models.Tournament

class TourMatchesFragment(tour: Tournament?) : Fragment() {

    private var tourMatchesViewModel: TourMatchesViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourMatchesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourMatchesViewModel = ViewModelProvider(this).get(modelClass = TourMatchesViewModel::class.java)
        tourMatchesViewModel!!.setTournament(tournament!!)

        binding = FragmentTourMatchesBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tourMatchesViewModel!!.tournament.observe(
            viewLifecycleOwner,
            { _: Tournament? ->
                tournamentObserver()
            })
    }

    private fun tournamentObserver() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(tour: Tournament?): Fragment {
            return TourMatchesFragment(tour)
        }
    }
}
