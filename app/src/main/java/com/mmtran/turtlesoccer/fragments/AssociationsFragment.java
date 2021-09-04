package com.mmtran.turtlesoccer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.adapters.AssociationsAdapter;
import com.mmtran.turtlesoccer.databinding.FragmentAssociationsBinding;
import com.mmtran.turtlesoccer.loaders.ResourceLoader;
import com.mmtran.turtlesoccer.models.AssociationsViewModel;
import com.mmtran.turtlesoccer.models.Nation;
import com.mmtran.turtlesoccer.utils.ActionBarUtil;

import java.util.List;

public class AssociationsFragment extends Fragment {

    private AssociationsViewModel associationsViewModel;
    private FragmentAssociationsBinding binding;
    private ResourceLoader resourceLoader;
    private List<Nation> nationList;
    private AssociationsAdapter associationsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        resourceLoader = new ResourceLoader(getResources());
        nationList = resourceLoader.getActiveNations();

        associationsViewModel =
                new ViewModelProvider(this).get(AssociationsViewModel.class);

        binding = FragmentAssociationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        associationsAdapter = new AssociationsAdapter(getActivity());
        binding.associationList.setAdapter(associationsAdapter);
        associationsAdapter.setData(nationList);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ActionBarUtil.buildActionBar(getLayoutInflater(), actionBar, R.layout.toolbar_associations);
        actionBar.setTitle(R.string.toolbar_associations);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
