package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompResultsViewModel : ViewModel() {

    private val _competition: MutableLiveData<Competition> = MutableLiveData()

    val competition: LiveData<Competition>
        get() = _competition

    fun setCompetition(competition: Competition) {
        _competition.postValue(competition)
    }
}
