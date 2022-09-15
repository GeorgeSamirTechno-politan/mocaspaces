package com.technopolitan.mocaspaces.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.di.ActivityScope
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import javax.inject.Inject


class HomeViewModel @Inject constructor(private var workSpaceRemote: WorkSpaceRemote) :
    ViewModel() {

    private val workSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val meetingRoomPageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val eventSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val bizLoungePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val locationMediator: MediatorLiveData<Location?> = MediatorLiveData()
    private var workSpaceMediator: MediatorLiveData<ApiStatus<List<WorkSpaceMapper>>> =
        MediatorLiveData()
    private val viewTypeMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val pageSize: Int = 10
    private var workSpaceRemainingPage = 1


    init {
        workSpacePageNumberMediator.value = 1
        meetingRoomPageNumberMediator.value = 1
        eventSpacePageNumberMediator.value = 1
        bizLoungePageNumberMediator.value = 1
        viewTypeMediator.value = 1
        setWorkSpaceRequest()
    }

    private fun setWorkSpaceRequest() {
        workSpaceMediator = workSpaceRemote.getWorkSpace(workSpacePageNumberMediator.value!!, pageSize, locationMediator.value)
    }

    fun getWorkSpaceList(): LiveData<ApiStatus<List<WorkSpaceMapper>>> = workSpaceMediator

    fun updateWorkSpaceRemainingPage(remaining: Int){
        this.workSpaceRemainingPage = remaining
    }

    fun updatePageNumber() {
        when(viewTypeMediator.value){
            1->{
                if(workSpaceRemainingPage > 0){
                    workSpacePageNumberMediator.value = workSpacePageNumberMediator.value!! + 1
                    setWorkSpaceRequest()
                }
            }
            2->{

            }
            3->{

            }
            4->{

            }
        }
    }

    fun setLocation(location: Location) {
        locationMediator.value =location
    }


}