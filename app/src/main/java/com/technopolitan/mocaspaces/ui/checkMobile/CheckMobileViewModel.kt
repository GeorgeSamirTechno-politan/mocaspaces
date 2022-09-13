package com.technopolitan.mocaspaces.ui.checkMobile

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.checkMobile.CheckMobileDataModule
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class CheckMobileViewModel @Inject constructor(
    private var checkMobileRemote: CheckMobileRemote,
    private var checkMobileDataModule: CheckMobileDataModule
) :
    BaseViewModel<String>() {

    fun checkMobile(mobile: String) {
        this.apiMutableLiveData = checkMobileRemote.verifyMobile(mobile)
    }

    fun handleCheckMobileApi(): LiveData<ApiStatus<String>> = apiMutableLiveData

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

    fun getMobile(countryCode: String, mobile: String): String {
        return checkMobileDataModule.getMobileWithCountryCode(countryCode, mobile)
    }

    fun initCountryDropDown(countryMapperList: List<CountryMapper>) {
        checkMobileDataModule.setCountryDropDown(countryMapperList)
    }


}