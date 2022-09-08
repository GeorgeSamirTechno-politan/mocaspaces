package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.broadcasts.SmsReceiver
import com.technopolitan.mocaspaces.interfaces.OnSmsCatchListener
import dagger.Module
import javax.inject.Inject


@Module
class SmsVerifyCatcherModule @Inject constructor(
    private var activity: Activity,
    private var context: Context,
    private var fragment: Fragment?
) {

    private lateinit var onSmsCatchListener: OnSmsCatchListener<String>
    private lateinit var smsReceiver: SmsReceiver
    private var phoneNumber: String? = null
    private var filter: String? = null

    fun init(
        onSmsCatchListener: OnSmsCatchListener<String>,
        phoneNumber: String? = null,
        filter: String? = null
    ) {
        this.onSmsCatchListener = onSmsCatchListener
        this.phoneNumber = phoneNumber
        this.filter = filter
        registerReceiver()
    }

    private fun registerReceiver() {
        smsReceiver = SmsReceiver()
        smsReceiver.let {
            it.setCallback(onSmsCatchListener)
            it.setPhoneNumberFilter(phoneNumber)
            it.setFilter(filter)
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
            activity.registerReceiver(smsReceiver, intentFilter)
        }
    }


    fun onStop() {
        try {
            activity.unregisterReceiver(smsReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}