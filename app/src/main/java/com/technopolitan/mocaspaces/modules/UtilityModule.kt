package com.technopolitan.mocaspaces.modules

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.Window
import android.view.WindowManager
import androidx.browser.customtabs.CustomTabsIntent
import com.technopolitan.mocaspaces.R.color
import com.technopolitan.mocaspaces.R.string
import dagger.Module
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject


@Module
class UtilityModule @Inject constructor(
    private var context: Context,
    private var activity: Activity
) {


    fun shareLink(link: String?) {
        try {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/html"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, link)
            context.startActivity(
                Intent.createChooser(
                    sharingIntent,
                    context.getString(string.share_message)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openUrlWithChromeTab(url: String?) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        if (url != null) if (url.isNotEmpty()) {
            try {
                val customTabsIntent: CustomTabsIntent = builder.build()
                customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                customTabsIntent.launchUrl(context, Uri.parse(url))
            } catch (e: java.lang.Exception) {
                val customTabsIntent: CustomTabsIntent = builder.build()
                customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                customTabsIntent.launchUrl(context, Uri.parse("http://$url"))
            }
        }
    }

    fun directionToLocationUsingGoogleMap(latitude: String, longitude: String) {
        try {
            val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    fun sendEmail(emailAddress: String) {
        val i = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null
            )
        )
        context.startActivity(Intent.createChooser(i, "Send email"))
    }

    fun getImageBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun getImageBase64(file: File): String {
        val bitmap = BitmapFactory.decodeFile(file.path)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun getImageBase64(uri: Uri): String {
        val bitmap = BitmapFactory.decodeFile(uri.path)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun doCall(phoneNumber: String?) {
        if (phoneNumber != null) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setStatusBar(
        stateBarColor: Int = color.accent_color,
        navigationColor: Int = color.white,
        backGroundDrawable: Int? = null,
        isLightStateBar: Boolean = false,
        isDarkMode: Boolean = false
    ) {
        val window: Window = activity.window
        if (backGroundDrawable != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = context.getColor(android.R.color.transparent)
            window.navigationBarColor = context.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(context.getDrawable(backGroundDrawable))
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = context.getColor(stateBarColor)
            window.navigationBarColor = context.getColor(navigationColor)
        }
//        if (isLightStateBar && !isDarkMode) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.decorView.windowInsetsController!!.setSystemBarsAppearance()
//                window.decorView.setSystemUiVisibility(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
//            }else window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            window.statusBarColor = context.getColor(color.white)
//        }
    }


}