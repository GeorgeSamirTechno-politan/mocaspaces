package com.technopolitan.mocaspaces.bases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus

abstract class BaseViewModel<T> : ViewModel() {

    internal var apiMutableLiveData: MutableLiveData<ApiStatus<T>> = MutableLiveData()


}