package com.technopolitan.mocaspaces.ui.home.meetingSpace

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.MeetingRoomRemote
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import javax.inject.Inject

class MeetingRoomViewModel @Inject constructor(private var meetingRoomRemote: MeetingRoomRemote) :
    BaseViewModel<List<MeetingRoomMapper>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var type: Int? = null
    private var id: Int? = null
    private var meetingSpaceListMediator: MediatorLiveData<MutableList<MeetingRoomMapper?>> =
        MediatorLiveData()

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
        meetingSpaceListMediator.postValue(mutableListOf())
    }

    private fun setMeetingSpaceRequest() {
        if (hasLoadMore())
            apiMediatorLiveData = meetingRoomRemote.getMeetingRoom(pageNumber, pageSize, type, id)
    }

    fun getMeetingSpaceApi(): LiveData<ApiStatus<List<MeetingRoomMapper>>> = apiMediatorLiveData

    fun getMeetingSpaceListLiveData(): LiveData<MutableList<MeetingRoomMapper?>> =
        meetingSpaceListMediator

    fun setMeetingSpaceListMediator(newList: MutableList<MeetingRoomMapper?>) {
        val list: MutableList<MeetingRoomMapper?> = mutableListOf()
        if (meetingSpaceListMediator.value != null) {
            val oldList = meetingSpaceListMediator.value!!
            if (oldList.isNotEmpty()) {
                if (oldList[oldList.lastIndex] == null)
                    oldList.removeAt(oldList.lastIndex)
                list.addAll(oldList)
            }
        }
        list.addAll(newList)
        if (hasLoadMore()) list.add(null)
        meetingSpaceListMediator.postValue(list)
    }

    fun removeSourceFromMeetingSpaceApi() {
        apiMediatorLiveData.removeSource(meetingRoomRemote.getSource())
    }


    fun loadMore() {
        pageNumber += 1
        setMeetingSpaceRequest()
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
        meetingSpaceListMediator.postValue(mutableListOf())
        removeSourceFromMeetingSpaceApi()
        setMeetingSpaceRequest()
    }


    fun updateMeetingSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }
}