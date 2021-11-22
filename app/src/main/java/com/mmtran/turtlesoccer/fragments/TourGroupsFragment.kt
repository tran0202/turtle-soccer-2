package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.databinding.FragmentTourGroupsBinding
import com.mmtran.turtlesoccer.models.TourGroupsViewModel
import com.mmtran.turtlesoccer.models.Tournament

class TourGroupsFragment(tour: Tournament?) : Fragment() {

    private var tourGroupsViewModel: TourGroupsViewModel? = null
    private var tournament: Tournament? = tour

    private var binding: FragmentTourGroupsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        tourGroupsViewModel = ViewModelProvider(this).get(modelClass = TourGroupsViewModel::class.java)
        tourGroupsViewModel!!.setTournament(tournament!!)

        binding = FragmentTourGroupsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tourGroupsViewModel!!.tournament.observe(
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
            return TourGroupsFragment(tour)
        }
    }
}
