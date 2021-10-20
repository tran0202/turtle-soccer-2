package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamListViewModel : ViewModel() {

    private val _teamList: MutableLiveData<List<Team>> = MutableLiveData()

    val teamList: LiveData<List<Team>>
        get() = _teamList

    fun setTeamList(teamList: List<Team>) {
        _teamList.postValue(teamList)
    }
}
