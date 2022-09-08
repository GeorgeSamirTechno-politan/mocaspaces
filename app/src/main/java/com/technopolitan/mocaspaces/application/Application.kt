package com.technopolitan.mocaspaces.application

import android.app.Application
import android.os.StrictMode

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

    }
}