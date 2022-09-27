package com.technopolitan.mocaspaces.models.location.mappers

import android.content.Context
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.shared.PricesResponse

class PriceMapper {

    var startFrom: String = ""
    var price: String = ""
    var per: String = ""
    var currency: String = ""

    fun initPriceMapper(
        startFrom: String,
        price: String,
        per: String,
        currency: String
    ): PriceMapper {
        this.startFrom = startFrom
        this.price = price
        this.per = per
        this.currency = currency
        return this
    }

    fun intPriceList(
        response: PricesResponse,
        context: Context,
        currency: String
    ): MutableList<PriceMapper> {
        val priceList = mutableListOf<PriceMapper>()
        if (response.day != null) {
            priceList.add(
                PriceMapper().initPriceMapper(
                    startFrom = context.getString(R.string.day_pass),
                    per = "",
                    price = response.day.toString(),
                    currency = currency
                )
            )
        }
        if (response.hourly != null)
            priceList.add(
                PriceMapper().initPriceMapper(
                    startFrom = context.getString(R.string.hourly_starting),
                    per = "/${context.getString(R.string.hour)}",
                    price = response.hourly.toString(),
                    currency = currency
                )
            )
        if (response.tailored != null)
            priceList.add(
                PriceMapper().initPriceMapper(
                    startFrom = context.getString(R.string.tailored_starting),
                    per = "/${context.getString(R.string.hour)}",
                    price = response.tailored.toString(),
                    currency = currency
                )
            )
        if (response.bundle != null)
            priceList.add(
                PriceMapper().initPriceMapper(
                    startFrom = context.getString(R.string.bundle_starting),
                    per = "/${context.getString(R.string.hour)}",
                    price = response.bundle.toString(),
                    currency = currency
                )
            )
        if (response.privateOfficePrice != null)
            priceList.add(
                PriceMapper().initPriceMapper(
                    startFrom = context.getString(R.string.bundle_starting),
                    per = "/${context.getString(R.string.hour)}",
                    price = response.bundle.toString(),
                    currency = currency
                )
            )
        return priceList
    }

}