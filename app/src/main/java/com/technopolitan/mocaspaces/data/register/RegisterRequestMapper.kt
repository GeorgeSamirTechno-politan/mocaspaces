package com.technopolitan.mocaspaces.data.register

import android.os.Parcel
import android.os.Parcelable
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.memberType.MemberTypeResponse

class RegisterRequestMapper() : Parcelable{

    lateinit var profileImagePath: String
    lateinit var fistName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var mobile: String
    lateinit var counterMapper : CountryMapper
    lateinit var memberTypeMapper: DropDownMapper
    lateinit var company: String
    lateinit var jobTitle: String
    lateinit var birthDate : String
    lateinit var studentFrontCardPath: String
    lateinit var studentBackCardPath: String
    lateinit var studentCardExpiryDate: String
    lateinit var password: String

    constructor(parcel: Parcel) : this() {
        profileImagePath = parcel.readString()!!
        fistName = parcel.readString()!!
        lastName = parcel.readString()!!
        email = parcel.readString()!!
        mobile = parcel.readString()!!
        counterMapper = parcel.readParcelable(CountryMapper::class.java.classLoader)!!
        company = parcel.readString()!!
        jobTitle = parcel.readString()!!
        birthDate = parcel.readString()!!
        studentFrontCardPath = parcel.readString()!!
        studentBackCardPath = parcel.readString()!!
        studentCardExpiryDate = parcel.readString()!!
        password = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(profileImagePath)
        parcel.writeString(fistName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(mobile)
        parcel.writeParcelable(counterMapper, flags)
        parcel.writeString(company)
        parcel.writeString(jobTitle)
        parcel.writeString(birthDate)
        parcel.writeString(studentFrontCardPath)
        parcel.writeString(studentBackCardPath)
        parcel.writeString(studentCardExpiryDate)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterRequestMapper> {
        override fun createFromParcel(parcel: Parcel): RegisterRequestMapper {
            return RegisterRequestMapper(parcel)
        }

        override fun newArray(size: Int): Array<RegisterRequestMapper?> {
            return arrayOfNulls(size)
        }
    }

}