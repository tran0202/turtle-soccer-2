package com.mmtran.turtlesoccer.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StageViewModel : ViewModel() {

    private val _stage: MutableLiveData<Stage> = MutableLiveData()

    val stage: LiveData<Stage>
        get() = _stage

    fun setStage(stage: Stage) {
        _stage.postValue(stage)
    }
}
