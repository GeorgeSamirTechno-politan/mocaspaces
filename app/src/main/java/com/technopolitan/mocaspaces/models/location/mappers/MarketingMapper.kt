package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.MarketingResponse
import com.technopolitan.mocaspaces.network.BaseUrl

class MarketingMapper(response: MarketingResponse) {

    var path: String = ""
    var name: String = ""
    var showButton: Boolean = false

    init {
        this.name = response.name
        this.path = BaseUrl.baseForImage(BaseUrl.locationApi) + response.path
        if (name == "meetingspace" || name == "eventspace" || name == "bizlounge") {
            showButton = true
        }
    }
}