package com.mmtran.turtlesoccer.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AssociationListViewModel extends ViewModel {

    private MutableLiveData<List<Nation>> _nationList;

    public AssociationListViewModel() {
        _nationList = new MutableLiveData<>();
    }

    public LiveData<List<Nation>> getNationList() {
        return _nationList;
    }

    public void setNationList(List<Nation> nationList) {
        _nationList.postValue(nationList);
    }
}
