package com.technopolitan.mocaspaces.application

import android.app.Application
import android.os.StrictMode
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieConfig
//import com.technopolitan.mocaspaces.di.DaggerWorkerFactory
import javax.inject.Inject


open class MyApplication : Application() {

//    @Inject
//    lateinit var daggerWorkerFactory: DaggerWorkerFactory

    override fun onCreate() {
        super.onCreate()
//        initDaggerWorker()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()


        Lottie.initialize(
            LottieConfig.Builder()
                .setEnableSystraceMarkers(true)
                .build()
        )

    }

//    private fun initDaggerWorker() {
//        val config = Configuration.Builder()
//            .setWorkerFactory(daggerWorkerFactory)
//            .build()
//
//        WorkManager.initialize(this, config)
//    }


}


