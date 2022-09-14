package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import android.text.Spannable
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.response.WorkSpaceResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class WorkSpaceMapper constructor(private var dateTimeModule: DateTimeModule) {

    var image: String = ""
    var isFavourite: Boolean = false
    var shareLink: String = ""
    var locationName: String = ""
    var address: String = ""
    var hourlyPrice: String = ""
    var tailoredPrice: String = ""
    var dayPassPrice: String = ""
    var bundlePrice: String = ""
    var amenityList: List<AmenityMapper> = mutableListOf()
    val workTimeMapper: WorkTimeMapper = WorkTimeMapper(dateTimeModule)
    var currency: String = ""
    var priceList: MutableList<Spannable> = mutableListOf()


    fun init(response: WorkSpaceResponse): WorkSpaceMapper {
        isFavourite = response.isFavourite
        image =
            BaseUrl.baseForImage(BaseUrl.locationApi) + response.locationImagesResponse.locationImageFilePath
//        shareLink = response.sh
        locationName = response.name
        address = "${response.districtResponse.name} + ${response.cityResponse.name}"
        hourlyPrice = response.pricesResponse.hourly.toInt().toString()
        tailoredPrice = response.pricesResponse.tailored.toInt().toString()
        dayPassPrice = response.pricesResponse.day.toInt().toString()
        bundlePrice = response.pricesResponse.bundle.toInt().toString()
        currency = response.currencyResponse.name
        val list = mutableListOf<AmenityMapper>()
        response.locationAmenities.forEach { item ->
            list.add(AmenityMapper(BaseUrl.baseForImage(BaseUrl.locationApi) + item.icon))
        }
        amenityList = list
        workTimeMapper.init(response.locationWorkingHourResponses)
        return this
    }

    fun initPriceList(context: Context, spannableStringModule: SpannableStringModule) {
        priceList.add(getPriceForDayPass(context, spannableStringModule))
        priceList.add(getPriceForHourly(context, spannableStringModule))
        priceList.add(getPriceForTailored(context, spannableStringModule))
        priceList.add(getPriceForBundle(context, spannableStringModule))
    }

    private fun getPriceForDayPass(
        context: Context,
        spannableStringModule: SpannableStringModule
    ): Spannable {
        val tailoredStarting = context.getString(R.string.day_pass) + "\n"
        return spannableStringModule.addString(tailoredStarting)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .addString(tailoredPrice)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._18sdp, R.font.gt_meduim)
            .addString("/${context.getString(R.string.hour)}\n")
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._10sdp, R.font.gt_regular)
            .addString(currency)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .getSpannableString()
    }

    private fun getPriceForHourly(
        context: Context,
        spannableStringModule: SpannableStringModule
    ): Spannable {
        val tailoredStarting = context.getString(R.string.hourly_starting) + "\n"
        return spannableStringModule.addString(tailoredStarting)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .addString(tailoredPrice)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._18sdp, R.font.gt_meduim)
            .addString("/${context.getString(R.string.hour)}\n")
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._10sdp, R.font.gt_regular)
            .addString(currency)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .getSpannableString()
    }

    private fun getPriceForTailored(
        context: Context,
        spannableStringModule: SpannableStringModule
    ): Spannable {
        val tailoredStarting = context.getString(R.string.tailored_starting) + "\n"
        return spannableStringModule.addString(tailoredStarting)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .addString(tailoredPrice)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._18sdp, R.font.gt_meduim)
            .addString("/${context.getString(R.string.hour)}\n")
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._10sdp, R.font.gt_regular)
            .addString(currency)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .getSpannableString()
    }

    private fun getPriceForBundle(
        context: Context,
        spannableStringModule: SpannableStringModule
    ): Spannable {
        val tailoredStarting = context.getString(R.string.bundle_starting) + "\n"
        return spannableStringModule.addString(tailoredStarting)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .addString(tailoredPrice)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._18sdp, R.font.gt_meduim)
            .addString("/${context.getString(R.string.hour)}\n")
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._10sdp, R.font.gt_regular)
            .addString(currency)
            .init(R.color.accent_color, com.intuit.sdp.R.dimen._8sdp, R.font.gt_regular)
            .getSpannableString()
    }
}