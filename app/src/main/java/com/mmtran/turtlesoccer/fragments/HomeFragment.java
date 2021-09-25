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
import com.mmtran.turtlesoccer.databinding.FragmentHomeBinding;
import com.mmtran.turtlesoccer.loaders.FirebaseStorageLoader;
import com.mmtran.turtlesoccer.models.HomeViewModel;
import com.mmtran.turtlesoccer.utils.ActionBarUtil;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FirebaseStorageLoader firebaseStorageLoader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ActionBarUtil.buildActionBar(getLayoutInflater(), actionBar, R.layout.toolbar_associations);
        actionBar.setTitle(R.string.toolbar_associations);


        firebaseStorageLoader = new FirebaseStorageLoader();
        firebaseStorageLoader.init(getContext());
        firebaseStorageLoader.loadImage(getActivity(), binding.logoHome, "logos/Turtle_Soccer_logo.png");
        firebaseStorageLoader.loadImage(getActivity(), binding.heroImage, "soccer_ts1475731972.jpg");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
