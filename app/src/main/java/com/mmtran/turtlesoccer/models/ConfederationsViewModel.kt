package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfederationsViewModel : ViewModel() {

    private val _confederationList: MutableLiveData<List<Confederation>> = MutableLiveData()

    val confederationList: LiveData<List<Confederation>>
        get() = _confederationList

    fun setConfederationList(confederationList: List<Confederation>) {
        _confederationList.postValue(confederationList)
    }
}
