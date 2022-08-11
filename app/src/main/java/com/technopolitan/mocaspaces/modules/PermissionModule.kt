package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import dagger.Module
import javax.inject.Inject


@Module
class PermissionModule @Inject constructor(
    private var context: Context, private var fragment: Fragment?, private var activity: Activity,
    private var dialogModule: DialogModule
) {


    fun requestPermission(
        permissionName: String,
        requestCode: Int,
        headerPermissionName: String,
        detailsMessage: String,
        callBack: (entity: Boolean) -> Unit,
    ) {
        when {
            checkCustomPermission(permissionName) -> callBack(true)
            activity.shouldShowRequestPermissionRationale(permissionName) -> showDescriptionDialogForPermission(
                detailsMessage,
                permissionName,
                headerPermissionName,
                requestCode,
                callBack
            )
            else -> requestPermission(
                permissionName = permissionName,
                requestCode = requestCode,
                callBack = callBack,
                headerPermissionName = headerPermissionName,
                detailsMessage = detailsMessage
            )
        }
    }

    fun checkCustomPermission(permissionName: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionName
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showDescriptionDialogForPermission(
        detailsMessage: String,
        permissionName: String,
        headerPermissionName: String,
        requestCode: Int,
        callBack: (entity: Boolean) -> Unit
    ) {
        dialogModule.showTwoChooseDialogFragment(
            detailsMessage,
            positiveBtnText = context.getString(R.string.ok),
            negativeBtnText = context.getString(R.string.cancel),
            headerMessage = headerPermissionName,
            singleClick = false,
            callBack = {
                if (it) doRequestPermission(
                    permissionName = permissionName,
                    requestCode = requestCode,
                    callBack = callBack,
                    headerPermissionName = headerPermissionName,
                    detailsMessage = detailsMessage
                )
            })
    }

    private fun doRequestPermission(
        permissionName: String,
        requestCode: Int,
        callBack: (entity: Boolean) -> Unit,
        headerPermissionName: String,
        detailsMessage: String,
    ) {
        activity.requestPermissions(arrayOf(permissionName), requestCode)
        fragment?.let {
            it.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    callBack(true)
                } else {
                    showDescriptionDialogForPermission(
                        detailsMessage = detailsMessage,
                        permissionName = permissionName,
                        headerPermissionName = headerPermissionName,
                        requestCode = requestCode,
                        callBack = callBack
                    )
                }
            }
        }
    }
}