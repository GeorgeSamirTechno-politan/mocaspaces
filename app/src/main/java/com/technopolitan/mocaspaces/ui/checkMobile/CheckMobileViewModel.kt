package com.technopolitan.mocaspaces.ui.checkMobile

import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import javax.inject.Inject

class CheckMobileViewModel @Inject constructor(private var checkMobileRemote: CheckMobileRemote) :
    BaseViewModel<String>() {

    fun checkMobile(mobile: String) {
        this.apiMutableLiveData = checkMobileRemote.verifyMobile(mobile)
    }

    fun handleCheckMobileApi(): LiveData<ApiStatus<String>> = apiMutableLiveData
}