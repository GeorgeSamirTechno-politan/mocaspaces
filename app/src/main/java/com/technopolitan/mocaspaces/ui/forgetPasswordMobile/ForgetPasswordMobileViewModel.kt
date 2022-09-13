package com.technopolitan.mocaspaces.ui.forgetPasswordMobile

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.checkMobile.CheckMobileDataModule
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class ForgetPasswordMobileViewModel @Inject constructor(
    private var checkMobileDataModule: CheckMobileDataModule
) : BaseViewModel<String>() {

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


}