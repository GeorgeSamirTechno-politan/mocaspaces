package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import javax.inject.Inject


@Module
class PermissionModule @Inject constructor(
    private var context: Context, private var activity: Activity,
    private var dialogModule: DialogModule,
    private var sharedPrefModule: SharedPrefModule
) {

    private lateinit var activityResult: ActivityResultLauncher<String>
    private lateinit var permissionName: String
    private var requestCode: Int = 1
    private lateinit var headerPermissionName: String
    private lateinit var detailsMessage: String
    private lateinit var settingDetailsMessage: String
    private lateinit var callBack: (entity: Boolean) -> Unit

    fun init(
        activityResult: ActivityResultLauncher<String>,
        permissionName: String,
        callBack: (entity: Boolean) -> Unit,
    ) {
        this.activityResult = activityResult
        this.permissionName = permissionName
        this.callBack = callBack
        initPermissionInfo()
        requestPermission()
    }

    private fun initPermissionInfo() {
        when (permissionName) {
            android.Manifest.permission.CAMERA -> {
                requestCode = Constants.cameraPermissionCode
                headerPermissionName = context.getString(R.string.camera_permission)
                detailsMessage = context.getString(R.string.camera_permission_setting_message)
            }
            android.Manifest.permission.ACCESS_NETWORK_STATE -> {
                requestCode = Constants.networkStatusPermissionCode
                headerPermissionName = context.getString(R.string.network_status_permission)
                detailsMessage = context.getString(R.string.network_Status_setting_message)
            }
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                requestCode = Constants.writeExternalStoragePermissionCode
                headerPermissionName = context.getString(R.string.write_external_storage)
                detailsMessage =
                    context.getString(R.string.write_external_storage_permission_setting_message)
            }
            android.Manifest.permission.READ_EXTERNAL_STORAGE -> {
                requestCode = Constants.readExternalStoragePermissionCode
                headerPermissionName = context.getString(R.string.read_external_storage)
                detailsMessage = context.getString(R.string.read_external_storage_message)
                settingDetailsMessage =
                    context.getString(R.string.read_external_storage_setting_message)
            }
            android.Manifest.permission.RECEIVE_SMS -> {
                requestCode = Constants.receivingSmsPermissionCode
                headerPermissionName = context.getString(R.string.sms_permission)
                detailsMessage = context.getString(R.string.sms_permission_message)
                settingDetailsMessage = context.getString(R.string.sms_permission_setting_message)
            }
        }
    }

    private fun requestPermission() {
        when {
            checkCustomPermission(permissionName) -> callBack(true)
            hasAskedBefore() -> showDescriptionDialogForPermission()
            hasAskedBefore() && !activity.shouldShowRequestPermissionRationale(permissionName) -> showDialogToOpenSetting()
            else -> doRequestPermission()
        }
    }

    private fun hasAskedBefore(): Boolean {
        return when (permissionName) {
            android.Manifest.permission.CAMERA -> sharedPrefModule.contain(AppKeys.CameraPermissionAskedBefore.name)
            android.Manifest.permission.ACCESS_NETWORK_STATE -> sharedPrefModule.contain(AppKeys.NetworkStatusPermissionAskedBefore.name)
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> sharedPrefModule.contain(AppKeys.WriteExternalStoragePermissionAskedBefore.name)
            android.Manifest.permission.RECEIVE_SMS -> sharedPrefModule.contain(AppKeys.ReceivingSmsPermissionAskedBefore.name)
            else -> sharedPrefModule.contain(AppKeys.ReadExternalStoragePermissionAskedBefore.name)
        }
    }


    private fun checkCustomPermission(permissionName: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionName
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showDialogToOpenSetting() {
        dialogModule.showTwoChooseDialogFragment(
            settingDetailsMessage,
            positiveBtnText = context.getString(R.string.ok),
            negativeBtnText = context.getString(R.string.cancel),
            headerMessage = headerPermissionName,
            singleClick = false,
            callBack = {
                activity.startActivityForResult(Intent(Settings.ACTION_SETTINGS), requestCode)
            })
    }

    private fun showDescriptionDialogForPermission() {
        dialogModule.showTwoChooseDialogFragment(
            detailsMessage,
            positiveBtnText = context.getString(R.string.ok),
            negativeBtnText = context.getString(R.string.cancel),
            headerMessage = headerPermissionName,
            singleClick = false,
            callBack = {
                if (it) doRequestPermission()
            })
    }

    private fun doRequestPermission() {
        activity.requestPermissions(arrayOf(permissionName), requestCode)
        activityResult.launch(permissionName)
    }

    fun handlePermissionCallBack(isGranted: Boolean) {
        if (isGranted) {
            callBack(true)
        } else {
            showDescriptionDialogForPermission()
        }
    }
}