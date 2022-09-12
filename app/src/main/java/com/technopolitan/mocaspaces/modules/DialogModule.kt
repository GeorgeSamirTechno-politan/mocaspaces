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
        revertColor: Boolean = false,
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
            bundle.putBoolean(AppKeys.RevertColor.name, revertColor)
            navigationModule.navigateTo(
                R.id.two_choose_dialog,
                bundle = bundle,
                navHostId = navHostId
            )
            subscribeTwoChooseDialog(navHostId = navHostId, callBack = { callBack(it) })
        }
    }

    private fun showSingleDialogFragment(
        message: String,
        btnText: String? = null,
        navHostId: Int? = null,
        callBack: (entity: Boolean) -> Unit,

        ) {
        if (navigationModule.savedStateHandler(R.id.single_button_dialog) == null) {
            val bundle = Bundle()
            bundle.putString(AppKeys.Message.name, message)
            bundle.putString(AppKeys.PositiveBtnText.name, btnText)
            navigationModule.navigateTo(
                R.id.single_button_dialog,
                bundle = bundle,
                navHostId = navHostId
            )
            subscribeSingleChooseDialog(navHostId = navHostId, callBack = { callBack(it) })
        }
    }


    fun showMessageDialog(
        message: String = "",
        btnText: String = context.getString(R.string.ok),
        callBack: ((entity: Boolean) -> Unit)
    ) {
        showSingleDialogFragment(
            message = message,
            btnText = btnText,
            navHostId = R.id.nav_host_fragment,
            callBack = callBack,
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
            revertColor = false,
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

    private fun subscribeSingleChooseDialog(
        callBack: (entity: Boolean) -> Unit,
        navHostId: Int? = null
    ) {
        val initValue: Boolean? = null
        navigationModule.savedStateHandler(
            navHostId = navHostId,
            destinationId = R.id.single_button_dialog
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


    fun showDatePickerDialog(
        day: Int = 1,
        month: Int = 1,
        year: Int = 2000,
        navHostId: Int? = null,
        maxYear: Int? = null,
        callBack: (year: Int, month: Int, day: Int) -> Unit,
    ) {
        val bundle = Bundle()
        bundle.putInt(AppKeys.Day.name, day)
        bundle.putInt(AppKeys.Year.name, year)
        bundle.putInt(AppKeys.Month.name, month)
        if(maxYear != null)
            bundle.putInt(AppKeys.MaxYear.name, maxYear)
        navigationModule.navigateTo(R.id.date_picker_dialog, bundle = bundle, navHostId = navHostId)
        subscribeDatePickerDialog(callBack, navHostId)
    }

    private fun subscribeDatePickerDialog(
        callBack: (year: Int, month: Int, day: Int) -> Unit,
        navHostId: Int? = null
    ) {
        val initValue: Boolean? = null
        navigationModule.savedStateHandler(
            navHostId = navHostId,
            destinationId = R.id.date_picker_dialog
        )?.let { savedState ->
            savedState.getLiveData(AppKeys.Message.name, initialValue = initValue)
                .observe((activity as MainActivity)) {
                    if (it != null) {
                        savedState.remove<Boolean>(AppKeys.Message.name)
                        val splitMessage = it.toString().split("-")
                        callBack(
                            splitMessage[0].toInt(),
                            splitMessage[1].toInt(),
                            splitMessage[2].toInt()
                        )
                    }

                }
        }
    }
}