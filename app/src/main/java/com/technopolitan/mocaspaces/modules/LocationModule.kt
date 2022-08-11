package com.technopolitan.mocaspaces.modules

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender
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
class LocationModule {
    @Inject
    lateinit var context: Context

    fun getCurrentLocation(
        locationListener: LocationListener,
        minDistanceForUpdateLocation: Long,
        minTimeBeforeUpdateLocation: Long,
        activity: Activity,
        locationEnableListener: LocationEnableListener
    ) {
        try {
            if (isLocationEnabled())
                getLocation(
                    locationListener,
                    minTimeBeforeUpdateLocation,
                    minDistanceForUpdateLocation
                ) else
                enableLocationDirectly(
                    activity,
                    locationEnableListener,
                    context,
                    locationListener,
                    minDistanceForUpdateLocation,
                    minTimeBeforeUpdateLocation
                )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLocation(
        locationListener: LocationListener,
        minTimeBeforeUpdate: Long,
        minDistanceForUpdate: Long
    ) {
        try {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            val locationProvider = LocationManager.NETWORK_PROVIDER
            locationManager.requestLocationUpdates(
                locationProvider,
                minTimeBeforeUpdate,
                minDistanceForUpdate.toFloat(),
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

    private fun enableLocationDirectly(
        activity: Activity,
        locationEnableListener: LocationEnableListener,
        context: Context,
        locationListener: LocationListener,
        minDistanceForUpdateLocation: Long,
        minTimeBeforeUpdateLocation: Long
    ) {
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
                    onSuccess(
                        locationEnableListener,
                        locationListener,
                        minDistanceForUpdateLocation,
                        minTimeBeforeUpdateLocation,
                        it
                    )
                }

        } catch (e: Exception) {
            Log.e(javaClass.name, "isLocationEnabled", e)
        }
    }

    private fun onSuccess(
        locationEnableListener: LocationEnableListener,
        locationListener: LocationListener,
        minDistanceForUpdateLocation: Long,
        minTimeBeforeUpdateLocation: Long,
        locationSettingsResponse: LocationSettingsResponse
    ) {
        locationEnableListener.onSuccess(locationSettingsResponse = locationSettingsResponse)
        if (locationSettingsResponse.locationSettingsStates!!.isLocationPresent && locationSettingsResponse.locationSettingsStates!!.isNetworkLocationPresent)
            getLocation(locationListener, minTimeBeforeUpdateLocation, minDistanceForUpdateLocation)
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