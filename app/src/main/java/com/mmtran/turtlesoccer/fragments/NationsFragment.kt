package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.utils.ActionBarUtil
import com.mmtran.turtlesoccer.activities.MainActivity
import com.mmtran.turtlesoccer.adapters.NationsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentNationsBinding
import com.mmtran.turtlesoccer.models.*
import com.mmtran.turtlesoccer.utils.CommonUtil

class NationsFragment : Fragment() {

    private var nationList: List<Nation?>? = emptyList()

    private var binding: FragmentNationsBinding? = null
    private var nationsAdapter: NationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentNationsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        ActionBarUtil.buildActionBar(layoutInflater, actionBar, R.layout.toolbar_nations)
        actionBar!!.setTitle(R.string.toolbar_nations)

        (activity as MainActivity).nationListViewModel!!.nationList.observe(
            viewLifecycleOwner,
            { nationList_: List<Nation?>? ->
                nationList = (activity as MainActivity).nationListViewModel!!.getActiveNations(nationList_)
                nationsObserver()
            })
    }

    private fun nationsObserver() {

        if (nationList.isNullOrEmpty()) return

        val recyclerView: RecyclerView = binding!!.nationList
        recyclerView.layoutManager = LinearLayoutManager(context)
        CommonUtil.addDivider(recyclerView, requireContext())
        nationsAdapter = NationsAdapter(context, nationList!!)
        recyclerView.adapter = nationsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
