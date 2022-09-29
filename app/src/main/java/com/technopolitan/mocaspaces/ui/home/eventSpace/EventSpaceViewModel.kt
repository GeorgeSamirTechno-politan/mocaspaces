package com.technopolitan.mocaspaces.ui.home.eventSpace

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.home.PaxFilterDataModule
import com.technopolitan.mocaspaces.data.remote.EventSpaceRemote
import com.technopolitan.mocaspaces.data.remote.PaxFilterRemote
import com.technopolitan.mocaspaces.databinding.FilterPaxLayoutBinding
import com.technopolitan.mocaspaces.models.location.mappers.LocationPaxMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.utilities.Constants
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject

class EventSpaceViewModel @Inject constructor(
    private var eventSpaceRemote: EventSpaceRemote,
    private var paxFilterRemote: PaxFilterRemote,
    private var paxFilterDataModule: PaxFilterDataModule
) :
    BaseViewModel<List<MeetingRoomMapper?>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var type: Int? = null
    private var id: Int? = null
    private var fromPax: Int? = null
    private var toPax: Int? = null
    private var eventSpaceListMediator: SingleLiveEvent<MutableList<MeetingRoomMapper?>> =
        SingleLiveEvent()
    private var paxFilterMediator: SingleLiveEvent<ApiStatus<List<LocationPaxMapper>>> =
        SingleLiveEvent()

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
        eventSpaceListMediator.postValue(mutableListOf())
        paxFilterMediator.postValue(LoadingStatus())
        setPaxFilterRequest()
    }

    fun initPaxFilterDataModule(
        binding: FilterPaxLayoutBinding,
        featureId: Int,
        list: List<LocationPaxMapper>,
        callBack: () -> Unit
    ) {
        paxFilterDataModule.init(binding, featureId, list) {
            if (it.selected) {
                fromPax = it.fromPax
                toPax = it.toPax
            } else {
                fromPax = null
                toPax = null
            }
            callBack()
            refreshAll()
        }
    }

    private fun setPaxFilterRequest() {
        paxFilterMediator = paxFilterRemote.getPaxFilter(Constants.eventTypeId)
    }

    fun getPaxLiveData(): LiveData<ApiStatus<List<LocationPaxMapper>>> {
        return paxFilterMediator
    }

    private fun setEventSpaceRequest() {
        if (hasLoadMore())
            apiMediatorLiveData =
                eventSpaceRemote.getEventSpace(pageNumber, pageSize, type, id, fromPax, toPax)
    }

    fun getEventSpaceApi(): LiveData<ApiStatus<List<MeetingRoomMapper?>>> = apiMediatorLiveData

    fun getEventSpaceListLiveData(): LiveData<MutableList<MeetingRoomMapper?>> =
        eventSpaceListMediator

    fun setEventSpaceListMediator(newList: MutableList<MeetingRoomMapper?>) {
        val list: MutableList<MeetingRoomMapper?> = mutableListOf()
        if (eventSpaceListMediator.value != null) {
            val oldList = eventSpaceListMediator.value!!
            if (oldList.isNotEmpty()) {
                if (oldList[oldList.lastIndex] == null)
                    oldList.removeAt(oldList.lastIndex)
                list.addAll(oldList)
            }
        }
        list.addAll(newList)
        if (hasLoadMore()) list.add(null)
        eventSpaceListMediator.postValue(list)
    }

    fun removeSourceFromEventSpaceApi() {
        apiMediatorLiveData.removeSource(eventSpaceRemote.getSource())
    }


    fun loadMore() {
        pageNumber += 1
        setEventSpaceRequest()
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
        eventSpaceListMediator.postValue(mutableListOf())
        removeSourceFromEventSpaceApi()
        setEventSpaceRequest()
    }


    fun updateEventSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }
}