package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompetitionListViewModel : ViewModel() {

    private val _competitionList: MutableLiveData<List<Competition>> = MutableLiveData()

    val competitionList: LiveData<List<Competition>>
        get() = _competitionList

    fun setCompetitionList(competitionList: List<Competition>) {
        _competitionList.postValue(competitionList)
    }
}
