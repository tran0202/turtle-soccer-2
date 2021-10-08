package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.mmtran.turtlesoccer.R
import com.mmtran.turtlesoccer.models.Tournament

import java.util.ArrayList

class CompTournamentsAdapter(context: Context) : ArrayAdapter<CompTournamentItem?>(context, R.layout.row_comp_tournament) {

    fun setData(tournamentList: List<Tournament?>?) {
        clear()
        val items: MutableList<CompTournamentItem> = ArrayList()
        if (tournamentList != null && tournamentList.isNotEmpty()) {
            for (tournament in tournamentList) {
                items.add(CompTournamentItem(tournament!!))
            }
        }
        addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return item!!.getView(context, convertView, parent)
    }
}
