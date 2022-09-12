package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.ybq.android.spinkit.SpinKitView
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class ApiResponseModule<T> @Inject constructor(
    private var dialogModule: DialogModule,
    private var context: Context,
    private var customAlertModule: CustomAlertModule,
    private var activity: Activity,
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

    fun handleResponse(
        apiStatus: ApiStatus<T>,
        spinKitView: SpinKitView,
        view: View,
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                spinKitView.visibility = View.GONE
                view.visibility = View.VISIBLE
                onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> {
                spinKitView.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
            is ErrorStatus, is FailedStatus -> {
                spinKitView.visibility = View.GONE
                view.visibility = View.VISIBLE
                showErrorOrFailedMessage(apiStatus.message)
            }
        }
    }

    fun handleResponse(
        apiStatus: ApiStatus<T>,
        spinKitView: SpinKitView,
        view: View,
        onSuccessCallBack: (entity: T) -> Unit,
        onFailureCallBack: ()-> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                spinKitView.visibility = View.GONE
                view.visibility = View.VISIBLE
                onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> {
                spinKitView.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
            is ErrorStatus, is FailedStatus -> {
                spinKitView.visibility = View.GONE
                view.visibility = View.VISIBLE
                showErrorOrFailedMessage(apiStatus.message)
                onFailureCallBack.invoke()
            }
        }
    }

    private fun showErrorOrFailedMessage(message: String?) {
        message?.let {
//            dialogModule.showTwoChooseDialogFragment(
//                it,
//                positiveBtnText = context.getString(
//                    R.string.ok,
//                ),
//            ) { }

            customAlertModule.showSnakeBar(activity.window.decorView.rootView as ViewGroup, it)
        }
    }

}