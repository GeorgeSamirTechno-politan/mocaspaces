package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.ui.main.MainActivity
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import io.ak1.pix.PixFragment
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import io.ak1.pix.utility.ARG_PARAM_PIX
import javax.inject.Inject

@Module
class PixModule @Inject constructor(
    private var permissionModule: PermissionModule,
    private var navigationModule: NavigationModule,
    private var activity: Activity,
    private var fragment: Fragment?,
    private var pikItModule: PikItModule,

    ) {

    private val options = Options().apply {
        ratio = Ratio.RATIO_AUTO
        count = 1
        spanCount = 4
        path = Constants.mocaImagesPath
        isFrontFacing = true
        mode = Mode.Picture
        flash = Flash.On
        preSelectedUrls = ArrayList()
    }
    private lateinit var uri: Uri
    private lateinit var pixFragment: PixFragment
    private var useCropAlgorithm: Boolean = true
    private lateinit var callBack: (bitmap: Bitmap) -> Unit
    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    fun init(
        useCropAlgorithm: Boolean = true, callBack: (bitmap: Bitmap) -> Unit,
        activityResultLauncher: ActivityResultLauncher<String>
    ) {
        this.useCropAlgorithm = useCropAlgorithm
        this.callBack = callBack
        this.activityResultLauncher = activityResultLauncher
        requestCameraPermission()
    }

    fun init(useCropAlgorithm: Boolean = true, callBack: (bitmap: Bitmap) -> Unit) {
        this.useCropAlgorithm = useCropAlgorithm
        this.callBack = callBack
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        val permissionList = mutableListOf<String>()
        permissionList.add(android.Manifest.permission.CAMERA)
        permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionModule.init(
//            activityResultLauncher,
            permissionList,
        ) {
            getImageUsingPix()
            callReplace()
        }
    }


    private fun callReplace() {
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, pixFragment.apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_PIX, options)
                }
            }, "PixFragment").addToBackStack("PixFragment").commit()

    }

    private fun getImageUsingPix() {
        pixFragment = pixFragment(options) { pixResult ->
            when (pixResult.status) {
                PixEventCallback.Status.SUCCESS -> {
                    this.uri = pixResult.data[0]
                    pikItModule.init(uri) {
                        handleImageForFaceDetection(it)
                    }
                }
                else -> {}
            }
        }
    }


    private fun listenForImageFromScanningFragment() {
        val bitmap: Bitmap? = null
        navigationModule.savedStateHandler(R.id.scanning_dialog)?.let { savedState ->
            savedState.getLiveData(AppKeys.Message.name, initialValue = bitmap)
                .observe(fragment!!) {
                    if (it != null) {
                        savedState.remove<Boolean>(AppKeys.Message.name)
                        callBack(it)
                    }
                }
        }
    }


    private fun handleImageForFaceDetection(path: String) {
        (activity as MainActivity).supportFragmentManager.popBackStack()
        Handler(Looper.getMainLooper()).postDelayed({
            val bundle = Bundle()
            bundle.putString(AppKeys.Message.name, path)
            bundle.putParcelable(AppKeys.ImageUri.name, uri)
            navigationModule.navigateTo(R.id.scanning_dialog, bundle = bundle)
            listenForImageFromScanningFragment()
        }, 300)
    }

//    fun updatePermissionResult(granted: Boolean) {
//        granted.let {
//            permissionModule.handlePermissionCallBack(it)
//        }
//    }
}