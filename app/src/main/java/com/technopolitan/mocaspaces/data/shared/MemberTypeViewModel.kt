package com.technopolitan.mocaspaces.data.shared

import androidx.lifecycle.MutableLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.remote.MemberTypeRemote
import javax.inject.Inject

class MemberTypeViewModel @Inject constructor(private var memberTypeRemote: MemberTypeRemote) :
    BaseViewModel<List<DropDownMapper>>() {

    fun setMemberTypeRequest() {
        apiMutableLiveData = memberTypeRemote.getMemberType()
    }

    fun getMemberTypes(): MutableLiveData<ApiStatus<List<DropDownMapper>>> {
        return apiMutableLiveData
    }
}