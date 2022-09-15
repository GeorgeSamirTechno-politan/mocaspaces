package com.technopolitan.mocaspaces.ui.forgetPasswordMobile

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.checkMobile.CheckMobileDataModule
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.remote.SendOtpForgotPasswordMobile
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class ForgetPasswordMobileViewModel @Inject constructor(
    private var checkMobileDataModule: CheckMobileDataModule,
    private var sendOtpForgotPasswordMobile: SendOtpForgotPasswordMobile
) : BaseViewModel<String>() {


    lateinit var countryMapper: CountryMapper

    fun initCheckMobileDataModule(
        countryDropDownLayout: LinearLayout,
        countryImageView: ImageView,
        countryTextView: TextView,
        countryArrowDown: ImageView,
        mobileNumberEditText: EditText,
        button: TransitionButton,
        callBack: (entity: CountryMapper) -> Unit
    ) {
        checkMobileDataModule.init(
            countryDropDownLayout,
            countryImageView,
            countryTextView,
            countryArrowDown,
            mobileNumberEditText,
            button,
            callBack
        )
    }

    fun initCountryDropDown(countryMapperList: List<CountryMapper>) {
        checkMobileDataModule.setCountryDropDown(countryMapperList)
    }

    fun setSendMobileOtpRequest(mobile: String){
        apiMutableLiveData = sendOtpForgotPasswordMobile.verifyMobile(mobile)
    }

    fun getMobileOtp(): LiveData<ApiStatus<String>>{
        return apiMutableLiveData
    }


}