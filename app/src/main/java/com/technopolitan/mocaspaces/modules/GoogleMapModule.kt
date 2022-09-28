package com.technopolitan.mocaspaces.modules

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.technopolitan.mocaspaces.R
import dagger.Module
import javax.inject.Inject


@Module
class GoogleMapModule @Inject constructor(
    private var context: Context,
    private var utilityModule: UtilityModule,
    private var fragment: Fragment?,
    private var permissionModel: PermissionModule
) : OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private var addMarkerOnly = false
    private lateinit var markerLatLong: LatLng
    private var markerTitle = ""
    private var hasScrollWithIt: Boolean = false
    private val mapFragment = SupportMapFragment.newInstance()

    fun build(mapId: Int) {
        try {
            mapFragment.childFragmentManager.findFragmentById(mapId)
            mapFragment.getMapAsync(this)
        } catch (e: Exception) {
            Log.e(javaClass.name, "build: ", e)
        }
    }

//    fun withScrollOutSide(nestedScrollView: NestedScrollView): GoogleMapModule {
//        this.nestedScrollView = nestedScrollView
//        return this
//    }

    fun addMarker(latLng: LatLng, title: String): GoogleMapModule {
        this.markerLatLong = latLng
        this.markerTitle = title
        addMarkerOnly = true
        return this
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0
        initGoogleMap()
        initView()
    }

    @SuppressLint("MissingPermission")
    private fun initGoogleMap() {
        if (hasScrollWithIt) {
            this.googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            this.googleMap.uiSettings.isZoomControlsEnabled = true
            this.googleMap.uiSettings.isZoomGesturesEnabled = true
            this.googleMap.uiSettings.isCompassEnabled = false
            this.googleMap.uiSettings.isScrollGesturesEnabled = false
            this.googleMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = false
            permissionModel.init(android.Manifest.permission.ACCESS_FINE_LOCATION, false) {
                this.googleMap.isMyLocationEnabled = it
            }
        }
    }


    fun disableMapScroll(hasScrollWithIt: Boolean): GoogleMapModule {
        this.hasScrollWithIt = hasScrollWithIt
        return this
    }

    private fun initView() {
        when {
            addMarkerOnly -> {
                moveCamera()
                addMarker()
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun addMarker() {
        googleMap.setOnMarkerClickListener(markerClickListener)
        googleMap.addMarker(MarkerOptions().apply {
            position(markerLatLong)
            title(title)
            draggable(false)
            visible(true)
            icon(bitmapFromVector(context, R.drawable.ic_location_icon))
        })

    }

    private fun moveCamera() {
        val cameraPosition = CameraPosition.builder().apply {
            zoom(13.0f)
            target(markerLatLong)
            tilt(0.0f)
            bearing(0.0f)
        }.build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private val markerClickListener = GoogleMap.OnMarkerClickListener {
        if (it.title == this.markerTitle) {
            openMap()
        }
        return@OnMarkerClickListener true
    }

    private val onMapTouchListener = GoogleMap.OnMapClickListener {

    }

    private fun openMap() {
        utilityModule.directionToLocationUsingGoogleMap(
            markerLatLong.latitude.toString(),
            markerLatLong.longitude.toString()
        )
    }


}