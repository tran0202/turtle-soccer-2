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
import com.mmtran.turtlesoccer.databinding.FragmentConfederationsBinding;
import com.mmtran.turtlesoccer.models.ConfederationsViewModel;
import com.mmtran.turtlesoccer.utils.ActionBarUtil;

public class ConfederationsFragment extends Fragment {

    private ConfederationsViewModel confederationsViewModel;
    private FragmentConfederationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        confederationsViewModel =
                new ViewModelProvider(this).get(ConfederationsViewModel.class);

        binding = FragmentConfederationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        confederationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ActionBarUtil.buildActionBar(getLayoutInflater(), actionBar, R.layout.toolbar_confederations);
        actionBar.setTitle(R.string.toolbar_confederations);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}