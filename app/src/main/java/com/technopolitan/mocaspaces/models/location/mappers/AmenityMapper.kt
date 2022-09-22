package com.technopolitan.mocaspaces.models.location.mappers

data class AmenityMapper(
    var image: String = ""


) {
    override fun toString(): String {
        return "AmenityMapper(image='$image')"
    }
}