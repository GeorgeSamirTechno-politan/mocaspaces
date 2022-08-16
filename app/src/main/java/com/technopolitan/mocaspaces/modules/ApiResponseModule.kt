package com.technopolitan.mocaspaces.modules

import android.content.Context
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class ApiResponseModule<T> @Inject constructor(
    private var dialogModule: DialogModule,
    private var context: Context,
) {
    fun handleResponse(
        apiStatus: ApiStatus<T>,
        circularProgressButton: TransitionButton,
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                circularProgressButton.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND) {
                    onSuccessCallBack(apiStatus.data!!)
                }
            }
            is LoadingStatus -> circularProgressButton.startAnimation()
            is ErrorStatus, is FailedStatus -> {
                circularProgressButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE) {
                    showErrorOrFailedMessage(apiStatus.message)
                }
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