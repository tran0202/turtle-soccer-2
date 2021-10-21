package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NationListViewModel : ViewModel() {

    private val _nationList: MutableLiveData<List<Nation>> = MutableLiveData()

    val nationList: LiveData<List<Nation>>
        get() = _nationList

    fun setNationList(nationList: List<Nation>) {
        _nationList.postValue(nationList)
    }
}
