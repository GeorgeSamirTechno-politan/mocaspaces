package com.technopolitan.mocaspaces.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.technopolitan.mocaspaces.modules.NotificationModule
import javax.inject.Inject

class FCMService @Inject constructor(private var notificationModule: NotificationModule) :
    FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.data.let {

        }
    }


}