package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.adapters.ConfederationsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentConfederationsBinding
import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.models.ConfederationListViewModel
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.CompetitionListViewModel
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import androidx.core.content.ContextCompat

class ConfederationsFragment : Fragment() {

    private var confederationListViewModel: ConfederationListViewModel? = null
    private var competitionListViewModel: CompetitionListViewModel? = null
    private var confederationList: List<Confederation?>? = emptyList()
    private var competitionList: List<Competition?>? = emptyList()

    private var binding: FragmentConfederationsBinding? = null
    private var confederationsAdapter: ConfederationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val dataLoader = FirestoreLoader()

        confederationListViewModel = ViewModelProvider(this).get(modelClass = ConfederationListViewModel::class.java)
        dataLoader.getConfederations(confederationListViewModel!!)

        competitionListViewModel = ViewModelProvider(this).get(modelClass = CompetitionListViewModel::class.java)
        dataLoader.getCompetitions(competitionListViewModel!!)

        binding = FragmentConfederationsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        ActionBarUtil.buildActionBar(layoutInflater, actionBar, R.layout.toolbar_confederations)
        actionBar!!.setTitle(R.string.toolbar_confederations)

        confederationListViewModel!!.confederationList.observe(
            viewLifecycleOwner,
            { confederationList_: List<Confederation?>? ->
                confederationList = confederationList_
                confederationsObserver()
            })
        competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                confederationsObserver()
            })
    }

    private fun confederationsObserver() {

        if (confederationList.isNullOrEmpty() || competitionList.isNullOrEmpty()) return

        for (confederation: Confederation? in confederationList!!) {
            val compList = competitionList!!.filter { it!!.confederationId == confederation!!.id }
            confederation!!.competitionList = compList
        }

        val recyclerView: RecyclerView = binding!!.confederationList
        recyclerView.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_gray_5
            )!!
        )
        recyclerView.addItemDecoration(divider)
        confederationsAdapter = ConfederationsAdapter(context, confederationList!!)
        recyclerView.adapter = confederationsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
