package com.mmtran.turtlesoccer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.adapters.AssociationsAdapter;
import com.mmtran.turtlesoccer.databinding.FragmentAssociationsBinding;
import com.mmtran.turtlesoccer.loaders.FirestoreLoader;
import com.mmtran.turtlesoccer.models.AssociationListViewModel;
import com.mmtran.turtlesoccer.utils.ActionBarUtil;

public class AssociationsFragment extends Fragment {

    private AssociationListViewModel associationListViewModel;
    private FragmentAssociationsBinding binding;
    private AssociationsAdapter associationsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        associationListViewModel = new ViewModelProvider(this).get(AssociationListViewModel.class);

        FirestoreLoader dataLoader = new FirestoreLoader();
        dataLoader.getActiveNations(associationListViewModel);

        binding = FragmentAssociationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        associationsAdapter = new AssociationsAdapter(getContext());
        binding.associationList.setAdapter(associationsAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ActionBarUtil.buildActionBar(getLayoutInflater(), actionBar, R.layout.toolbar_associations);
        actionBar.setTitle(R.string.toolbar_associations);

        associationListViewModel.getNationList().observe(getViewLifecycleOwner(), nationList -> {
            associationsAdapter.setData(nationList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
