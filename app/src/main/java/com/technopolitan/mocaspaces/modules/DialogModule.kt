package com.technopolitan.mocaspaces.modules


import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.ui.main.MainActivity
import dagger.Module
import javax.inject.Inject


@Module
class DialogModule @Inject constructor(
    private var context: Context,
    private var navigationModule: NavigationModule,
    private var activity: Activity
) {


    fun showTwoChooseDialogFragment(
        message: String,
        positiveBtnText: String? = null,
        negativeBtnText: String? = null,
        singleClick: Boolean = true,
        headerMessage: String? = null,
        navHostId: Int? = null,
        callBack: (entity: Boolean) -> Unit,

        ) {
        if (navigationModule.savedStateHandler(R.id.two_choose_dialog) == null) {
            val bundle = Bundle()
            bundle.putString(AppKeys.Message.name, message)
            bundle.putString(
                AppKeys.PositiveBtnText.name, positiveBtnText ?: context
                    .getString(R.string.yes)
            )
            bundle.putString(
                AppKeys.NegativeBtnText.name, negativeBtnText ?: context
                    .getString(R.string.no)
            )
            bundle.putString(
                AppKeys.HeaderMessage.name, headerMessage ?: context
                    .getString(R.string.app_name)
            )
            bundle.putBoolean(
                AppKeys.SingleClick.name, singleClick
            )
            navigationModule.navigateTo(
                R.id.two_choose_dialog,
                bundle = bundle,
                navHostId = navHostId
            )
            subscribeTwoChooseDialog(navHostId = navHostId, callBack = { callBack(it) })
        }
    }

    fun showMessageDialog(message: String, headerMessage: String) {
        showTwoChooseDialogFragment(
            message = message,
            positiveBtnText =
            context.getString(R.string.ok),
            headerMessage = headerMessage,
            singleClick = true,
            navHostId = R.id.nav_host_fragment,
            negativeBtnText = context.getString(R.string.cancel),
            callBack = {
            },
        )
    }



    fun showCloseAppDialog() {
        showTwoChooseDialogFragment(
            message = context.getString(R.string.close_app_message),
            positiveBtnText =
            context.getString(R.string.close),
            headerMessage = context.getString(R.string.close_app),
            singleClick = false,
            navHostId = R.id.nav_host_fragment,
            negativeBtnText = context.getString(R.string.cancel),
            callBack = {
                if (it)
                    activity.finishAffinity()
            },
        )
    }

    private fun subscribeTwoChooseDialog(
        callBack: (entity: Boolean) -> Unit,
        navHostId: Int? = null
    ) {
        val initValue: Boolean? = null
        navigationModule.savedStateHandler(
            navHostId = navHostId,
            destinationId = R.id.two_choose_dialog
        )?.let { savedState ->
            savedState.getLiveData(AppKeys.Message.name, initialValue = initValue)
                .observe((activity as MainActivity)) {
                    if (it != null) {
                        savedState.remove<Boolean>(AppKeys.Message.name)
                        callBack((it))
                    }

                }
        }
    }
}