package com.mmtran.turtlesoccer.utils

import android.content.Context
import android.view.View
import android.widget.TextView
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

    fun renderField(value: String, field: TextView) {
        if (!value.isNullOrEmpty()) {
            field.visibility = View.VISIBLE
            field.text = value
        } else {
            field.visibility = View.GONE
        }
    }

    fun renderLabelField(value: String, label: TextView, field: TextView) {
        if (!value.isNullOrEmpty()) {
            label.visibility = View.VISIBLE
            field.visibility = View.VISIBLE
            field.text = value
        } else {
            label.visibility = View.GONE
            field.visibility = View.GONE
        }
    }
}
