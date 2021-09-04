package com.mmtran.turtlesoccer.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfederationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfederationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("FIFA and Regional Confederations");
    }

    public LiveData<String> getText() {
        return mText;
    }
}