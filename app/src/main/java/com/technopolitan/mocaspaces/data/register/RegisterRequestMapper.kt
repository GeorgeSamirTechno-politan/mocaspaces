package com.technopolitan.mocaspaces.data.register

import android.os.Parcel
import android.os.Parcelable
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.gender.GenderMapper

class RegisterRequestMapper(){

    lateinit var profileImageBase64: String
    lateinit var fistName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var mobile: String
    lateinit var counterMapper : CountryMapper
    lateinit var memberTypeMapper: DropDownMapper
    lateinit var company: String
    lateinit var jobTitle: String
    lateinit var genderMapper: GenderMapper
    lateinit var birthDate : String
    lateinit var studentFrontCardPath: String
    lateinit var studentBackCardPath: String
    lateinit var studentCardExpiryDate: String
    lateinit var password: String


}