package com.technopolitan.mocaspaces.modules

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.interfaces.OnSmsCatchListener
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import javax.inject.Inject

@Module
class SmsIdentifierModule @Inject constructor(
    private var context: Context,
    private var permissionModule: PermissionModule,
    private var smsVerifyCatcherModule: SmsVerifyCatcherModule
) {

    private lateinit var callback: (mobile: String) -> Unit
    private lateinit var activityResultLauncher: ActivityResultLauncher<String>
    fun init(
        activityResultLauncher: ActivityResultLauncher<String>,
        callback: (otp: String) -> Unit,

        ) {
        this.callback = callback
        this.activityResultLauncher = activityResultLauncher
        permissionModule.init(
//            activityResultLauncher,
            android.Manifest.permission.RECEIVE_SMS,
        ) {
            readSMS()
        }
    }

    private fun readSMS() {
        smsVerifyCatcherModule.init(smsCatchListener)
    }

    private val smsCatchListener = object : OnSmsCatchListener<String> {
        override fun onSmsCatch(message: String) {
            if (message.contains("moca")) {
                callback(message.split(" ")[0])
            }
        }

    }

//    fun updatePermissionResult(granted: Boolean?) {
//        granted?.let {
//            permissionModule.handlePermissionCallBack(it)
//        }
//    }

}