package com.technopolitan.mocaspaces.data.gender

class GenderMapper {
    var genderName: String = ""
    var genderId : Int = 0

    fun init(genderResponse: GenderResponse): GenderMapper{
        genderId = genderResponse.id
        genderName = genderResponse.name
        return this
    }
}