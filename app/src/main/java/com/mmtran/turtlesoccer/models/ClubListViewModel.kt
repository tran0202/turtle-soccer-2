package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClubListViewModel : ViewModel() {

    private val _clubList: MutableLiveData<List<Club>> = MutableLiveData()

    val clubList: LiveData<List<Club>>
        get() = _clubList

    fun setClubList(clubList: List<Club>) {
        _clubList.postValue(clubList)
    }
}
