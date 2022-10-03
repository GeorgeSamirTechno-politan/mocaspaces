package com.technopolitan.mocaspaces.modules

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.SharedPrefKey
import javax.inject.Inject

class NotificationModule @Inject constructor(
    private var context: Context,
    private var sharedPrefModule: SharedPrefModule
) {

    fun showNotificationMessage(
        title: String,
        body: String,
        imageResource: Int = R.mipmap.ic_launcher,
        channelId: String = "${context.getString(R.string.app_name)} channel id",
        pendingIntent: PendingIntent?
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationImportance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(
                channelId,
                "Channel human readable title", notificationImportance
            )
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationId =
            sharedPrefModule.getIntFromShared(SharedPrefKey.NotificationId.name) + 1
        sharedPrefModule.setIntToShared(SharedPrefKey.NotificationId.name, notificationId)
        notificationManager.notify(
            notificationId,
            getNotificationBuilder(
                title, body, context, imageResource,
                channelId,
                pendingIntent
            ).build()
        )
    }

    private fun getNotificationBuilder(
        title: String, body: String, context: Context,
        imageResource: Int, channelId: String,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(imageResource)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(bigTextStyle(context, body))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(defaultSoundUri)
                .setChannelId(channelId)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        if (pendingIntent != null) notificationBuilder.setContentIntent(pendingIntent)
        return notificationBuilder
    }

    private fun bigTextStyle(
        context: Context,
        body: String
    ) = NotificationCompat.BigTextStyle(
        NotificationCompat.Builder(
            context,
            body
        )
    )
}