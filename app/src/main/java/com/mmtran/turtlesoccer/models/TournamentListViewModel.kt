package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TournamentListViewModel : ViewModel() {

    private val _tournamentList: MutableLiveData<List<Tournament>> = MutableLiveData()

    val tournamentList: LiveData<List<Tournament>>
        get() = _tournamentList

    fun setTournamentList(tournamentList: List<Tournament>) {
        _tournamentList.postValue(tournamentList)
    }
}
