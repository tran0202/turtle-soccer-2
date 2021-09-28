package com.mmtran.turtlesoccer.utils

import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBar

object ActionBarUtil {
    @JvmStatic
    fun buildActionBar(inflater: LayoutInflater, actionBar: ActionBar?, toolBar: Int?) {
        val layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
        val customActionBar = inflater.inflate(toolBar!!, null)
        if (actionBar != null) {
            actionBar.setCustomView(customActionBar, layoutParams)
            actionBar.setDisplayShowCustomEnabled(true)
        }
    }
}
