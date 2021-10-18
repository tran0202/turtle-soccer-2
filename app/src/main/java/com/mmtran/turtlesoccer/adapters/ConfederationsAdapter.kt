package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.activities.CompetitionActivity

import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.databinding.RowConfederationBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader
import com.mmtran.turtlesoccer.models.Competition

const val EXTRA_COMPETITION = "com.mmtran.turtlesoccer.COMPETITION"

class ConfederationsAdapter(context: Context?, confederationList: List<Confederation?>) :
    RecyclerView.Adapter<ConfederationsAdapter.ViewHolder>(), ConfCompetitionsAdapter.ItemClickListener {

    private val _context = context
    private var _confederationList: List<Confederation?> = confederationList
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private var confCompetitionsAdapter: ConfCompetitionsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowConfederationBinding = RowConfederationBinding.inflate(mInflater, parent, false)
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
        holder.descriptionTextView.text = _confederationList[position]!!.description

        val recyclerView: RecyclerView = holder.confCompetitionListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(_context, 1)
        confCompetitionsAdapter = ConfCompetitionsAdapter(_context, _confederationList[position]!!.competitionList!!)
        confCompetitionsAdapter!!.setClickListener(this);
        recyclerView.adapter = confCompetitionsAdapter
    }

    override fun onItemClick(view: View?, competitionList: List<Competition?>, position: Int) {
        val intent = Intent(_context, CompetitionActivity::class.java).apply {
            putExtra(EXTRA_COMPETITION, competitionList[position]!!)
        }
        startActivity(_context!!, intent, null)
//        Log.i("TAG",
//            "You clicked " + competitionList!![position]!!.name
//                 + ", which is at cell position " + position
//        )
    }

    override fun getItemCount(): Int {
        return _confederationList.size
    }

    inner class ViewHolder internal constructor(binding: RowConfederationBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var confederationFlagImageView: ImageView = binding.confederationLogo
        var confederationNameTextView: TextView = binding.confederationName
        var descriptionTextView: TextView = binding.description
        var confCompetitionListRecyclerView: RecyclerView = binding.confCompetitionList

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): Confederation? {
        return _confederationList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
