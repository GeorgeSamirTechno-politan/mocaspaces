package com.technopolitan.mocaspaces.ui.workspacePlans

import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpacePlanRemote
import com.technopolitan.mocaspaces.models.shared.PriceResponse
import com.technopolitan.mocaspaces.models.workSpace.WorkSpacePlanMapper
import javax.inject.Inject

class WorkSpacePlansViewModel @Inject constructor(private var workSpacePlanRemote: WorkSpacePlanRemote) :
    BaseViewModel<List<WorkSpacePlanMapper>>() {
    fun setPlanRequest(priceResponse: PriceResponse, currency: String) {
        workSpacePlanRemote.getWorkSpacePlans(priceResponse, currency)
    }

    fun getWorkSpacePlan(): LiveData<ApiStatus<List<WorkSpacePlanMapper>>> = apiMediatorLiveData
}