package com.technopolitan.mocaspaces.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.technopolitan.mocaspaces.interfaces.OnSmsCatchListener


class SmsReceiver : BroadcastReceiver() {
    private lateinit var callback: OnSmsCatchListener<String>
    private var phoneNumberFilter: String? = null
    private var filter: String? = null


    override fun onReceive(context: Context?, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<*>?
                for (i in pdusObj!!.indices) {
                    val currentMessage: SmsMessage? =
                        pdusObj[i]?.let { getIncomingMessage(it, bundle) }
                    val phoneNumber: String = currentMessage!!.displayOriginatingAddress
                    if (phoneNumberFilter != null && phoneNumber != phoneNumberFilter) {
                        return
                    }
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.displayOriginatingAddress}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.originatingAddress}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.serviceCenterAddress}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.pseudoSubject}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.pseudoSubject}")
//                    Log.d(javaClass.name, "onReceive: ${currentMessage.pseudoSubject}")
                    val message: String = currentMessage.displayMessageBody
                    if (filter != null && !message.matches(Regex(filter!!))) {
                        return
                    }
                    callback.onSmsCatch(message, currentMessage.originatingAddress!!)
                } // end for loop
            } // bundle is null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val format = bundle.getString("format")
        return SmsMessage.createFromPdu(aObject as ByteArray, format)
    }

    fun setPhoneNumberFilter(phoneNumberFilter: String?) {
        this.phoneNumberFilter = phoneNumberFilter
    }

    fun setFilter(regularExpression: String?) {
        filter = regularExpression
    }

    fun setCallback(onSmsCatchListener: OnSmsCatchListener<String>) {
        this.callback = onSmsCatchListener
    }

}