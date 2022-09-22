package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.EventSpaceRemote
import com.technopolitan.mocaspaces.data.remote.MeetingRoomRemote
import com.technopolitan.mocaspaces.data.remote.SearchHintRemote
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.models.location.mappers.HomeSearchMapper
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import javax.inject.Inject


open class HomeViewModel @Inject constructor(
    private var searchHintRemote: SearchHintRemote,
    private var workSpaceRemote: WorkSpaceRemote,
    private var meetingRoomRemote: MeetingRoomRemote,
    private var eventSpaceRemote: EventSpaceRemote,
    private var context: Context
) : ViewModel() {

    private val workSpaceFilterMediator: MediatorLiveData<SearchHintMapper> = MediatorLiveData()
    private val meetingRoomPageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val eventSpacePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val bizLoungePageNumberMediator: MediatorLiveData<Int> = MediatorLiveData()
    private val locationMediator: MediatorLiveData<Location?> = MediatorLiveData()

    private var meetingRoomMediator: MediatorLiveData<ApiStatus<List<MeetingRoomMapper>>> =
        MediatorLiveData()
    private var eventSpaceMediator: MediatorLiveData<ApiStatus<List<MeetingRoomMapper>>> =
        MediatorLiveData()
    private var searchHintApiMediator: MediatorLiveData<ApiStatus<List<SearchHintMapper>>> =
        MediatorLiveData()
    private var searchHintListMediator: MediatorLiveData<List<SearchHintMapper>> =
        MediatorLiveData()
    private var viewType = 1
    private val pageSize: Int = 10
    private val homeSearchMapperList: MutableList<HomeSearchMapper> = mutableListOf()
    private var meetingRemainingPage = 1
    private var eventRemainingPage = 1


    init {
        workSpaceFilterMediator.value = SearchHintMapper()
        initSearchMapperList()
        meetingRoomPageNumberMediator.value = 1
        eventSpacePageNumberMediator.value = 1
        bizLoungePageNumberMediator.value = 1
        searchHintApiMediator.value = LoadingStatus()

        meetingRoomMediator.value = LoadingStatus()
        eventSpaceMediator.value = LoadingStatus()
        searchHintListMediator.value = mutableListOf()
        setSearchHintRequest()
    }

    private fun initSearchMapperList() {
        homeSearchMapperList.add(
            getHomeWorkSpaceMapper()
        )
        homeSearchMapperList.add(
            getHomeMeetingSpaceMapper()
        )
        homeSearchMapperList.add(
            getHomeEventSpaceMapper()
        )
        homeSearchMapperList.add(
            getHomeBizLoungeMapper()
        )
    }

    private fun getHomeWorkSpaceMapper(): HomeSearchMapper = HomeSearchMapper(
        R.color.workspace_color,
        context.getString(R.string.workspace),
        0,
        R.color.text_input_box_work_space_color,
        R.drawable.work_space_bottom_rounded_corner
    )

    private fun getHomeMeetingSpaceMapper(): HomeSearchMapper = HomeSearchMapper(
        R.color.meeting_space_color,
        context.getString(R.string.meeting_space),
        1,
        R.color.text_input_box_meeting_space_color,
        R.drawable.meeting_space_bottom_rounded_corner
    )

    private fun getHomeEventSpaceMapper(): HomeSearchMapper = HomeSearchMapper(
        R.color.event_space_color,
        context.getString(R.string.event_space),
        2,
        R.color.text_input_box_event_space_color,
        R.drawable.event_space_bottom_rounded_corner
    )

    private fun getHomeBizLoungeMapper(): HomeSearchMapper = HomeSearchMapper(
        R.color.biz_lounge_color,
        context.getString(R.string.biz_lounge),
        3,
        R.color.text_input_box_biz_lounge_color,
        R.drawable.biz_lounge_bottom_rounded_corner
    )

    fun getHomeSearchMapperList(): List<HomeSearchMapper> = homeSearchMapperList


    fun getWorkSpaceFilterLiveData(): LiveData<SearchHintMapper> = workSpaceFilterMediator


    fun initAllHomeRequest() {
//        setWorkSpaceRequest()
        setMeetingRoomRequest()
        setEventSpaceRequest()
    }


    fun getViewType(): Int = viewType

    private fun setSearchHintRequest() {
        searchHintApiMediator = searchHintRemote.getAllSearchHint()
    }

    fun getSearchHintApi(): LiveData<ApiStatus<List<SearchHintMapper>>> = searchHintApiMediator


    private fun setMeetingRoomRequest() {
        meetingRoomMediator = meetingRoomRemote.getMeetingRoom(
            meetingRoomPageNumberMediator.value!!,
            pageSize,
            locationMediator.value
        )
    }

    fun getMeetingRoomList(): LiveData<ApiStatus<List<MeetingRoomMapper>>> = meetingRoomMediator

    private fun setEventSpaceRequest() {
        eventSpaceMediator = eventSpaceRemote.getEventSpace(
            meetingRoomPageNumberMediator.value!!,
            pageSize,
            locationMediator.value
        )
    }

    fun getEventRoomList(): LiveData<ApiStatus<List<MeetingRoomMapper>>> = eventSpaceMediator


    fun updateMeetingRoomPage(remaining: Int) {
        this.meetingRemainingPage = remaining
    }

    fun updateEventSpacePage(remaining: Int) {
        this.eventRemainingPage = remaining
    }

    fun resetWorkSpace() {
//        workSpaceMediator.value = LoadingStatus()
//        workSpaceRemainingPage = 1
//        setWorkSpaceRequest()
    }

    fun resetEventSpace() {
//        eventSpaceMediator.value = LoadingStatus()
//        eventRemainingPage = 1
//        setEventSpaceRequest()
    }

    fun resetMeeting() {
//        meetingRoomMediator.value = LoadingStatus()
//        meetingRemainingPage = 1
//        setMeetingRoomRequest()
    }

    fun updatePageNumber() {

//        when (viewType) {
//            1 -> {
////                if (workSpaceRemainingPage > 0) {
////                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
////                    setWorkSpaceRequest()
////                }
//            }
//            2 -> {
//                if (meetingRemainingPage > 0) {
//                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
//                }
//            }
//            3 -> {
//                if (eventRemainingPage > 0) {
//                    meetingRoomPageNumberMediator.value = meetingRoomPageNumberMediator.value!! + 1
//                }
//
//            }
//            4 -> {
//
//            }
//        }
//        updateSearchHintList()
    }

    fun setLocation(location: Location?) {
        locationMediator.postValue(location)
    }

    fun getLocationLiveData(): LiveData<Location?> = locationMediator

    fun setViewType(i: Int) {
        viewType = i
    }


    fun updateSearchHintList() {
        val updatedList = mutableListOf<SearchHintMapper>()
        searchHintApiMediator.value?.let { status ->
            status.data?.let { list ->
                list.forEach { item ->
                    when (viewType) {
                        1 -> if (item.spaceTypeMapper.hasWorkSpace)
                            updatedList.add(item)
                        2 -> if (item.spaceTypeMapper.hasMeeting)
                            updatedList.add(item)
                        3 -> if (item.spaceTypeMapper.hasEvent)
                            updatedList.add(item)
                        4 -> if (item.spaceTypeMapper.hasBizLounge)
                            updatedList.add(item)
                    }

                }
            }
        }
        searchHintListMediator.postValue(updatedList)
    }

    fun getSearchHintListLiveData(): LiveData<List<SearchHintMapper>> = searchHintListMediator


    fun setSearchHint(it: SearchHintMapper) {
        when (viewType) {
            1 -> workSpaceFilterMediator.postValue(it)
        }
    }


}