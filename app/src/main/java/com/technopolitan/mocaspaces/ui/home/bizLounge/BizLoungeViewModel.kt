package com.technopolitan.mocaspaces.ui.home.bizLounge

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.LoadingStatus
import com.technopolitan.mocaspaces.data.remote.BizLoungeRemote
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeMapper
import javax.inject.Inject

class BizLoungeViewModel @Inject constructor(
    private var bizLoungeRemote: BizLoungeRemote,
) :
    BaseViewModel<List<BizLoungeMapper?>>() {

    private var pageNumber = 1
    private val pageSize: Int = 10
    private var remainingPage: Int = 1
    private var location: Location? = null
    private var type: Int? = null
    private var id: Int? = null
    private var bizLoungeListMediator: MediatorLiveData<MutableList<BizLoungeMapper?>> =
        MediatorLiveData()

    init {
        pageNumber = 1
        apiMediatorLiveData.postValue(LoadingStatus())
        bizLoungeListMediator.postValue(mutableListOf())
    }

    private fun setApiRequest() {
        if (hasLoadMore())
            apiMediatorLiveData =
                bizLoungeRemote.getBizLounge(pageNumber, pageSize, type, id, location)
    }

    fun getApiList(): LiveData<ApiStatus<List<BizLoungeMapper?>>> = apiMediatorLiveData


    fun getAdapterListLiveData(): LiveData<MutableList<BizLoungeMapper?>> = bizLoungeListMediator

    fun setBizLoungeListMediator(newList: MutableList<BizLoungeMapper?>) {
        val list: MutableList<BizLoungeMapper?> = mutableListOf()
        if (bizLoungeListMediator.value != null) {
            val oldList = bizLoungeListMediator.value!!
            if (oldList.isNotEmpty()) {
                if (oldList[oldList.lastIndex] == null)
                    oldList.removeAt(oldList.lastIndex)
                list.addAll(oldList)
            }
        }
        list.addAll(newList)
        if (hasLoadMore()) list.add(null)
        bizLoungeListMediator.postValue(list)
    }

    fun removeSourceBizLoungeApi() {
        apiMediatorLiveData.removeSource(bizLoungeRemote.getSource())
    }


    fun loadMore() {
        pageNumber += 1
        setApiRequest()
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
        bizLoungeListMediator.postValue(mutableListOf())
        removeSourceBizLoungeApi()
        setApiRequest()
    }


    fun updateWorkSpaceRemainingPage(remaining: Int) {
        this.remainingPage = remaining
    }
}

