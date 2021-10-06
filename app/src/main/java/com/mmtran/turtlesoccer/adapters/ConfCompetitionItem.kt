package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mmtran.turtlesoccer.databinding.RowConfCompetitionBinding
import com.mmtran.turtlesoccer.models.Competition

class ConfCompetitionItem(private val competition: Competition) : ListItem() {

    private var binding: RowConfCompetitionBinding? = null

    override fun getView(context: Context?, convertView: View?, parent: ViewGroup?): View {

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowConfCompetitionBinding.inflate(inflater, parent, false)
        val root: View = binding!!.root

        binding!!.competitionName.text = competition.name

        return root
    }
}
