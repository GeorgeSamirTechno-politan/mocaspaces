package com.technopolitan.mocaspaces.ui.home.workSpace

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject

class WorkSpaceViewModel @Inject constructor(
    private var workSpaceRemote: WorkSpaceRemote
) :
    BaseViewModel<List<WorkSpaceMapper?>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var location: Location? = null
    private var type: Int? = null
    private var id: Int? = null
    private var workSpaceListMediator: SingleLiveEvent<MutableList<WorkSpaceMapper?>> =
        SingleLiveEvent()
    var scrolledPosition = 0

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
        workSpaceListMediator.postValue(mutableListOf())
    }

    private fun setWorkSpaceRequest() {
        if (hasLoadMore())
            apiMediatorLiveData =
                workSpaceRemote.getWorkSpace(pageNumber, pageSize, type, id, location)
    }

    fun getWorkSpaceList(): LiveData<ApiStatus<List<WorkSpaceMapper?>>> = apiMediatorLiveData


    fun getWorkSpaceListLiveData(): LiveData<MutableList<WorkSpaceMapper?>> = workSpaceListMediator

    fun setWorkSpaceListMediator(newList: MutableList<WorkSpaceMapper?>) {
        val list: MutableList<WorkSpaceMapper?> = mutableListOf()
        if (workSpaceListMediator.value != null) {
            val oldList = workSpaceListMediator.value!!
            if (oldList.isNotEmpty()) {
                if (oldList[oldList.lastIndex] == null)
                    oldList.removeAt(oldList.lastIndex)
                list.addAll(oldList)
            }
        }
        list.addAll(newList)
        if (hasLoadMore()) list.add(null)
        workSpaceListMediator.postValue(list)
    }


    fun loadMore() {
        pageNumber += 1
        setWorkSpaceRequest()
    }

    fun hasLoadMore(): Boolean {
        Log.d(javaClass.name, "hasLoadMore: $remainingPage")
        return remainingPage > 0
    }


    fun setFilter(type: Int?, id: Int?) {
        this.type = type
        this.id = id
        refreshAll()

    }

    fun refresh() {
        remainingPage = 1
        pageNumber = 1
//        type = null
//        id = null
        refreshAll()
    }

    private fun refreshAll() {
        remainingPage = 1
        pageNumber = 1
        workSpaceListMediator.postValue(mutableListOf())
        scrolledPosition = 0
        setWorkSpaceRequest()
    }

    fun setLocation(location: Location?) {
        this.location = location
    }


    fun updateWorkSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }

    fun updateItem(item: WorkSpaceMapper, position: Int) {
        val list = workSpaceListMediator.value!!
        list[position] = item
        scrolledPosition = position
        workSpaceListMediator.postValue(list)
    }

    fun updateItem(locationId: Int, updatedFavourite: Boolean) {
        try {
            val list = workSpaceListMediator.value!!
            val item = list.find { it?.id == locationId }
            val position = list.indexOf(item)
            list[position]?.isFavourite = updatedFavourite
            scrolledPosition = position
            workSpaceListMediator.postValue(list)
        } catch (e: Exception) {
            Log.e(javaClass.name, "updateItem: ", e)
        }
    }

    fun updateDataAgainToView() {
        val list = workSpaceListMediator.value!!
        workSpaceListMediator.postValue(list)
    }


}