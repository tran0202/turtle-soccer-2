package com.mmtran.turtlesoccer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mmtran.turtlesoccer.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        buildActionBar(getLayoutInflater(), actionBar, R.layout.toolbar_home);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void buildActionBar (LayoutInflater inflater, ActionBar actionBar, Integer toolBar) {
//        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
//        View customActionBar = inflater.inflate(toolBar, null);
//        if (actionBar != null) {
//            actionBar.setCustomView(customActionBar, layoutParams);
//            actionBar.setDisplayShowCustomEnabled(true);
//        }
//    }
}
