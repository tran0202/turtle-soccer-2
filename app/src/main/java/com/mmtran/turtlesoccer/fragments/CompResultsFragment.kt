package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmtran.turtlesoccer.databinding.FragmentCompResultsBinding
import com.mmtran.turtlesoccer.models.CompResultsViewModel
import com.mmtran.turtlesoccer.models.Competition

class CompResultsFragment(comp: Competition?) : Fragment() {

    private var compResultsViewModel: CompResultsViewModel? = null
    private var competition: Competition? = comp

    private var binding: FragmentCompResultsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        compResultsViewModel = ViewModelProvider(this).get(
            CompResultsViewModel::class.java
        )
        compResultsViewModel!!.setCompetition(competition!!)

        binding = FragmentCompResultsBinding.inflate(inflater, container, false)

        compResultsViewModel!!.competition.observe(
            viewLifecycleOwner,
            { _: Competition? ->
                competitionObserver()
            })

        return binding!!.root
    }

    private fun competitionObserver() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(comp: Competition?): Fragment {
            return CompResultsFragment(comp)
        }
    }
}
