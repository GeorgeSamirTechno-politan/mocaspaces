package com.technopolitan.mocaspaces.interfaces

import android.location.Location

interface LocationListenerInterface {

    fun onLocationChange(location: Location)
}