package com.technopolitan.mocaspaces.interfaces

import com.google.android.gms.location.LocationSettingsResponse

interface LocationEnableListener {
    fun onSuccess(locationSettingsResponse: LocationSettingsResponse)
}