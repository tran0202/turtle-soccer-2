package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.mmtran.turtlesoccer.models.Confederation
import com.mmtran.turtlesoccer.R

import java.util.ArrayList

class ConfederationsAdapter(context: Context) : ArrayAdapter<ConfederationItem?>(context, R.layout.row_confederation) {

    fun setData(confederationList: List<Confederation?>?) {
        clear()
        val items: MutableList<ConfederationItem> = ArrayList()
        if (confederationList != null && confederationList.isNotEmpty()) {
            for (confederation in confederationList) {
                items.add(ConfederationItem(confederation!!))
            }
        }
        addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return item!!.getView(context, convertView, parent)
    }
}
