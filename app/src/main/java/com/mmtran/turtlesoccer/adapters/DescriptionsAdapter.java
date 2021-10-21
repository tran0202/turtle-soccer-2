package com.mmtran.turtlesoccer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.databinding.RowDescriptionBinding;

import java.util.ArrayList;
import java.util.List;

public class DescriptionsAdapter extends ArrayAdapter<DescriptionItem> {

    public DescriptionsAdapter(@NonNull Context context) {
        super(context, R.layout.row_description);
    }

    public void setData(List<String> descriptionList) {

        clear();
        List<DescriptionItem> items = new ArrayList<>();

        if (descriptionList != null && descriptionList.size() > 0) {
            for (String description: descriptionList) {
                items.add(new DescriptionItem(description));
            }
        }
        addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DescriptionItem item = getItem(position);
        return item.getView(getContext(), convertView, parent);
    }
}

class DescriptionItem extends ListItem {

    private final String description;

    public DescriptionItem(String description) {
        this.description = description;
    }

    @Override
    public View getView (Context context, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RowDescriptionBinding binding = RowDescriptionBinding.inflate(inflater, parent, false);
        View root = binding.getRoot();

        binding.description.setText(description);

        return root;
    }
}
