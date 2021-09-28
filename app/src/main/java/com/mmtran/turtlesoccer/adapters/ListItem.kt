package com.mmtran.turtlesoccer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup

abstract class ListItem {
    abstract fun getView(context: Context?, convertView: View?, parent: ViewGroup?): View?
}
