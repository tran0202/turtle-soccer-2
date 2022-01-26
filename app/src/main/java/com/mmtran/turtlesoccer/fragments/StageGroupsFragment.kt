package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.adapters.GroupsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentStageGroupsBinding
import com.mmtran.turtlesoccer.models.Stage
import com.mmtran.turtlesoccer.models.StageViewModel

class StageGroupsFragment(stage: Stage? = Stage()) : Fragment() {

    private var stageViewModel: StageViewModel? = null
    private var _stage: Stage? = stage ?: Stage()

    private var binding: FragmentStageGroupsBinding? = null
    private var groupsAdapter: GroupsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        stageViewModel = ViewModelProvider(this).get(modelClass = StageViewModel::class.java)
        stageViewModel!!.setStage(_stage!!)

        binding = FragmentStageGroupsBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding!!.groupList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        groupsAdapter = GroupsAdapter(requireContext(), if (_stage!!.groups != null) _stage!!.groups!! else emptyList())
        recyclerView.adapter = groupsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(stage: Stage?): Fragment {
            return StageGroupsFragment(stage)
        }
    }
}
