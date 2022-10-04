package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.response.WorkSpaceResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.formatPrice

class WorkSpaceMapper constructor(
    private var dateTimeModule: DateTimeModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) {

    var image: String = ""
    var id: Int = 0
    var isFavourite: Boolean = false
    var shareLink: String = ""
    var locationName: String = ""
    var address: String = ""
    var locationLatLng: LatLng = LatLng(0.0, 0.0)
    var amenityList: List<AmenityMapper> = mutableListOf()
    var distance: String = ""
    var priceList: MutableList<PriceMapper> = mutableListOf()
    val workTimeMapper: WorkTimeMapper =
        WorkTimeMapper(dateTimeModule, spannableStringModule, context)


    fun init(response: WorkSpaceResponse, location: Location?, context: Context): WorkSpaceMapper {
        this.id = response.id
        isFavourite = response.isFavourite
        locationLatLng = LatLng(response.latitude.toDouble(), response.longitude.toDouble())
        if (response.imagesResponse != null)
            image =
                BaseUrl.baseForImage(BaseUrl.locationApi) + response.imagesResponse.locationImageFilePath
        initDistance(location)
        locationName = response.name
        if (response.districtResponse != null && response.cityResponse != null)
            address = "${response.districtResponse.name}, ${response.cityResponse.name}"
        val list = mutableListOf<AmenityMapper>()
        response.locationAmenities.forEach { item ->
            list.add(AmenityMapper(BaseUrl.baseForImage(BaseUrl.locationApi) + item.icon))
        }
        amenityList = mutableListOf()
        amenityList = list
        workTimeMapper.init(response.workingHourRespons)
        if (response.currencyResponse != null)
            priceList = PriceMapper().intPriceList(
                response.pricesResponse,
                context,
                response.currencyResponse.name
            )
        return this
    }


    private fun initDistance(location: Location?) {
        if (location != null) {
            val distanceInKm = SphericalUtil.computeDistanceBetween(
                locationLatLng,
                LatLng(location.latitude, location.longitude)
            ) / 1000
            distance = "${formatPrice(distanceInKm)}${context.getString(R.string.km_away)}"
        }
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
        if (locationLatLng != other.locationLatLng) return false
        if (amenityList != other.amenityList) return false
        if (workTimeMapper != other.workTimeMapper) return false
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
        result = 31 * result + locationLatLng.hashCode()
        result = 31 * result + amenityList.hashCode()
        result = 31 * result + workTimeMapper.hashCode()
        result = 31 * result + priceList.hashCode()
        return result
    }


}