package com.technopolitan.mocaspaces.models.location.bizLounge

import android.content.Context
import com.technopolitan.mocaspaces.models.location.mappers.WorkTimeMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class BizLoungeMapper constructor(
    private var dateTimeModule: DateTimeModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) {

    var id: Int = 0
    var image: String = ""
    var locationName: String = ""
    var address: String = ""
    val workTimeMapper: WorkTimeMapper =
        WorkTimeMapper(dateTimeModule, spannableStringModule, context)

    fun init(bizLoungeResponse: BizLoungeResponse): BizLoungeMapper {
        id = bizLoungeResponse.id
        if (bizLoungeResponse.locationDistrict != null && bizLoungeResponse.locationCity != null)
            address =
                "${bizLoungeResponse.locationDistrict.name}, ${bizLoungeResponse.locationCity.name}"
        locationName = bizLoungeResponse.name
        workTimeMapper.init(bizLoungeResponse.workingHourResponse)
        if (bizLoungeResponse.imagesResponse != null)
            image =
                BaseUrl.baseForImage(BaseUrl.locationApi) + bizLoungeResponse.imagesResponse.locationImageFilePath
        return this
    }


}