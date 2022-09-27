package com.technopolitan.mocaspaces.data.remote

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeMapper
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeResponse
import com.technopolitan.mocaspaces.models.location.request.LocationRequest
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class BizLoungeRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var dateTimeModule: DateTimeModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) : BaseRemote<List<BizLoungeMapper?>, List<BizLoungeResponse>>() {

    private var pageNumber: Int = 1
    private var pageSize: Int = 10
    private var type: Int? = null
    private var id: Int? = null
    fun getBizLounge(
        pageNumber: Int = 1,
        pageSize: Int = 10,
        type: Int? = null,
        id: Int? = null,
    ): MediatorLiveData<ApiStatus<List<BizLoungeMapper?>>> {
        this.pageNumber = pageNumber
        this.pageSize = pageSize
        this.id = id
        this.type = type
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<List<BizLoungeResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getAllBizLounge(
            request = LocationRequest(
                pageNumber = pageNumber,
                pageSize = pageSize,
                type = type,
                id = id
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<List<BizLoungeResponse>>): ApiStatus<List<BizLoungeMapper?>> {
        val list = mutableListOf<BizLoungeMapper?>()
        return if (it.succeeded) {
            if (it.data == null)
                list.add(null)
            else
                it.data.let {
                    it.forEach { item ->
                        list.add(
                            BizLoungeMapper(
                                dateTimeModule,
                                context,
                                spannableStringModule
                            ).init(item)
                        )
                    }
                }
            SuccessStatus(message = "", list, getRemaining(it.pageTotal!!, pageSize, pageSize))
        } else FailedStatus(it.message)
    }
}