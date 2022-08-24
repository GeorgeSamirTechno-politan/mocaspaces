package com.technopolitan.mocaspaces.data.country

import android.content.Context
import android.telephony.TelephonyManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.PowerMenuModule
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject


class CountryDataModule @Inject constructor(
    private var context: Context,
    private var powerMenuModule: PowerMenuModule,
    private var glideModule: GlideModule
) {

    private lateinit var countryDropDownLayout: LinearLayout
    private lateinit var countryImageView: ImageView
    private lateinit var countryTextView: TextView
    private lateinit var countryArrowDown: ImageView
    private lateinit var dropDownList: MutableList<DropDownMapper>
    private lateinit var countryList: List<CountryMapper>
    private lateinit var rotateClockWise: Animation
    private lateinit var rotateAntiClockWise: Animation
    private lateinit var dropDownSubject: PublishSubject<DropDownMapper>

    fun init(
        countryDropDownLayout: LinearLayout,
        countryImageView: ImageView,
        countryTextView: TextView,
        countryArrowDown: ImageView,
    ) {
        this.countryDropDownLayout = countryDropDownLayout
        this.countryImageView = countryImageView
        this.countryTextView = countryTextView
        this.countryArrowDown = countryArrowDown
        dropDownSubject = PublishSubject.create()
        initAnimation()

    }

    fun setCountryDropDown(countryList: List<CountryMapper>) {
        this.countryList = countryList
        initDropDown()
        initClickOnCountryPicker()
    }

    private fun initAnimation() {
        rotateClockWise = AnimationUtils.loadAnimation(context, R.anim.rotate_clock_wise)
        rotateAntiClockWise = AnimationUtils.loadAnimation(context, R.anim.rotate_anti_clock_wise)
    }


    private fun initClickOnCountryPicker() {
        countryDropDownLayout.setOnClickListener {
          if(powerMenuModule.isShown()){
              powerMenuModule.dismiss()
              countryArrowDown.startAnimation(rotateClockWise)
              countryArrowDown.rotation = 0.0f
          }else{
              powerMenuModule.show(countryDropDownLayout)
              countryArrowDown.startAnimation(rotateAntiClockWise)
              countryArrowDown.rotation = 180.0f
          }
        }
    }

    private fun initDropDown() {
        val simCountryCode = simCountryIso()
        val dropDownList = mutableListOf<DropDownMapper>()
        var selectedItem : DropDownMapper? = null
        countryList.forEach {
            var selected = simCountryCode.lowercase() == it.codeString.lowercase()
            val dropDownItem = DropDownMapper(
                it.id,
                it.code,
                it.url,
                selected,
                it.regex
            )
            if (selected)  selectedItem = dropDownItem
            dropDownList.add(dropDownItem)
        }

        this.dropDownList = dropDownList
        if (selectedItem ==  null) {
            dropDownList[0].selected = true
            selectedItem = dropDownList[0]
        }
        setSelectedForFirstTime(selectedItem!!)
        initPowerMenu()
    }

    private fun initPowerMenu(){
        powerMenuModule.init({
            setSelected(it)
        }, dropDownList)
    }

    private fun setSelectedForFirstTime(item: DropDownMapper) {
        dropDownList.forEach {
            it.selected = item.id == it.id
        }
        glideModule.loadImage(item.image, countryImageView)
        countryTextView.text = item.name
        subscribeMobileObserver(item)
    }

    private fun setSelected(item: DropDownMapper) {
        dropDownList.forEach {
            it.selected = item.id == it.id
        }
        glideModule.loadImage(item.image, countryImageView)
        countryTextView.text = item.name
        countryArrowDown.startAnimation(rotateClockWise)
        countryArrowDown.rotation = 0.0f
        subscribeMobileObserver(item)
    }

    private fun subscribeMobileObserver(regex: DropDownMapper) {
        dropDownSubject.onNext(regex)
    }

    fun listenForDropDownObserver(): Observable<DropDownMapper> = dropDownSubject


    private fun simCountryIso(): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simCountryIso = tm.simCountryIso
        return if (simCountryIso.isNullOrEmpty())
            Locale.getDefault().country
        else simCountryIso
    }
}