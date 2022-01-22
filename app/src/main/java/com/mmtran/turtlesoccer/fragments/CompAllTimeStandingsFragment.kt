package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.adapters.PoolsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompAllTimeStandingsBinding
import com.mmtran.turtlesoccer.models.CompAllTimeStandingsViewModel
import com.mmtran.turtlesoccer.models.Competition

class CompAllTimeStandingsFragment(comp: Competition?) : Fragment() {

    private var compAllTimeStandingsViewModel: CompAllTimeStandingsViewModel? = null
    private var competition: Competition? = comp

    private var binding: FragmentCompAllTimeStandingsBinding? = null
    private var poolsAdapter: PoolsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        compAllTimeStandingsViewModel = ViewModelProvider(this).get(modelClass = CompAllTimeStandingsViewModel::class.java)
        compAllTimeStandingsViewModel!!.setCompetition(competition!!)

        binding = FragmentCompAllTimeStandingsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding!!.poolList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        poolsAdapter = PoolsAdapter(requireContext(), competition!!.pools!!)
        recyclerView.adapter = poolsAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(comp: Competition?): Fragment {
            return CompAllTimeStandingsFragment(comp)
        }
    }
}
