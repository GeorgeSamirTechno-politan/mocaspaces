package com.technopolitan.mocaspaces.bases

import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent

abstract class BaseViewModel<T> : ViewModel() {

    internal var apiMediatorLiveData: SingleLiveEvent<ApiStatus<T>> = SingleLiveEvent()


}