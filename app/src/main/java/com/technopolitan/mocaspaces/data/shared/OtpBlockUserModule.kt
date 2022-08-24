//package com.technopolitan.mocaspaces.data.shared
//
//import android.content.Context
//import android.os.CountDownTimer
//import com.technopolitan.mocaspaces.R
//import com.technopolitan.mocaspaces.enums.AppKeys
//import com.technopolitan.mocaspaces.modules.DateTimeModule
//import com.technopolitan.mocaspaces.modules.DialogModule
//import com.technopolitan.mocaspaces.modules.SharedPrefModule
//import com.technopolitan.mocaspaces.utilities.DateTimeConstants
//import dagger.Module
//import java.util.*
//import javax.inject.Inject
//
//@Module
//class OtpBlockUserModule @Inject constructor(
//    private var context: Context,
//    private var dialogModule: DialogModule,
//    private var sharedPrefModule: SharedPrefModule,
//    private var dateTimeModule: DateTimeModule
//) {
//    private var count: CountDownTimer? = null
//    private var tries: Int = 0
//    fun init(count: CountDownTimer? = null) {
//        this.count = count
//        initOTPTry()
//    }
//
//    private fun initOTPTry() {
//        tries = if (sharedPrefModule.contain(AppKeys.OTPTrys.name)) {
//            sharedPrefModule.getIntFromShared(AppKeys.OTPTrys.name)
//        } else
//            3
//    }
//
//    fun enableSendOtp(): Boolean{
//        if(sharedPrefModule.contain(AppKeys.Blocked.name) && sharedPrefModule.getBooleanFromShared(
//                AppKeys.Blocked.name
//            ) && tries > 0){
//            if (dateTimeModule.diffInHours(
//                    Calendar.getInstance().time,
//                    dateTimeModule.formatDate(
//                        sharedPrefModule.getStringFromShared(AppKeys.BlockedDateTime.name),
//                        DateTimeConstants.dateTimeFormat
//                    )!!
//                ) > 2){
//                sharedPrefModule.setBooleanToShared(AppKeys.Blocked.name, false)
//                sharedPrefModule.setStringToShared(AppKeys.BlockedDateTime.name, "")
//                count?.start()
//                return true
//            }else{
//                enableResend(enable = true, resendSMS = false)
//                showBlockedDialog()
//                return false
//            }
//        }else {
//            count?.start()
//            return true
//        }
//    }
//
//    fun blockUser() {
//        enableResend(enable = true, resendSMS = false)
//        sharedPrefModule.setBooleanToShared(AppKeys.Blocked.name, true)
//        dateTimeModule.getTodayDateOrTime(DateTimeConstants.dateTimeFormat)?.let {
//            sharedPrefModule.setStringToShared(
//                AppKeys.BlockedDateTime.name,
//                it
//            )
//        }
//    }
//
////    fun enableResend(enable: Boolean, resendSMS: Boolean) {
////        resendTextView.isEnabled = enable
////        resendTextView.setOnClickListener {
////            if (resendSMS) {
////                resendCallBack(true)
////            } else if (tries == 0) {
////                showBlockedDialog()
////            }
////
////
////        }
////    }
//
//    fun showBlockedDialog() {
//        dialogModule.showMessageDialog(
//            context.getString(R.string.otp_send_block_message),
//            context.getString(R.string.warning)
//        )
//    }
//}