package com.technopolitan.mocaspaces.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.imageview.ShapeableImageView
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.main.CustomBottomNavigationModule
import com.technopolitan.mocaspaces.data.remote.MainRemote
import com.technopolitan.mocaspaces.databinding.CustomBottomNavigationLayoutBinding
import com.technopolitan.mocaspaces.modules.ConnectionLiveDataModule
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRemote: MainRemote,
    private var connectionLiveDataModule: ConnectionLiveDataModule,
    private val customBottomNavigationModule: CustomBottomNavigationModule
) : BaseViewModel<Any>() {
    private var networkChangeMediator = MediatorLiveData<Boolean>()


    fun updateNetworkChangeMediator() {
        networkChangeMediator.addSource(connectionLiveDataModule) {
            networkChangeMediator.postValue(it)
        }
    }

    fun connectionChangeLiveData(): LiveData<Boolean> = networkChangeMediator

    fun initCustomBottomNavigationModule(
        binding: CustomBottomNavigationLayoutBinding,
        myPassTab: ShapeableImageView,
        viewLifecycleOwner: LifecycleOwner
    ){
        customBottomNavigationModule.init(binding, myPassTab, viewLifecycleOwner)
    }

}