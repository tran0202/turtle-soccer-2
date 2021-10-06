package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.mmtran.turtlesoccer.adapters.ConfederationsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentConfederationsBinding
import com.mmtran.turtlesoccer.loaders.FirestoreLoader
import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.models.ConfederationListViewModel
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.models.CompetitionListViewModel
import com.mmtran.turtlesoccer.utils.ActionBarUtil

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
        confederationListViewModel = ViewModelProvider(this).get(
            ConfederationListViewModel::class.java
        )
        dataLoader.getConfederations(confederationListViewModel!!)

        competitionListViewModel = ViewModelProvider(this).get(
            CompetitionListViewModel::class.java
        )
        dataLoader.getCompetitions(competitionListViewModel!!)

        binding = FragmentConfederationsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        confederationsAdapter = ConfederationsAdapter(requireContext())
        binding!!.confederationList.adapter = confederationsAdapter

        return root
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
                joinCompetition()
            })
        competitionListViewModel!!.competitionList.observe(
            viewLifecycleOwner,
            { competitionList_: List<Competition?>? ->
                competitionList = competitionList_
                joinCompetition()
            })
    }

    private fun joinCompetition() {
        if (confederationList.isNullOrEmpty() || competitionList.isNullOrEmpty()) return
        for (confederation: Confederation? in confederationList!!) {
            val compList = competitionList!!.filter { it!!.confederationId == confederation!!.id }
            confederation!!.competitionList = compList
        }
        confederationsAdapter!!.setData(confederationList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
