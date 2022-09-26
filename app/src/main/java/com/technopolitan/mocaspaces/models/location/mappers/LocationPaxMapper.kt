package com.technopolitan.mocaspaces.models.location.mappers

import com.technopolitan.mocaspaces.models.location.response.LocationFilterPaxResponse

class LocationPaxMapper {

    var id: Int = 0
    var fromPax: Int = 0
    var toPax: Int = 0
    var selected: Boolean = false

    constructor(response: LocationFilterPaxResponse) {
        this.id = response.id
        this.fromPax = response.fromPax
        this.toPax = response.toPax
        this.selected = false
    }
}