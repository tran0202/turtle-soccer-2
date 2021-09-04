package com.mmtran.turtlesoccer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.models.Nation;

import java.util.ArrayList;
import java.util.List;

public class AssociationsAdapter extends ArrayAdapter<AssociationItem> {

    public AssociationsAdapter(@NonNull Context context) {
        super(context, R.layout.row_association);
    }

    public void setData(List<Nation> nationList) {

        clear();
        List<AssociationItem> items = new ArrayList<>();

        if (nationList != null && nationList.size() > 0) {
            for (Nation nation: nationList) {
                items.add(new AssociationItem(nation));
            }
        }
        addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssociationItem item = getItem(position);
        View view = item.getView(getContext(), convertView, parent);
        return view;
    }
}
