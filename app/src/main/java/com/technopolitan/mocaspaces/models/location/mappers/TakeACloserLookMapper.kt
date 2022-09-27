package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.TakeACloserLookResponse
import com.technopolitan.mocaspaces.network.BaseUrl

class TakeACloserLookMapper(response: TakeACloserLookResponse) {

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