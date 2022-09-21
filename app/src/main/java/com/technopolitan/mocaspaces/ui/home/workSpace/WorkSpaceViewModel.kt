package com.technopolitan.mocaspaces.ui.home.workSpace

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.AddFavouriteWorkSpaceRemote
import com.technopolitan.mocaspaces.data.remote.DeleteWorkSpaceFavouriteRemote
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import javax.inject.Inject

class WorkSpaceViewModel @Inject constructor(
    private var workSpaceRemote: WorkSpaceRemote,
    private var addFavouriteWorkSpaceRemote: AddFavouriteWorkSpaceRemote,
    private var deleteWorkSpaceFavouriteRemote: DeleteWorkSpaceFavouriteRemote
) :
    BaseViewModel<List<WorkSpaceMapper>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var location: Location? = null
    private var type: Int? = null
    private var id: Int? = null
    private var favouriteMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
    }

    private fun setWorkSpaceRequest() {
        apiMediatorLiveData = workSpaceRemote.getWorkSpace(pageNumber, pageSize, type, id, location)
    }

    fun getWorkSpaceList(): LiveData<ApiStatus<List<WorkSpaceMapper>>> = apiMediatorLiveData

    fun setAddFavourite(locationId: Int) {
        favouriteMediator = addFavouriteWorkSpaceRemote.addFavourite(locationId)
    }

    fun getFavourite(): LiveData<ApiStatus<String>> = favouriteMediator

    fun removeSourceOfAddFavourite() {
        favouriteMediator.removeSource(addFavouriteWorkSpaceRemote.getSource())
    }

    fun setDeleteFavourite(locationId: Int) {
        favouriteMediator = deleteWorkSpaceFavouriteRemote.deleteFavourite(locationId)
    }


    fun removeSourceOfDeleteFavourite() {
        favouriteMediator.removeSource(deleteWorkSpaceFavouriteRemote.getSource())
    }


    fun removeSourceFromWorkSpaceApi() {
        apiMediatorLiveData.removeSource(workSpaceRemote.getSource())
    }


    fun loadMore() {
        pageNumber += 1
        setWorkSpaceRequest()
    }

    fun hasLoadMore(): Boolean = remainingPage > 0


    fun setFilter(type: Int?, id: Int?) {
        this.type = type
        this.id = id
        apiMediatorLiveData.removeSource(workSpaceRemote.getSource())
        apiMediatorLiveData.postValue(LoadingStatus())
        setWorkSpaceRequest()
    }

    fun refresh() {
        remainingPage = 1
        pageNumber = 1
//        type = null
//        id = null
        apiMediatorLiveData.removeSource(workSpaceRemote.getSource())
        apiMediatorLiveData.postValue(LoadingStatus())
        setWorkSpaceRequest()
    }

    fun setLocation(location: Location?) {
        this.location = location
    }


    fun updateWorkSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }


}