package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.technopolitan.mocaspaces.models.location.response.LocationDetailsResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class LocationDetailsMapper constructor(
    private var context: Context,
    private var respnse: LocationDetailsResponse,
    private var spannableStringModule: SpannableStringModule,
    private var dateTimeModule: DateTimeModule
) {
    var id: Int = 0
    var shareLink: String = ""
    var isFavourite: Boolean = false
    var mainImage: String = ""
    var hasFoodMenu: Boolean = false
    var venueName: String = ""
    var locationName: String = ""
    var shortAddress: String = ""
    var priceList: MutableList<PriceMapper> = mutableListOf()
    var workTimeMapper = WorkTimeMapper(dateTimeModule, spannableStringModule, context)
    var amenityList: MutableList<AmenityMapper> = mutableListOf()
    var about: String = ""
    var longAddress: String = ""
    var locationLatLong: LatLng = LatLng(0.0, 0.0)
    var marketingList: MutableList<MarketingMapper> = mutableListOf()
    var termsOfUse: String = ""

    init {
        id = respnse.id
        shareLink = ""
        isFavourite = respnse.isFavourite
        if (respnse.image != null)
            mainImage =
                BaseUrl.baseForImage(BaseUrl.locationApi) + respnse.image?.locationImageFilePath
        venueName = ""
        hasFoodMenu = false
        workTimeMapper.init(respnse.workingHourList)
        if (respnse.amenityList != null) {
            respnse.amenityList?.forEach {
                amenityList.add(
                    AmenityMapper(
                        BaseUrl.baseForImage(BaseUrl.locationApi) + it.icon,
                        it.name
                    )
                )
            }
        }
        about = respnse.about
        locationName = respnse.locationName
        shortAddress = respnse.district.name + ", " + respnse.city.name
        priceList = PriceMapper().intPriceList(respnse.price, context, respnse.currency.name)
        longAddress = respnse.district.name + ", " + respnse.city.name + ", " + respnse.country.name
        locationLatLong = LatLng(respnse.latitude.toDouble(), respnse.longitude.toDouble())
        termsOfUse = respnse.termsOfUse
        if (respnse.marketingList != null)
            respnse.marketingList?.forEach {
                marketingList.add(MarketingMapper(it))
            }
    }


}