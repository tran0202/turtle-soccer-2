package com.mmtran.turtlesoccer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.mmtran.turtlesoccer.databinding.FragmentHomeBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.HomeViewModel
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.utils.ActionBarUtil.buildActionBar

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel? = null
    private var binding: FragmentHomeBinding? = null
    private var firebaseStorageLoader: FirebaseStorageLoader? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        buildActionBar(layoutInflater, actionBar, R.layout.toolbar_home)
        actionBar!!.setTitle(R.string.toolbar_home)

        firebaseStorageLoader = FirebaseStorageLoader()
        firebaseStorageLoader!!.init(context)
        firebaseStorageLoader!!.loadImage(
            activity,
            binding!!.logoHome,
            "logos/Turtle_Soccer_logo.png"
        )
        firebaseStorageLoader!!.loadImage(activity, binding!!.heroImage, "soccer_ts1475731972.jpg")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
