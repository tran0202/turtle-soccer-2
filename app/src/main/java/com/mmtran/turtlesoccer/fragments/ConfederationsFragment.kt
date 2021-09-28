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
import com.mmtran.turtlesoccer.models.ConfederationsViewModel
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.utils.ActionBarUtil

class ConfederationsFragment : Fragment() {

    private var confederationsViewModel: ConfederationsViewModel? = null
    private var binding: FragmentConfederationsBinding? = null
    private var confederationsAdapter: ConfederationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        confederationsViewModel = ViewModelProvider(this).get(
            ConfederationsViewModel::class.java
        )
        val dataLoader = FirestoreLoader()
        dataLoader.getConfederations(confederationsViewModel!!)

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

        confederationsViewModel!!.confederationList.observe(
            viewLifecycleOwner,
            { confederationList: List<Confederation?>? ->
                confederationsAdapter!!.setData(confederationList)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
