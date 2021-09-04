package com.mmtran.turtlesoccer.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.ActionBar;

public class ActionBarUtil {

    public static void buildActionBar(LayoutInflater inflater, ActionBar actionBar, Integer toolBar) {
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View customActionBar = inflater.inflate(toolBar, null);
        if (actionBar != null) {
            actionBar.setCustomView(customActionBar, layoutParams);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }
}
