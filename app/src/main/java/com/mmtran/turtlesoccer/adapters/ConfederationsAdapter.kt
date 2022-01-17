package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.activities.MainActivity

import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.databinding.RowConfederationBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition
import com.mmtran.turtlesoccer.utils.CompetitionUtil

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

        val confederation: Confederation = _confederationList[position]!!
        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.confederationFlagImageView,
            "logos/" + confederation.logoFilename
        )

        holder.confederationNameTextView.text = confederation.name

        if (!confederation.descriptions!!.isNullOrEmpty() && !confederation.descriptions?.get(0).isNullOrEmpty()) {
            holder.descriptionTextView.text = confederation.descriptions?.get(0)
        } else {
            holder.descriptionTextView.visibility = View.GONE
        }

        if (!confederation.competitionList!!.isNullOrEmpty()) {
            holder.competitionLabelTextView.visibility = View.VISIBLE
        } else {
            holder.competitionLabelTextView.visibility = View.GONE
        }

        val recyclerView: RecyclerView = holder.confCompetitionListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        confCompetitionsAdapter = ConfCompetitionsAdapter(_context, confederation.competitionList!!)
        confCompetitionsAdapter!!.setClickListener(this)
        recyclerView.adapter = confCompetitionsAdapter
    }

    override fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int) {

        CompetitionUtil.browseToCompetition(_context as MainActivity, competitionList[position]!!)
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
