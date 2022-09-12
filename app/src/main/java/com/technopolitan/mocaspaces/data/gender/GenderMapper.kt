package com.technopolitan.mocaspaces.data.gender

import android.os.Parcel
import android.os.Parcelable

class GenderMapper() : Parcelable{
    var genderName: String = ""
    var genderId : Int = 0

    constructor(parcel: Parcel) : this() {
        parcel.readString()?.let { genderName = it }
        parcel.readInt()?.let { genderId = it }
    }

    fun init(genderResponse: GenderResponse): GenderMapper{
        genderId = genderResponse.id
        genderName = genderResponse.name
        return this
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(genderName)
        parcel.writeInt(genderId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenderMapper> {
        override fun createFromParcel(parcel: Parcel): GenderMapper {
            return GenderMapper(parcel)
        }

        override fun newArray(size: Int): Array<GenderMapper?> {
            return arrayOfNulls(size)
        }
    }
}