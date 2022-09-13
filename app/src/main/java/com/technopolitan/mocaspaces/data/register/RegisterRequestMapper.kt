package com.technopolitan.mocaspaces.data.register

import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.gender.GenderMapper

class RegisterRequestMapper {

    lateinit var profileImageBase64: String
    lateinit var fistName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var mobile: String
    lateinit var counterMapper: CountryMapper
    lateinit var memberTypeMapper: DropDownMapper
    lateinit var company: String
    lateinit var jobTitle: String
    lateinit var genderMapper: GenderMapper
    var birthDate: String? = null
    var studentFrontCardPath: String? = null
    var studentBackCardPath: String? = null
    var studentCardExpiryDate: String? = null
    lateinit var password: String


}