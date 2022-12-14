package com.technopolitan.mocaspaces.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.remote.MainRemote
import com.technopolitan.mocaspaces.modules.ConnectionLiveDataModule
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRemote: MainRemote,
    private var connectionLiveDataModule: ConnectionLiveDataModule
) : BaseViewModel<Any>() {
    private var networkChangeMediator = MediatorLiveData<Boolean>()


    fun updateNetworkChangeMediator() {
        networkChangeMediator.addSource(connectionLiveDataModule) {
            networkChangeMediator.postValue(it)
        }
    }

    fun connectionChangeLiveData(): LiveData<Boolean> = networkChangeMediator

}