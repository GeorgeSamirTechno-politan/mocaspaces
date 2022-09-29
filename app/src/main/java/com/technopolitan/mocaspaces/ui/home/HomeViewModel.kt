package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.SearchHintRemote
import com.technopolitan.mocaspaces.models.location.mappers.HomeSearchMapper
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject


open class HomeViewModel @Inject constructor(
    private var searchHintRemote: SearchHintRemote,
    private val context: Context
) : ViewModel() {

    private val workSpaceFilterMediator: SingleLiveEvent<SearchHintMapper> = SingleLiveEvent()
    private val meetingSpaceFilterMediator: SingleLiveEvent<SearchHintMapper> = SingleLiveEvent()
    private val eventSpaceFilterMediator: SingleLiveEvent<SearchHintMapper> = SingleLiveEvent()
    private val bizLoungeFilterMediator: SingleLiveEvent<SearchHintMapper> = SingleLiveEvent()

    private val locationMediator: SingleLiveEvent<Location?> = SingleLiveEvent()

    private var searchHintApiMediator: SingleLiveEvent<ApiStatus<List<SearchHintMapper>>> =
        SingleLiveEvent()
    private var searchHintListMediator: SingleLiveEvent<List<SearchHintMapper>> =
        SingleLiveEvent()
    private var viewTypeMutable: MutableLiveData<Int> = MutableLiveData()
    private val homeSearchMapperList: MutableList<HomeSearchMapper> = mutableListOf()
    private var selectedLocationId: Int = 0
    private val backFromDetailsLiveEvent: MutableLiveData<Boolean> = MutableLiveData()


    init {
        workSpaceFilterMediator.value = SearchHintMapper()
        meetingSpaceFilterMediator.value = SearchHintMapper()
        eventSpaceFilterMediator.value = SearchHintMapper()
        bizLoungeFilterMediator.value = SearchHintMapper()
        initSearchMapperList()
        viewTypeMutable.postValue(1)
        searchHintApiMediator.value = LoadingStatus()
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

    fun getMeetingSpaceFilterLiveData(): LiveData<SearchHintMapper> = meetingSpaceFilterMediator

    fun getEventSpaceFilterLiveData(): LiveData<SearchHintMapper> = eventSpaceFilterMediator

    fun getBizLoungeFilterLiveData(): LiveData<SearchHintMapper> = bizLoungeFilterMediator

    fun getBackFromDetailsLiveData(): LiveData<Boolean> = backFromDetailsLiveEvent

    fun setBackFromDetailsLiveData(value: Boolean) = backFromDetailsLiveEvent.postValue(value)


    fun getViewType(): LiveData<Int> = viewTypeMutable

    private fun setSearchHintRequest() {
        searchHintApiMediator = searchHintRemote.getAllSearchHint()
    }

    fun getSearchHintApi(): LiveData<ApiStatus<List<SearchHintMapper>>> = searchHintApiMediator


    fun setLocation(location: Location?) {
        locationMediator.postValue(location)
    }

    fun getLocationLiveData(): LiveData<Location?> = locationMediator

    fun setViewType(i: Int) {
        viewTypeMutable.postValue(i)
    }


    fun updateSearchHintList() {
        val updatedList = mutableListOf<SearchHintMapper>()
        searchHintApiMediator.value?.let { status ->
            status.data?.let { list ->
                list.forEach { item ->
                    when (viewTypeMutable.value) {
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
        when (viewTypeMutable.value) {
            1 -> workSpaceFilterMediator.postValue(it)
            2 -> meetingSpaceFilterMediator.postValue(it)
            3 -> eventSpaceFilterMediator.postValue(it)
            4 -> bizLoungeFilterMediator.postValue(it)
        }
    }

    fun setSelectedLocationId(id: Int) {
        this.selectedLocationId = id
    }

    fun getSelectedLocationId(): Int {
        return this.selectedLocationId
    }

    fun updateDataAgainToView() {
        setViewType(1)
        updateSearchHintList()
    }

}