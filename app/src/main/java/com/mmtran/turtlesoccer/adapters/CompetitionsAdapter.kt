package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.Competition

import java.util.ArrayList

class CompetitionsAdapter(context: Context) : ArrayAdapter<CompetitionItem?>(context, R.layout.row_competition) {

    fun setData(competitionList: List<Competition?>?) {
        clear()
        val items: MutableList<CompetitionItem> = ArrayList()
        if (competitionList != null && competitionList.isNotEmpty()) {
            for (competition in competitionList) {
                items.add(CompetitionItem(competition!!))
            }
        }
        addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return item!!.getView(context, convertView, parent)
    }
}
