package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.adapters.RoundsAdapter
import com.mmtran.turtlesoccer.databinding.FragmentStageMatchesBinding
import com.mmtran.turtlesoccer.models.Stage
import com.mmtran.turtlesoccer.models.StageViewModel

class StageMatchesFragment(stage: Stage? = Stage()) : Fragment() {

    private var stageViewModel: StageViewModel? = null
    private var _stage: Stage? = stage

    private var binding: FragmentStageMatchesBinding? = null
    private var roundsAdapter: RoundsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        stageViewModel = ViewModelProvider(this).get(modelClass = StageViewModel::class.java)
        stageViewModel!!.setStage( if (_stage != null) _stage!! else Stage())

        binding = FragmentStageMatchesBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding!!.roundList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        roundsAdapter = RoundsAdapter(requireContext(), _stage!!.rounds!!)
        recyclerView.adapter = roundsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(stage: Stage?): Fragment {
            return StageMatchesFragment(stage)
        }
    }
}
