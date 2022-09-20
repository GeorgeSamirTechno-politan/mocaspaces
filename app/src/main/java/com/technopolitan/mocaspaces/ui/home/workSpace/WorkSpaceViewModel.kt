package com.technopolitan.mocaspaces.ui.home.workSpace

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import javax.inject.Inject

class WorkSpaceViewModel @Inject constructor(private var workSpaceRemote: WorkSpaceRemote) :
    BaseViewModel<List<WorkSpaceMapper>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var location: Location? = null
    private var type: Int? = null
    private var id: Int? = null
    private var loadMoreMediator: MediatorLiveData<ApiStatus<List<WorkSpaceMapper>>> =
        MediatorLiveData()

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
    }

    private fun setWorkSpaceRequest() {
        apiMediatorLiveData = workSpaceRemote.getWorkSpace(pageNumber, pageSize, type, id, location)
    }

    fun getWorkSpaceList(): LiveData<ApiStatus<List<WorkSpaceMapper>>> = apiMediatorLiveData

    private fun setLoadMore() {
        loadMoreMediator = workSpaceRemote.getWorkSpace(pageNumber, pageSize, type, id, location)
    }

    fun loadMoreLiveData(): LiveData<ApiStatus<List<WorkSpaceMapper>>> = loadMoreMediator


    fun loadMore() {
        pageNumber += 1
        setLoadMore()
    }

    fun hasLoadMore(): Boolean = remainingPage > 0


    fun setFilter(type: Int?, id: Int?) {
        this.type = type
        this.id = id
        apiMediatorLiveData.removeSource(apiMediatorLiveData)
        setWorkSpaceRequest()
    }

    fun refresh() {
        apiMediatorLiveData.removeSource(apiMediatorLiveData)
        remainingPage = 1
        pageNumber = 1
//        type = null
//        id = null

        setWorkSpaceRequest()
    }

    fun setLocation(location: Location?) {
        this.location = location
    }


    fun updateWorkSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }


}