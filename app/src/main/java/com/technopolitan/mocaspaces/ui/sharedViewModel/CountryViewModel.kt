package com.technopolitan.mocaspaces.ui.sharedViewModel

import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.remote.CountryRemote
import javax.inject.Inject

class CountryViewModel @Inject constructor(private var countryRemote: CountryRemote) :
    BaseViewModel<List<CountryMapper>>() {

        fun setCountryRequest(){
            apiMediatorLiveData = countryRemote.getCountry()
        }

    fun getCountry(): LiveData<ApiStatus<List<CountryMapper>>> = apiMediatorLiveData
}