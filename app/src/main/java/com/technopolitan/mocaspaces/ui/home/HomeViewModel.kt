package com.technopolitan.mocaspaces.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.EventSpaceRemote
import com.technopolitan.mocaspaces.data.remote.MeetingRoomRemote
import com.technopolitan.mocaspaces.data.remote.SearchHintRemote
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private var searchHintRemote: SearchHintRemote,
    private var workSpaceRemote: WorkSpaceRemote,
    private var meetingRoomRemote: MeetingRoomRemote,
    private var eventSpaceRemote: EventSpaceRemote
) :
    ViewModel() {

    private val workSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val meetingRoomPageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val eventSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val bizLoungePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val locationMediator: MediatorLiveData<Location?> = MediatorLiveData()
    private var workSpaceMediator: MediatorLiveData<ApiStatus<List<WorkSpaceMapper>>> =
        MediatorLiveData()
    private var meetingRoomMediator: MediatorLiveData<ApiStatus<List<MeetingRoomMapper>>> =
        MediatorLiveData()
    private var eventSpaceMediator: MediatorLiveData<ApiStatus<List<MeetingRoomMapper>>> =
        MediatorLiveData()
    private var searchHintMediator: MediatorLiveData<ApiStatus<List<SearchHintMapper>>> =
        MediatorLiveData()
    private val viewTypeMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val pageSize: Int = 10
    private var workSpaceRemainingPage = 1
    private var meetingRemainingPage = 1
    private var eventRemainingPage = 1


    init {
        workSpacePageNumberMediator.value = 1
        meetingRoomPageNumberMediator.value = 1
        eventSpacePageNumberMediator.value = 1
        bizLoungePageNumberMediator.value = 1
        viewTypeMediator.value = 1
        setSearchHintRequest()
        setWorkSpaceRequest()
        setMeetingRoomRequest()
        setEventSpaceRequest()
    }

    private fun setSearchHintRequest() {
        searchHintMediator = searchHintRemote.getAllSearchHint()
    }

    fun getSearchHintApi(): LiveData<ApiStatus<List<SearchHintMapper>>> = searchHintMediator

    private fun setWorkSpaceRequest() {
        workSpaceMediator = workSpaceRemote.getWorkSpace(
            workSpacePageNumberMediator.value!!,
            pageSize,
            locationMediator.value
        )
    }

    fun getWorkSpaceList(): LiveData<ApiStatus<List<WorkSpaceMapper>>> = workSpaceMediator

    private fun setMeetingRoomRequest() {
        meetingRoomMediator = meetingRoomRemote.getMeetingRoom(
            meetingRoomPageNumberMediator.value!!,
            pageSize,
            locationMediator.value
        )
    }

    fun getMeetingRoomList(): LiveData<ApiStatus<List<MeetingRoomMapper>>> = meetingRoomMediator

    private fun setEventSpaceRequest() {
        eventSpaceMediator = eventSpaceRemote.getEventSpace(meetingRoomPageNumberMediator.value!!, pageSize, locationMediator.value)
    }

    fun getEventRoomList(): LiveData<ApiStatus<List<MeetingRoomMapper>>> = eventSpaceMediator

    fun updateWorkSpaceRemainingPage(remaining: Int){
        this.workSpaceRemainingPage = remaining
    }

    fun updateMeetingRoomPage(remaining: Int){
        this.meetingRemainingPage = remaining
    }

    fun updateEventSpacePage(remaining: Int){
        this.eventRemainingPage = remaining
    }

    fun resetWorkSpace(){
//        workSpaceMediator.value = LoadingStatus()
//        workSpaceRemainingPage = 1
//        setWorkSpaceRequest()
    }

    fun resetEventSpace(){
//        eventSpaceMediator.value = LoadingStatus()
//        eventRemainingPage = 1
//        setEventSpaceRequest()
    }
    fun resetMeeting(){
//        meetingRoomMediator.value = LoadingStatus()
//        meetingRemainingPage = 1
//        setMeetingRoomRequest()
    }

    fun updatePageNumber() {
        when(viewTypeMediator.value){
            1->{
                if(workSpaceRemainingPage > 0){
                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
                    setWorkSpaceRequest()
                }
            }
            2->{
                if(meetingRemainingPage > 0){
                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
                    setMeetingRoomRequest()
                }
            }
            3->{
                if(eventRemainingPage > 0){
                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
                    setEventSpaceRequest()
                }
            }
            4->{

            }
        }
    }

    fun setLocation(location: Location) {
        locationMediator.value =location
    }

    fun setViewType(i: Int) {
        viewTypeMediator.value = i
    }


}