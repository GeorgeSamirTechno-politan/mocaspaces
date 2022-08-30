package com.technopolitan.mocaspaces.data.country

import android.os.Parcel
import android.os.Parcelable

class CountryMapper() : Parcelable {

    var id: Int = 0
    var name: String = ""
    var code: String = ""
    var codeString: String = ""
    var regex: String = ""
    var url: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()!!
        code = parcel.readString()!!
        codeString = parcel.readString()!!
        regex = parcel.readString()!!
        url = parcel.readString()!!
    }


    fun init(countryResponse: CountryResponse): CountryMapper {
        countryResponse.countryCode?.let { code = it }
        countryResponse.id?.let { id = it }
        countryResponse.countryName?.let { name = it }
        countryResponse.countryCodeString?.let { codeString = it }
        countryResponse.regex?.let { regex = it }
        countryResponse.imageIcon?.let { url = it }
        return this
    }


    companion object CREATOR : Parcelable.Creator<CountryMapper> {
        override fun createFromParcel(parcel: Parcel): CountryMapper {
            return CountryMapper(parcel)
        }

        override fun newArray(size: Int): Array<CountryMapper?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }


}