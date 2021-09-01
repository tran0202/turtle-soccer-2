package com.mmtran.turtlesoccer.ui.associations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssociationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AssociationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
