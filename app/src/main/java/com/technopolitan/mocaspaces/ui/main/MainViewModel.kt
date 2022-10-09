package com.technopolitan.mocaspaces.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.imageview.ShapeableImageView
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.main.CustomBottomNavigationModule
import com.technopolitan.mocaspaces.data.remote.RefreshFCMTokenRemote
import com.technopolitan.mocaspaces.databinding.CustomBottomNavigationLayoutBinding
import com.technopolitan.mocaspaces.modules.ConnectionLiveDataModule
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private var connectionLiveDataModule: ConnectionLiveDataModule,
    private val customBottomNavigationModule: CustomBottomNavigationModule,
    private val refreshTokenRemote: RefreshFCMTokenRemote
) : BaseViewModel<Any>() {
    private var networkChangeMediator = MediatorLiveData<Boolean>()
    private var startApplicationMutable: MutableLiveData<Boolean> = MutableLiveData()
    private var refreshFCMTokenSingleEvent: SingleLiveEvent<ApiStatus<String>> = SingleLiveEvent()

    fun updateNetworkChangeMediator() {
        networkChangeMediator.addSource(connectionLiveDataModule) {
            networkChangeMediator.postValue(it)
        }
    }

    fun setStartApplication(startApplication: Boolean = false) {
        startApplicationMutable.postValue(startApplication)
    }

    fun getStartApplication(): MutableLiveData<Boolean> {
        return startApplicationMutable
    }

    fun connectionChangeLiveData(): LiveData<Boolean> = networkChangeMediator

    fun initCustomBottomNavigationModule(
        binding: CustomBottomNavigationLayoutBinding,
        myPassTab: ShapeableImageView,
        viewLifecycleOwner: LifecycleOwner
    ) {
        customBottomNavigationModule.init(binding, myPassTab, viewLifecycleOwner)
    }

    fun refreshFCMToken(newToken: String){
        refreshFCMTokenSingleEvent = refreshTokenRemote.handleRefreshNewToken(newToken)
    }

    fun getRefreshFCMToken(): LiveData<ApiStatus<String>> = refreshFCMTokenSingleEvent



}