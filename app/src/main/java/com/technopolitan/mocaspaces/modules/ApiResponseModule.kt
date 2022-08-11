package com.technopolitan.mocaspaces.modules

import android.content.Context
import com.apachat.loadingbutton.core.customViews.CircularProgressButton
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.*
import javax.inject.Inject

class ApiResponseModule<T> @Inject constructor(
    private var dialogModule: DialogModule,
    private var context: Context,
) {
    fun handleResponse(
        apiStatus: ApiStatus<T>,
        circularProgressButton: CircularProgressButton,
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                circularProgressButton.revertAnimation()
                return onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> circularProgressButton.startAnimation()
            is ErrorStatus, is FailedStatus -> {
                showErrorOrFailedMessage(apiStatus.message)
                circularProgressButton.revertAnimation()
            }
        }
    }

    private fun showErrorOrFailedMessage(message: String?) {
        message?.let {
            dialogModule.showTwoChooseDialogFragment(it, positiveBtnText = context.getString(
                R.string.ok
            ), callBack = {

            })
        }
    }

}