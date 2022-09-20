package com.technopolitan.mocaspaces.modules

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.technopolitan.mocaspaces.interfaces.LocationEnableListener
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import javax.inject.Inject


@Module
class LocationModule @Inject constructor(
    private var context: Context,
    private var activity: Activity
) {

    private lateinit var callback: (location: Location)-> Unit
    private var minDistanceForUpdateLocation: Long = 100
    private var minTimeBeforeUpdateLocation: Long = 60

    private val locationListener: LocationListener = LocationListener {
        callback(it)
    }

    private val locationEnableListener: LocationEnableListener = object : LocationEnableListener{
        override fun onSuccess(locationSettingsResponse: LocationSettingsResponse) {
            if (locationSettingsResponse.locationSettingsStates!!.isLocationPresent &&
                locationSettingsResponse.locationSettingsStates!!.isNetworkLocationPresent
            ) getLocation()
        }

    }
    fun init(
        callback: (location: Location)-> Unit,
        minDistanceForUpdateLocation: Long = 100,
        minTimeBeforeUpdateLocation: Long = 60,
    ) {
        this.callback = callback
        this.minTimeBeforeUpdateLocation = minTimeBeforeUpdateLocation
        this.minDistanceForUpdateLocation = minDistanceForUpdateLocation
        try {
            if (isLocationEnabled())
                getLocation() else
                enableLocationDirectly()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            val locationProvider = LocationManager.GPS_PROVIDER
            locationManager.requestLocationUpdates(
                locationProvider,
                minTimeBeforeUpdateLocation,
                minDistanceForUpdateLocation.toFloat(),
                locationListener
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun locationRequest(): LocationRequest =
        LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }

    private fun enableLocationDirectly() {
        try {
//            val api =
//                GoogleApi(context, LocationServices.API, 0, GoogleApi.Settings.DEFAULT_SETTINGS)
//            GoogleApiClient.Builder(context).addApi(LocationServices.API).build().connect()
            val locationRequest = locationRequest()
            val builder: LocationSettingsRequest.Builder =
                LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)
            LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())
                .addOnFailureListener { e ->
                    onFailure(e, activity)

                }.addOnSuccessListener {
                    locationEnableListener.onSuccess(it)
                }

        } catch (e: Exception) {
            Log.e(javaClass.name, "isLocationEnabled", e)
        }
    }


    private fun onFailure(e: java.lang.Exception, activity: Activity) {
        try {
            (e as ResolvableApiException).startResolutionForResult(
                activity,
                Constants.enableLocationDirectlyRequestCode
            )
        } catch (ex: IntentSender.SendIntentException) {
            Log.e(javaClass.name, "enableLocationDirectly", e)
        }
    }

    private fun isLocationEnabled(): Boolean {
        var networkEnable = false
        try {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            try {
                networkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (e: Exception) {
                Log.e(javaClass.name, "isLocationEnabled", e)
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, "isLocationEnabled", e)
        }
        return networkEnable
    }
}