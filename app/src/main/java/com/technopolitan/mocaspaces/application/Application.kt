package com.technopolitan.mocaspaces.application

import android.app.Application
import android.os.StrictMode
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieConfig

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()


        Lottie.initialize(
            LottieConfig.Builder()
                .setEnableSystraceMarkers(true)
                .build()
        )
    }
}