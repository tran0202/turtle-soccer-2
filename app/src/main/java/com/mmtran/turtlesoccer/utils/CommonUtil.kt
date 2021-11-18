package com.mmtran.turtlesoccer.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.mmtran.turtlesoccer.R

object CommonUtil {

    fun addDivider(recyclerView: RecyclerView, context: Context) {

        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.divider_gray_5
            )!!
        )
        recyclerView.addItemDecoration(divider)
    }
}
