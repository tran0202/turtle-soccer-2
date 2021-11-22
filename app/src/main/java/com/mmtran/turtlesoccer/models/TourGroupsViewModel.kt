package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TourGroupsViewModel : ViewModel() {

    private val _tournament: MutableLiveData<Tournament> = MutableLiveData()

    val tournament: LiveData<Tournament>
        get() = _tournament

    fun setTournament(tournament: Tournament) {
        _tournament.postValue(tournament)
    }
}
