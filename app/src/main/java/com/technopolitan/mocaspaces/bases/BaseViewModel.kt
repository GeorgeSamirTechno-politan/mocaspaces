package com.technopolitan.mocaspaces.bases

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus

abstract class BaseViewModel<T> : ViewModel() {

    internal var apiMediatorLiveData: MediatorLiveData<ApiStatus<T>> = MediatorLiveData()


}