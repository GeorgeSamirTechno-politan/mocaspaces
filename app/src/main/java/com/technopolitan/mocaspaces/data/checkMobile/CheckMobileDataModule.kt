package com.technopolitan.mocaspaces.data.checkMobile

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryDataModule
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import com.technopolitan.mocaspaces.utilities.transitionButton.TransitionButton
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.regex.Pattern
import javax.inject.Inject

@Module
class CheckMobileDataModule @Inject constructor(
    private var countryDataModule: CountryDataModule,
    private var context: Context,
    private var validationModule: ValidationModule,
    private var rxModule: RXModule
) {

    private lateinit var countryDropDownLayout: LinearLayout
    private lateinit var countryImageView: ImageView
    private lateinit var countryTextView: TextView
    private lateinit var countryArrowDown: ImageView
    private lateinit var mobileNumberEditText: EditText
    private lateinit var mobileObserver: Observable<String>
    private lateinit var button: TransitionButton
    private lateinit var countryMapperList: List<CountryMapper>
    private lateinit var callBack: (entity: CountryMapper) -> Unit

    fun init(
        countryDropDownLayout: LinearLayout,
        countryImageView: ImageView,
        countryTextView: TextView,
        countryArrowDown: ImageView,
        mobileNumberEditText: EditText,
        button: TransitionButton,
        callBack: (entity: CountryMapper) -> Unit
    ) {
        this.countryDropDownLayout = countryDropDownLayout
        this.countryImageView = countryImageView
        this.countryTextView = countryTextView
        this.countryArrowDown = countryArrowDown
        this.mobileNumberEditText = mobileNumberEditText
        this.button = button
        this.callBack = callBack
        initMobileObserver()
        countryDataModule.init(
            countryDropDownLayout,
            countryImageView,
            countryTextView,
            countryArrowDown
        )
        subscribeCountryDropDown()
        buttonChange(false)
    }

    fun setCountryDropDown(countryMapperList: List<CountryMapper>) {
        this.countryMapperList = countryMapperList
        countryDataModule.setCountryDropDown(countryMapperList)
    }

    private fun subscribeCountryDropDown() {
        countryDataModule.listenForDropDownObserver().subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe{
                subscribeMobileObserver(it)
            }
    }

    private fun buttonChange(enable: Boolean) {
        if (enable) {
            button.isEnabled = true
            button.alpha = 1.toFloat()
        } else {
            button.isEnabled = false
            button.alpha = 0.5.toFloat()
        }
    }

    private fun validateMobile(regex: String): Boolean =
        validationModule.validateWithRegex(
            mobileNumberEditText,
            regex,
            context.getString(R.string.invalide_mobile_number)
        )

    private fun initMobileObserver() {
        mobileObserver =
            rxModule.getTextWatcherObservable(mobileNumberEditText)
                .subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
    }

    private fun subscribeMobileObserver(dropDownMapper: DropDownMapper) {
        mobileObserver.subscribe {
            validateMobile(dropDownMapper.description)
            if (Pattern.matches(dropDownMapper.description, it)) {
                buttonChange(true)
                button.setOnClickListener {
                    val countryMapper = getCountryMapper(dropDownMapper.id)
                    if (countryMapper.code == "+20") {
                        if(mobileNumberEditText.text.toString().startsWith("0")){
                            countryMapper.mobile = mobileNumberEditText.text.toString().removeRange(0, 1)
                        }else{
                            countryMapper.mobile = mobileNumberEditText.text.toString()
                        }
                    }
                    callBack(countryMapper)
                }
            } else buttonChange(false)
        }
    }

    fun getCountryMapper(itemId: Int): CountryMapper {
        return countryMapperList.filter { it.id == itemId }[0]
    }

    fun getMobileWithCountryCode(countryCode: String, mobileNumber: String): String {
        return if (countryCode == "+20") {
            if (mobileNumber.startsWith("0")) {
                val newMobile = mobileNumber.removeRange(0, 1)
                "${countryCode}${newMobile}"
            } else "${countryCode}${mobileNumber}"
        } else "${countryCode}${mobileNumber}"
    }


}