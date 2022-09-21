package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import android.location.Location
import android.text.Spannable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.response.WorkSpaceResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class WorkSpaceMapper constructor(private var dateTimeModule: DateTimeModule) {

    var image: String = ""
    var id: Int = 0
    var isFavourite: Boolean = false
    var shareLink: String = ""
    var locationName: String = ""
    var address: String = ""
    var distance: String = ""
    var hourlyPrice: String = ""
    var tailoredPrice: String = ""
    var dayPassPrice: String = ""
    var bundlePrice: String = ""
    var locationLatLng: LatLng = LatLng(0.0, 0.0)
    var amenityList: List<AmenityMapper> = mutableListOf()
    val workTimeMapper: WorkTimeMapper = WorkTimeMapper(dateTimeModule)
    var currency: String = ""
    var priceList: MutableList<PricePagerMapper> = mutableListOf()


    fun init(response: WorkSpaceResponse, location: Location?, context: Context): WorkSpaceMapper {
        this.id = response.id
        isFavourite = response.isFavourite
        if (response.locationImagesResponse != null)
            image =
                BaseUrl.baseForImage(BaseUrl.locationApi) + response.locationImagesResponse.locationImageFilePath
        locationLatLng = LatLng(response.latitude.toDouble(), response.longitude.toDouble())
        updateDistance(context, location)
        locationName = response.name
        address = "${response.districtResponse.name}, ${response.nameResponse.name}"
        hourlyPrice = response.pricesResponse.hourly.toInt().toString()
        tailoredPrice = response.pricesResponse.tailored.toInt().toString()
        dayPassPrice = response.pricesResponse.day.toInt().toString()
        bundlePrice = response.pricesResponse.bundle.toInt().toString()
        currency = response.currencyResponse.name
        val list = mutableListOf<AmenityMapper>()
        response.locationAmenities.forEach { item ->
            list.add(AmenityMapper(BaseUrl.baseForImage(BaseUrl.locationApi) + item.icon))
        }
        amenityList = mutableListOf()
        amenityList = list
        workTimeMapper.init(response.locationWorkingHourResponses)
        return this
    }

    private fun updateDistance(context: Context, location: Location?) {
        if (location != null) {
            val distanceInKm = SphericalUtil.computeDistanceBetween(
                locationLatLng,
                LatLng(location.latitude, location.longitude)
            ) / 1000
            distance = "$distanceInKm${context.getString(R.string.km_away)}"
        }
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

    fun initPriceList(context: Context) {
        priceList = mutableListOf()
        priceList.add(
            PricePagerMapper(
                startFrom = context.getString(R.string.day_pass),
                per = "",
                price = dayPassPrice,
                currency = currency
            )
        )
        priceList.add(
            PricePagerMapper(
                startFrom = context.getString(R.string.hourly_starting),
                per = "/${context.getString(R.string.hour)}",
                price = dayPassPrice,
                currency = currency
            )
        )
        priceList.add(
            PricePagerMapper(
                startFrom = context.getString(R.string.tailored_starting),
                per = "/${context.getString(R.string.hour)}",
                price = tailoredPrice,
                currency = currency
            )
        )
        priceList.add(
            PricePagerMapper(
                startFrom = context.getString(R.string.bundle_starting),
                per = "/${context.getString(R.string.hour)}",
                price = bundlePrice,
                currency = currency
            )
        )
    }

    override fun toString(): String {
        return "WorkSpaceMapper(dateTimeModule=$dateTimeModule, " +
                "image='$image', isFavourite=$isFavourite, " +
                "shareLink='$shareLink', locationName='$locationName', " +
                "address='$address', distance='$distance', " +
                "hourlyPrice='$hourlyPrice', tailoredPrice='$tailoredPrice', " +
                "dayPassPrice='$dayPassPrice', bundlePrice='$bundlePrice', " +
                "locationLatLng=$locationLatLng, amenityList=$amenityList, " +
                "workTimeMapper=$workTimeMapper, currency='$currency', " +
                "priceList=$priceList)"
    }


}