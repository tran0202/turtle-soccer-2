package com.mmtran.turtlesoccer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmtran.turtlesoccer.databinding.RowAssociationBinding;
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader;
import com.mmtran.turtlesoccer.models.Nation;

public class AssociationItem extends ListItem {

    private RowAssociationBinding binding;
    private Nation nation;
    private FirebaseStorageLoader firebaseStorageLoader;

    public AssociationItem(Nation nation) {
        this.nation = nation;
    }

    @Override
    public View getView (Context context, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = RowAssociationBinding.inflate(inflater, parent, false);
        View root = binding.getRoot();

        firebaseStorageLoader = new FirebaseStorageLoader(context);
        firebaseStorageLoader.loadImage(context, binding.associationFlag, "flags/" + nation.getFlagFilename());

        binding.associationId.setText(nation.getId());
        binding.associationName.setText(nation.getName());
        binding.confederation.setText(nation.getConfederationId());

        return root;
    }
}
