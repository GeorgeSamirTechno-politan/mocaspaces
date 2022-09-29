package com.technopolitan.mocaspaces.data.remote

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.mappers.LocationDetailsMapper
import com.technopolitan.mocaspaces.models.location.response.LocationDetailsResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class WorkSpaceDetailsRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule,
    private var dateTimeModule: DateTimeModule
) : BaseRemote<LocationDetailsMapper, LocationDetailsResponse>() {

    private var locationId: Int = 0

    fun getWorkSpaceDetails(locationId: Int): SingleLiveEvent<ApiStatus<LocationDetailsMapper>> {
        this.locationId = locationId
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<LocationDetailsResponse>> {
        return networkModule.provideService(BaseUrl.locationApi)
            .getWorkSpaceDetails(locationId = locationId)
    }

    override fun handleResponse(it: HeaderResponse<LocationDetailsResponse>): ApiStatus<LocationDetailsMapper> {
        return if (it.succeeded)
            SuccessStatus(
                it.message,
                LocationDetailsMapper(context, it.data!!, spannableStringModule, dateTimeModule)
            )
        else FailedStatus(it.message)
    }
}