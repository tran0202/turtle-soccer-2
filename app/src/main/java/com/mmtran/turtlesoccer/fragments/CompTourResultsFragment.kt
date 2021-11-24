package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.CompTourResultsAdapter
import com.mmtran.turtlesoccer.adapters.EXTRA_TOURNAMENT
import com.mmtran.turtlesoccer.databinding.FragmentCompTourResultsBinding
import com.mmtran.turtlesoccer.models.CompTourResultsViewModel
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.Tournament
import com.mmtran.turtlesoccer.utils.CommonUtil

class CompTourResultsFragment(comp: Competition?) : Fragment(), CompTourResultsAdapter.ItemClickListener {

    private var compTourResultsViewModel: CompTourResultsViewModel? = null
    private var competition: Competition? = comp

    private var binding: FragmentCompTourResultsBinding? = null
    private var compTourResultsAdapter: CompTourResultsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        compTourResultsViewModel = ViewModelProvider(this).get(modelClass = CompTourResultsViewModel::class.java)
        compTourResultsViewModel!!.setCompetition(competition!!)

        binding = FragmentCompTourResultsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding!!.tourResultList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        CommonUtil.addDivider(recyclerView, requireContext(), R.drawable.no_divider)
        compTourResultsAdapter = CompTourResultsAdapter(requireContext(), competition!!.tournamentList!!)
        compTourResultsAdapter!!.setClickListener(this)
        recyclerView.adapter = compTourResultsAdapter
    }

    override fun onItemClick(view: View?, tournamentList: List<Tournament?>, position: Int) {

        val args = Bundle()
        args.putSerializable(EXTRA_TOURNAMENT, tournamentList[position]!!)
        val navController = Navigation.findNavController(context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_tournaments, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(comp: Competition?): Fragment {
            return CompTourResultsFragment(comp)
        }
    }
}
