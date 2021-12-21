package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CampaignViewModel : ViewModel() {

    private val _campaign: MutableLiveData<Campaign> = MutableLiveData()

    val campaign: LiveData<Campaign>
        get() = _campaign

    fun setCampaign(campaign: Campaign) {
        _campaign.postValue(campaign)
    }
}
