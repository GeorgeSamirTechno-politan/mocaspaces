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
import com.technopolitan.mocaspaces.utilities.formatPrice

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
            distance = "${formatPrice(distanceInKm)}${context.getString(R.string.km_away)}"
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkSpaceMapper

        if (dateTimeModule != other.dateTimeModule) return false
        if (image != other.image) return false
        if (id != other.id) return false
        if (isFavourite != other.isFavourite) return false
        if (shareLink != other.shareLink) return false
        if (locationName != other.locationName) return false
        if (address != other.address) return false
        if (distance != other.distance) return false
        if (hourlyPrice != other.hourlyPrice) return false
        if (tailoredPrice != other.tailoredPrice) return false
        if (dayPassPrice != other.dayPassPrice) return false
        if (bundlePrice != other.bundlePrice) return false
        if (locationLatLng != other.locationLatLng) return false
        if (amenityList != other.amenityList) return false
        if (workTimeMapper != other.workTimeMapper) return false
        if (currency != other.currency) return false
        if (priceList != other.priceList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateTimeModule.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + id
        result = 31 * result + isFavourite.hashCode()
        result = 31 * result + shareLink.hashCode()
        result = 31 * result + locationName.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + hourlyPrice.hashCode()
        result = 31 * result + tailoredPrice.hashCode()
        result = 31 * result + dayPassPrice.hashCode()
        result = 31 * result + bundlePrice.hashCode()
        result = 31 * result + locationLatLng.hashCode()
        result = 31 * result + amenityList.hashCode()
        result = 31 * result + workTimeMapper.hashCode()
        result = 31 * result + currency.hashCode()
        result = 31 * result + priceList.hashCode()
        return result
    }


}