package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
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
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                onSuccessCallBack(apiStatus.data!!)
            }
            is ErrorStatus, is FailedStatus -> {
                showErrorOrFailedMessage(apiStatus.message)
            }
        }
    }

    fun handleResponse(
        apiStatus: ApiStatus<T>,
        loadingView: LottieAnimationView,
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                loadingView.visibility = View.GONE
                onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> {
                loadingView.visibility = View.VISIBLE
            }
            is ErrorStatus, is FailedStatus -> {
                loadingView.visibility = View.GONE
                showErrorOrFailedMessage(apiStatus.message)
            }
        }
    }

    fun handleResponse(
        apiStatus: ApiStatus<T>,
        loadingView: LottieAnimationView,
        view: View,
        onSuccessCallBack: (entity: T) -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                loadingView.visibility = View.GONE
                view.visibility = View.VISIBLE
                onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> {
                loadingView.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
            is ErrorStatus, is FailedStatus -> {
                loadingView.visibility = View.GONE
                view.visibility = View.VISIBLE
                showErrorOrFailedMessage(apiStatus.message)
            }
        }
    }

    fun handleResponse(
        apiStatus: ApiStatus<T>,
        loadingView: LottieAnimationView,
        view: View,
        onSuccessCallBack: (entity: T) -> Unit,
        onFailureCallBack: () -> Unit
    ) {
        when (apiStatus) {
            is SuccessStatus -> {
                loadingView.visibility = View.GONE
                view.visibility = View.VISIBLE
                onSuccessCallBack(apiStatus.data!!)
            }
            is LoadingStatus -> {
                loadingView.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
            is ErrorStatus, is FailedStatus -> {
                loadingView.visibility = View.GONE
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