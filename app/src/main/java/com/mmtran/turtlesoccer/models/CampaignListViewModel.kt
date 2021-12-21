package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CampaignListViewModel : ViewModel() {

    private val _campaignList: MutableLiveData<List<Campaign>> = MutableLiveData()

    val campaignList: LiveData<List<Campaign>>
        get() = _campaignList

    fun setCampaignList(campaignList: List<Campaign>) {
        _campaignList.postValue(campaignList)
    }
}
