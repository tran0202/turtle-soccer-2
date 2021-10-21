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
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowNationBinding = RowNationBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.nationIdTextView.text = _nationList[position]!!.id

        val firebaseStorageLoader = FirebaseStorageLoader(_context)
        firebaseStorageLoader.loadImage(
            _context,
            holder.nationFlagImageView,
            "flags/" + _nationList[position]!!.flagFilename
        )

        holder.nationNameTextView.text = _nationList[position]!!.name
        holder.confederationTextView.text = _nationList[position]!!.confederationId
    }

    override fun getItemCount(): Int {
        return _nationList.size
    }

    inner class ViewHolder internal constructor(binding: RowNationBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var nationIdTextView: TextView = binding.nationId
        var nationFlagImageView: ImageView = binding.nationFlag
        var nationNameTextView: TextView = binding.nationName
        var confederationTextView: TextView = binding.confederation

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, absoluteAdapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): Nation? {
        return _nationList[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
