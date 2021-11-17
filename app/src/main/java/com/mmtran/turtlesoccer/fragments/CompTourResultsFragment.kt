package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.adapters.CompTourResultsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentCompTourResultsBinding
import com.mmtran.turtlesoccer.models.CompTourResultsViewModel
import com.mmtran.turtlesoccer.models.Competition

class CompTourResultsFragment(comp: Competition?) : Fragment() {

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
        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.no_divider
            )!!
        )
        recyclerView.addItemDecoration(divider)
        compTourResultsAdapter = CompTourResultsAdapter(requireContext(), competition!!.tournamentList!!)
        recyclerView.adapter = compTourResultsAdapter
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
