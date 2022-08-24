package com.technopolitan.mocaspaces.data.country

class CountryMapper {

    var id: Int = 0
    var name: String = ""
    var code: String = ""
    var codeString: String = ""
    var regex: String = ""
    var url: String= ""



    fun init(countryResponse: CountryResponse) : CountryMapper{
        countryResponse.countryCode?.let { code = it }
        countryResponse.id?.let { id = it }
        countryResponse.countryName?.let { name = it }
        countryResponse.countryCodeString?.let { codeString = it }
        countryResponse.regex?.let { regex = it }
        countryResponse.imageIcon?.let { url = it }
        return this
    }




}