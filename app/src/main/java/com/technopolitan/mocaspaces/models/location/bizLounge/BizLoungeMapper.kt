package com.technopolitan.mocaspaces.models.location.bizLounge

import android.content.Context
import android.text.Spannable
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.mappers.WorkTimeMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class BizLoungeMapper constructor(private var dateTimeModule: DateTimeModule) {

    var id: Int = 0
    var image: String = ""
    var locationName: String = ""
    var address: String = ""
    val workTimeMapper: WorkTimeMapper = WorkTimeMapper(dateTimeModule)

    fun init(bizLoungeResponse: BizLoungeResponse): BizLoungeMapper {
        id = bizLoungeResponse.id
        address =
            "${bizLoungeResponse.locationDistrict.name}, ${bizLoungeResponse.locationCity.name}"
        locationName = bizLoungeResponse.name
        workTimeMapper.init(bizLoungeResponse.locationWorkingHourResponse)
        if (bizLoungeResponse.locationImagesResponse != null)
            image =
                BaseUrl.baseForImage(BaseUrl.locationApi) + bizLoungeResponse.locationImagesResponse.locationImageFilePath
        return this
    }

    fun getOpenHourText(context: Context, spannableStringModule: SpannableStringModule): Spannable {
        val stringSpan = spannableStringModule.newString()
        workTimeMapper.locationWorkTimeMapperList.forEach {
            if (it.dayFrom == it.dayTo) {
                spannableStringModule.addString(
                    context.getString(R.string.all_text) +
                            " ${it.dayFrom} ${it.startWorkString} " +
                            "- ${it.endWorkString}\n"
                )
                    .init(R.color.black, com.intuit.sdp.R.dimen._10sdp, R.font.gt_meduim)
            } else {
                spannableStringModule.addString(
                    "${it.dayFrom} ${context.getString(R.string.to)} " +
                            "${it.dayTo} ${it.startWorkString} - ${it.endWorkString}"
                )
                    .init(R.color.black, com.intuit.sdp.R.dimen._10sdp, R.font.gt_meduim)
            }
        }
        return stringSpan.getSpannableString()
    }
}