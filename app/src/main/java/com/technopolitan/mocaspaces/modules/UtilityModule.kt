@file:Suppress("DEPRECATION")

package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.viewpager2.widget.ViewPager2
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

    val darkStatusBar = 1
    val darkNavigationBar = 1 shl 1
    val lightStatusBar = 1 shl 3
    val lightNavigationBar = 1 shl 4

    fun setStatusBar(
        statusAndNavigationColor: Int = color.accent_color,
        backGroundDrawable: Int? = null
    ) {
        val window: Window = activity.window
        if (backGroundDrawable != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = context.getColor(android.R.color.transparent)
            window.navigationBarColor = context.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(AppCompatResources.getDrawable(context,backGroundDrawable))
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = context.getColor(statusAndNavigationColor)
            window.navigationBarColor = context.getColor(statusAndNavigationColor)
        }
        if (statusAndNavigationColor == color.white) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.decorView.windowInsetsController?.setSystemBarsAppearance(
//                    lightNavigationBar,
//                    lightStatusBar
//                )
//
//            }else{
                clearLightStatusBar()
//            }
        }else{
            // dark status and navigation bar here
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                window.decorView.windowInsetsController?.setSystemBarsAppearance(
//                    darkNavigationBar,
//                    darkStatusBar
//                )
//            }else{
              setLightStatusBar()
//            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setLightStatusBar() {
//        var flags = activity.window.decorView.systemUiVisibility
//        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        activity.window.decorView.systemUiVisibility = flags
    }

    private fun clearLightStatusBar() {
//        var flags = activity.window.decorView.systemUiVisibility
//        flags =
//            flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        activity.window.decorView.systemUiVisibility = flags
    }

    fun getBitmap(filePath: String): Bitmap {
        return BitmapFactory.decodeFile(filePath)
    }

    fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }

    fun getPageTransformationForViewPager2(): ViewPager2.PageTransformer {
        val nextItemVisiblePx = context.resources.getDimension(com.intuit.sdp.R.dimen._30sdp)
        val currentItemHorizontalMarginPx =
            context.resources.getDimension(com.intuit.sdp.R.dimen._15sdp)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        return pageTransformer
    }


}