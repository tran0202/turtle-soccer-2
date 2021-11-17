package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.databinding.FragmentTournamentsBinding
import com.mmtran.turtlesoccer.models.TournamentListViewModel
import com.mmtran.turtlesoccer.utils.ActionBarUtil.buildActionBar

class TournamentsFragment : Fragment() {

    private var tournamentListViewModel: TournamentListViewModel? = null
    private var binding: FragmentTournamentsBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        tournamentListViewModel = ViewModelProvider(this).get(modelClass = TournamentListViewModel::class.java)

        binding = FragmentTournamentsBinding.inflate(inflater, container,false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        buildActionBar(layoutInflater, actionBar, R.layout.toolbar_tournaments)
        actionBar!!.setTitle(R.string.toolbar_tournaments)

        firebaseStorageLoader = FirebaseStorageLoader(context)
//        firebaseStorageLoader!!.loadImage(
//            activity,
//            binding!!.logoHome,
//            "logos/Turtle_Soccer_logo.png"
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
