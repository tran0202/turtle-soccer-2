package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.databinding.RowCampaignBinding

import com.mmtran.turtlesoccer.models.Campaign

class CampaignsAdapter(context: Context?, campaignList: List<Campaign?>) :
    RecyclerView.Adapter<CampaignsAdapter.ViewHolder>() {

    private val _context = context
    private val _campaignList: List<Campaign?> = campaignList
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: RowCampaignBinding = RowCampaignBinding.inflate(_inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.campaignNameTextView.text = _campaignList[position]!!.name
    }

    override fun getItemCount(): Int {
        return _campaignList.size
    }

    inner class ViewHolder internal constructor(binding: RowCampaignBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        val root: View = binding.root
        var campaignNameTextView: TextView = binding.campaignName

        override fun onClick(view: View) {
            if (_clickListener != null) _clickListener!!.onItemClick(view, _campaignList, absoluteAdapterPosition)
        }

        init {
            root.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        _clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, campaignList: List<Campaign?>, position: Int)
    }
}
