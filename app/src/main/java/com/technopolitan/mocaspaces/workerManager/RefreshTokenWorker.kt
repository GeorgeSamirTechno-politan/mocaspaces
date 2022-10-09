//package com.technopolitan.mocaspaces.workerManager
//
//import android.content.Context
//import android.util.Log
//import androidx.work.RxWorker
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.technopolitan.mocaspaces.data.HeaderResponse
//import com.technopolitan.mocaspaces.enums.AppKeys
//import com.technopolitan.mocaspaces.models.RefreshTokenRequest
//import com.technopolitan.mocaspaces.modules.NetworkModule
//import com.technopolitan.mocaspaces.network.BaseUrl
//import io.reactivex.Single
//
//class RefreshTokenWorker constructor(
//    context: Context,
//    private val workerParams: WorkerParameters
//) : RxWorker(context, workerParams) {
//
//    lateinit var networkModule: NetworkModule
//
//
//    override fun createWork(): Single<Result> {
//        return refreshToken().map { Result.success() }
//            .doOnError {
//                Log.e(javaClass.name, "createWork: ", it)
//                Result.failure()
//            }
//            .doOnSuccess {
//                Result.success()
//            }
//    }
//
//
//    private fun refreshToken(): Single<HeaderResponse<String>> {
//        val newToken = workerParams.inputData.getString(AppKeys.Message.name)!!
//        return networkModule.provideService(BaseUrl.sso)
//            .refreshFCMToken(refreshTokenRequest = RefreshTokenRequest(newToken))
//
//    }
//}