package com.technopolitan.mocaspaces.modules

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import javax.inject.Inject

@Module
class AppDefaultModule @Inject constructor(private var context: Context) {

    init {
        initFirebase()
        initDeviceSerial()
        initDeviceInfo()
        initAppVersion()

    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(context)
        FirebaseApp.getInstance().setAutomaticResourceManagementEnabled(true)
        initDeviceToken()
        initNotificationToken()
    }

    fun deleteDeviceToken() {
        FirebaseInstallations.getInstance().delete().addOnCompleteListener {
            if (it.isSuccessful)
                println("successfully deleted fire base install id")
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun initDeviceSerial() {
//        if (Constants.deviceSerial.isEmpty()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                Constants.deviceSerial = Build.getSerial()
//            } else {
//                Constants.deviceSerial = Build.UNKNOWN
//            }
//            println("device serial: ${Constants.deviceSerial}")
//        }
    }

    private fun initDeviceToken() {
        if (Constants.firebaseInstallationId.isEmpty()) {
            FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener {
                if (it.isSuccessful) {
                    Constants.firebaseInstallationId = it.result!!.token
                    println("device token: ${it.result!!.token}")
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }
        }
    }


    private fun initDeviceInfo() {
        Constants.deviceInfo = "Device model: " + Build.MODEL + " ,Android version: " +
                Build.VERSION.RELEASE + " , Api level: " + Build.VERSION.SDK_INT
        println("device info: ${Constants.deviceInfo}")
    }

    private fun initAppVersion() {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            Constants.applicationVersion = pInfo.versionName
            println("application version: ${pInfo.versionName}")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun initNotificationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Constants.notificationToken = it.result!!
                println("fire base message token: ${it.result!!}")
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
}