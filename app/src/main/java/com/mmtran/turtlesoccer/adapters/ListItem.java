package com.mmtran.turtlesoccer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class ListItem {
    public abstract View getView(Context context, View convertView, ViewGroup parent);
}
