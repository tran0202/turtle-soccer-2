package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowNationBinding
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader

import com.mmtran.turtlesoccer.models.Nation

class NationsAdapter(context: Context?, nationList: List<Nation?>) :
    RecyclerView.Adapter<NationsAdapter.ViewHolder>() {

    private val _context = context
    private var _nationList: List<Nation?> = nationList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowNationBinding = RowNationBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val nation: Nation = _nationList[position]!!
        holder.nationIdTextView.text = nation.id

        if (!nation.flagFilename.isNullOrEmpty()) {
            val firebaseStorageLoader = FirebaseStorageLoader(_context)
            firebaseStorageLoader.loadImage(
                _context,
                holder.nationFlagImageView,
                "flags/" + nation.flagFilename
            )
        } else {
            holder.nationFlagImageView.visibility = View.GONE
        }

        holder.nationNameTextView.text = nation.name
        holder.confederationTextView.text = nation.confederation.id
    }

    override fun getItemCount(): Int {
        return _nationList.size
    }

    inner class ViewHolder internal constructor(binding: RowNationBinding) : RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        var nationIdTextView: TextView = binding.nationId
        var nationFlagImageView: ImageView = binding.nationFlag
        var nationNameTextView: TextView = binding.nationName
        var confederationTextView: TextView = binding.confederation
    }
}
