package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.technopolitan.mocaspaces.models.location.response.LocationDetailsResponse
import com.technopolitan.mocaspaces.models.shared.PriceResponse
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
    lateinit var priceResponse: PriceResponse
    var currency: String = ""

    init {
        id = respnse.id
        shareLink = ""
        isFavourite = respnse.isFavourite
        if (respnse.image != null)
            mainImage =
                BaseUrl.baseForImage(BaseUrl.locationApi) + respnse.image.locationImageFilePath
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

        respnse.price.let { price ->
            respnse.currency.let { currency ->
                this.priceResponse = price
                priceList = PriceMapper().intPriceList(price, context, currency.name)
                this.currency = currency.name
            }
        }
        about = respnse.about
        locationName = respnse.locationName
        respnse.district.let { district ->
            respnse.city.let { city ->
                respnse.country.let { country ->
                    shortAddress = district.name + ", " + city.name
                    longAddress = district.name + ", " + city.name + ", " + country.name
                }
            }
        }
        locationLatLong = LatLng(respnse.latitude.toDouble(), respnse.longitude.toDouble())
        termsOfUse = respnse.termsOfUse
        if (respnse.marketingList != null)
            respnse.marketingList?.forEach {
                marketingList.add(MarketingMapper(it))
            }
    }


}