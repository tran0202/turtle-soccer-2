package com.mmtran.turtlesoccer.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.databinding.RowAssociationBinding;
import com.mmtran.turtlesoccer.models.Nation;

public class AssociationItem extends ListItem {

    private RowAssociationBinding binding;
    private Nation nation;

    public AssociationItem(Nation nation) {
        this.nation = nation;
    }

    @Override
    public View getView (Context context, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = RowAssociationBinding.inflate(inflater, parent, false);
        View root = binding.getRoot();

        binding.associationFlag.setImageDrawable(context.getDrawable(R.drawable.turtle_soccer_logo));
        binding.associationId.setText(nation.getId());

        return root;
    }
}
