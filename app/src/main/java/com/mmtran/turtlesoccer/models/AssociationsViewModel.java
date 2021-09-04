package com.mmtran.turtlesoccer.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssociationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AssociationsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
