//package com.technopolitan.mocaspaces.di
//
//import android.content.Context
//import androidx.work.ListenableWorker
//import androidx.work.RxWorker
//import androidx.work.Worker
//import androidx.work.WorkerFactory
//import androidx.work.WorkerParameters
//import com.technopolitan.mocaspaces.modules.NetworkModule
//import com.technopolitan.mocaspaces.workerManager.RefreshTokenWorker
//
//class DaggerWorkerFactory(private val networkModule: NetworkModule): WorkerFactory() {
//    override fun createWorker(
//        appContext: Context,
//        workerClassName: String,
//        workerParameters: WorkerParameters
//    ): ListenableWorker {
//        val workerKlass = Class.forName(workerClassName).asSubclass(Worker::class.java)
//        val constructor = workerKlass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
//        val instance = constructor.newInstance(appContext, workerParameters) as RxWorker
//
//        when (instance) {
//            is RefreshTokenWorker -> {
//                instance.networkModule = networkModule
//            }
//            // optionally, handle other workers
//        }
//
//        return instance
//    }
//}