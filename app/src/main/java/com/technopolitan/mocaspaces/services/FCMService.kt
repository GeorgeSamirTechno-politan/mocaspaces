package com.technopolitan.mocaspaces.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.technopolitan.mocaspaces.modules.NotificationModule
import javax.inject.Inject

class FCMService  :
    FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.data.let {
            Log.d(javaClass.name, "onMessageReceived: $it")
        }
    }


}