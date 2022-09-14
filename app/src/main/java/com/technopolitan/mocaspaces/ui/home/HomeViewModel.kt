package com.technopolitan.mocaspaces.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var workSpaceRemote: WorkSpaceRemote) :
    ViewModel() {

    private val workSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val meetingRoomPageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val eventSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val bizLoungePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val workSpaceMediator: MediatorLiveData<ApiStatus<List<WorkSpaceMapper>>> =
        MediatorLiveData()
    private val pageSize: Int = 10

    init {
        workSpacePageNumberMediator.value = 1
        meetingRoomPageNumberMediator.value = 1
        eventSpacePageNumberMediator.value = 1
        bizLoungePageNumberMediator.value = 1
        setWorkSpaceRequest()
    }

    fun setWorkSpaceRequest() {
        workSpaceMediator.value =
            workSpaceRemote.getWorkSpace(workSpacePageNumberMediator.value!!, pageSize).value
    }

    fun updatePageNumber(mediator: MediatorLiveData<Int>) {
        (mediator.value!!.plus(1)).also { mediator.value = it }
    }


}