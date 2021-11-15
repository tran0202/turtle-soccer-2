package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.activities.MainActivity

import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.databinding.RowConfederationBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition

class ConfederationsAdapter(context: Context?, confederationList: List<Confederation?>) :
    RecyclerView.Adapter<ConfederationsAdapter.ViewHolder>(), ConfCompetitionsAdapter.ItemClickListener {

    private val _context = context
    private var _confederationList: List<Confederation?> = confederationList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var confCompetitionsAdapter: ConfCompetitionsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowConfederationBinding = RowConfederationBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.confederationFlagImageView,
            "logos/" + _confederationList[position]!!.logoFilename
        )

        holder.confederationNameTextView.text = _confederationList[position]!!.name

        if (!_confederationList[position]!!.descriptions!!.isNullOrEmpty() && !_confederationList[position]!!.descriptions?.get(0).isNullOrEmpty()) {
            holder.descriptionTextView.text = _confederationList[position]!!.descriptions?.get(0)
        } else {
            holder.descriptionTextView.visibility = View.GONE
        }

        if (!_confederationList[position]!!.competitionList!!.isNullOrEmpty()) {
            holder.competitionLabelTextView.visibility = View.VISIBLE
        } else {
            holder.competitionLabelTextView.visibility = View.GONE
        }

        val recyclerView: RecyclerView = holder.confCompetitionListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        confCompetitionsAdapter = ConfCompetitionsAdapter(_context, _confederationList[position]!!.competitionList!!)
        confCompetitionsAdapter!!.setClickListener(this)
        recyclerView.adapter = confCompetitionsAdapter
    }

    override fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int) {

        val args = Bundle()
        args.putSerializable(EXTRA_COMPETITION, competitionList[position]!!)
        val navController = Navigation.findNavController(_context as MainActivity, R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_competitions, args)
    }

    override fun getItemCount(): Int {
        return _confederationList.size
    }

    inner class ViewHolder internal constructor(binding: RowConfederationBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var confederationFlagImageView: ImageView = binding.confederationLogo
        var confederationNameTextView: TextView = binding.confederationName
        var descriptionTextView: TextView = binding.description
        var competitionLabelTextView: TextView = binding.competitionLabel
        var confCompetitionListRecyclerView: RecyclerView = binding.confCompetitionList
    }
}
