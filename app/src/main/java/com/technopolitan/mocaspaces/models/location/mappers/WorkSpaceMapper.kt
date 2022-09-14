package com.technopolitan.mocaspaces.models.location.mappers

class WorkSpaceMapper {

    var image: String = ""
    var favourite: Boolean = false
    var shareLink: String=""
    var locationName: String = ""
    var address: String = ""
    var hourlyPrice : String = ""
    var tailoredPrice: String = ""
    var dayPassPrice: String = ""
    var bundlePrice: String = ""
    var amenityList: List<AmenityMapper> = mutableListOf()


}